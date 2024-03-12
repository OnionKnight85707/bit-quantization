package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.common.exception.ApiException;
import com.mzwise.common.util.BalanceUtil;
import com.mzwise.common.util.DESTool;
import com.mzwise.constant.RechargeTypeEnum;
import com.mzwise.constant.TransactionTypeEnum;
import com.mzwise.modules.home.entity.HomeCoin;
import com.mzwise.modules.home.service.HomeCoinService;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.entity.UcRecharge;
import com.mzwise.modules.ucenter.entity.UcWallet;
import com.mzwise.modules.ucenter.mapper.UcRechargeMapper;
import com.mzwise.modules.ucenter.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-03-31
 */
@Service
public class UcRechargeServiceImpl extends ServiceImpl<UcRechargeMapper, UcRecharge> implements UcRechargeService {

    @Value("${encryption.des.rec}")
    private String desKey;

    @Autowired
    private HomeCoinService homeCoinService;
    @Autowired
    private UcWalletService walletService;
    @Autowired
    private BiPayService biPayService;
    @Autowired
    private UcMemberService memberService;
    @Autowired
    private UcProfitDetailService profitDetailService;
    @Autowired
    private UcStatisticsService statisticsService;

    @Override
    public List<UcWallet> getOptions(Long memberId) {
        List<HomeCoin> coins = homeCoinService.listCan(HomeCoin::getCanRecharge);
        List<String> symbols = coins.stream().map(v -> v.getSymbol()).collect(Collectors.toList());
        List<UcWallet> masterWallets = walletService.getMasterWallets(memberId);
        List<UcWallet> re = masterWallets.stream().filter(v -> symbols.contains(v.getSymbol())).collect(Collectors.toList());
        DESTool tool = new DESTool(desKey);
        re.stream().forEach(e -> {
            try {
                e.setAddress(tool.decode(e.getAddress()));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        return re;
    }

    @Override
    public String createCoinAddress(Long currentUserId, HttpServletRequest request, String symbol) {
        try {
            UcWallet masterWallet = walletService.getMasterWallet(symbol, currentUserId);
            Assert.notNull(masterWallet, "wallet null");
            if (StringUtils.isEmpty(masterWallet.getAddress())) {
                QueryWrapper<HomeCoin> wrapper = new QueryWrapper<>();
                wrapper.lambda().eq(HomeCoin::getSymbol, symbol);

                HomeCoin coin = homeCoinService.getOne(wrapper);
                System.out.println("createCoinAddress  home coin ="+coin);
                Assert.notNull(coin, "coin null");
                String address = biPayService.createCoinAddress(request, coin);
                DESTool tool = new DESTool(desKey);
                masterWallet.setAddress(tool.encode(address));
                walletService.updateById(masterWallet);
                return address;
            } else {
                throw new ApiException("do not repeat apply");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException("failed");
        }
    }

    /**
     * 保存充值记录
     * @param wallet 钱包
     * @param encodeAddress 加密后地钱包址
     * @param amount 充值金额
     * @param txId 链上交易id
     * @param rechargeType 充值类型
     * @return
     */
    @Override
    public boolean saveRecharge(UcWallet wallet, String encodeAddress, BigDecimal amount, String txId, RechargeTypeEnum rechargeType) {
        UcRecharge recharge = new UcRecharge();
        UcMember member = memberService.getMemberById(wallet.getMemberId());
        recharge.setMemberId(wallet.getMemberId());
        recharge.setNickname(member.getNickname());
        recharge.setPhone(member.getPhone());
        recharge.setEmail(member.getEmail());
        recharge.setAddress(encodeAddress);
        recharge.setAmount(amount);
        recharge.setSymbol(wallet.getSymbol());
        recharge.setTxid(txId);
        recharge.setWalletType(wallet.getType());
        recharge.setArrivalTime(new Date());
        recharge.setRechargeType(rechargeType);
        if ( ! save(recharge)) {
            return false;
        }
        // 统计：增加网络充币数量 & 增加后台充币数量
        if (RechargeTypeEnum.MANUAL == rechargeType) {
            statisticsService.addRechargeBackstage(amount);
        } else if (RechargeTypeEnum.U_DUN == rechargeType) {
            statisticsService.addRechargeOnline(amount);
        }
        return true;
    }

    /**
     * 充币成功
     * @param coin 币种
     * @param address 地址
     * @param amount 交易數量
     * @param fee 矿工费
     * @param decimals 币种精度
     * @param txId 区块链交易哈希
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized Boolean success(HomeCoin coin, String address, String amount, String fee, String decimals, String txId) throws Exception {
        DESTool tool = new DESTool(desKey);
        // 加密后钱包地址
        String encodeAddress = tool.encode(address);
        if (getByAddressAndTxid(encodeAddress, txId) != null) {
            return false;
        }
        UcWallet wallet = walletService.getByAddress(encodeAddress);
        if (wallet == null) {
            return false;
        }
        BigDecimal calcAmount = udunCalcAmount(amount, decimals);
        BigDecimal calcFee = udunCalcAmount(fee, decimals);
        // 钱包变动前金额
        BigDecimal amountBeforeChange = wallet.getBalance().add(wallet.getTicket());
        // 钱包变动后金额
        BigDecimal amountAfterChange = wallet.getBalance().add(wallet.getTicket()).add(calcAmount);
        boolean saveRechargeResult = this.saveRecharge(wallet, encodeAddress, calcAmount, txId, RechargeTypeEnum.U_DUN);
        if ( ! saveRechargeResult) {
            return false;
        }
        String remark = "用户优盾充币：用户=" + wallet.getMemberId() + ", 充币=" + amount;
        // 增加用户余额(并记录资金流水)
        Boolean increaseBalanceResult = BalanceUtil.increaseBalance(wallet.getMemberId(), wallet.getType(), calcAmount, calcFee,
                TransactionTypeEnum.RECHARGE, amountBeforeChange, amountAfterChange, remark);
        Thread thread = new Thread("优盾充值后补缴欠款") {
            @Override
            public void run() {
                // 充值后补缴欠款
                profitDetailService.repayArrears(wallet.getMemberId(), true);
            }
        };
        thread.start();
        return increaseBalanceResult;
    }

    @Override
    public Page<UcRecharge> list(Long memberId, Integer pageNum, Integer pageSize) {
        Page<UcRecharge> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UcRecharge> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UcRecharge::getMemberId, memberId)
                .orderByDesc(UcRecharge::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    public UcRecharge getByAddressAndTxid(String address, String txid) {
        QueryWrapper<UcRecharge> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UcRecharge::getAddress, address)
                .eq(UcRecharge::getTxid, txid);
        return getOne(wrapper);
    }

    /**
     * 优盾计算金额
     * @param amount 金额
     * @param decimals 次幂
     * @return
     */
    private BigDecimal udunCalcAmount(String amount, String decimals) {
        double pow = Math.pow(10, Double.parseDouble(decimals));
        double result = Double.parseDouble(amount) / pow;
        BigDecimal bd = new BigDecimal(result).setScale(8, BigDecimal.ROUND_HALF_UP);
        return bd;
    }

}
