package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.common.component.LocaleMessageDecorateSourceService;
import com.mzwise.common.component.RatePool;
import com.mzwise.common.exception.ApiException;
import com.mzwise.common.exception.ExceptionCodeConstant;
import com.mzwise.common.util.BalanceUtil;
import com.mzwise.common.util.DateUtil;
import com.mzwise.constant.*;
import com.mzwise.modules.home.entity.HomeCoin;
import com.mzwise.modules.home.service.HomeCoinService;
import com.mzwise.modules.ucenter.entity.*;
import com.mzwise.modules.ucenter.event.ServiceChargeEvent;
import com.mzwise.modules.ucenter.mapper.UcWalletMapper;
import com.mzwise.modules.ucenter.service.*;
import com.mzwise.modules.ucenter.vo.*;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static com.mzwise.constant.SysConstant.WALLET_TYPE_SYMBOL;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-03-30
 */
@Slf4j
@Service
public class UcWalletServiceImpl extends ServiceImpl<UcWalletMapper, UcWallet> implements UcWalletService {

    @Autowired
    private HomeCoinService coinService;
    @Autowired
    private RatePool ratePool;
    @Autowired
    private UcTransferService transferService;
    @Autowired
    private UcExchangeService exchangeService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private UcMemberService memberService;
    @Autowired
    private UcTransactionService transactionService;
    @Autowired
    private UcProfitDetailService profitDetailService;
    @Autowired
    private UcRechargeService rechargeService;
    @Autowired
    private UcPromotionsSettingService ucPromotionsSettingService;
    @Autowired
    private UcWalletService ucWalletService;
    @Autowired
    private UcStatisticsService statisticsService;
    @Autowired
    private LocaleMessageDecorateSourceService localeMessageDecorateSourceService;

    private BigDecimal getTotalBalance(UcWallet wallet) {
        return wallet.getBalance().add(wallet.getDeposit()).add(wallet.getFrozen());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void initWallets(Long memberId) {
        for (Map.Entry<WalletTypeEnum, String> entry : WALLET_TYPE_SYMBOL.entrySet()) {
            UcWallet ucWallet = new UcWallet();
            ucWallet.setMemberId(memberId);
            ucWallet.setType(entry.getKey());
            ucWallet.setSymbol(entry.getValue());
//            if (entry.getKey().equals(WalletTypeEnum.QUANT) || entry.getKey().equals(WalletTypeEnum.MINING) || entry.getKey().equals(WalletTypeEnum.PLATFORM)) {
                ucWallet.setMaster(true);
//            }
            save(ucWallet);
            //判断注册点是否处在优惠活动时间范围内,如在活动期内，则对账户进行赠送
            UcPromotionsSetting setting = ucPromotionsSettingService.getPromotionsSetting();
            boolean withinActivity = DateUtil.withinActivity(setting.getBeginTime(), setting.getEndTime());
            if (withinActivity) {
                BigDecimal award = new BigDecimal(setting.getUsdtNum());
                ucWallet.setTicket(award);
                ucWallet.setTotalTicket(award);
                ucWalletService.updateById(ucWallet);
                // 记录资金明细
                transactionService.addTransactionRecord(memberId, WalletTypeEnum.QUANT_SERVICE, TransactionTypeEnum.REGISTER_AWARD,
                        award, BigDecimal.ZERO, TransactionStatusEnum.SUCCESS, BigDecimal.ZERO, award, "注册奖励金额：" + award, null);
            }
        }
    }

    @Override
    public BalanceAllTypeVO getAllWalletsBalance(Long memberId) {
        QueryWrapper<UcWallet> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UcWallet::getMemberId, memberId);
        HashMap<String, AmountWithSymbolVO> re = new HashMap<>();
        List<UcWallet> wallets = list(wrapper);
        for (UcWallet wallet : wallets) {
            re.put(wallet.getType().getName(), new AmountWithSymbolVO(getTotalBalance(wallet), wallet.getSymbol()));
        }
        JSONObject object = JSONObject.fromObject(re);

        BalanceAllTypeVO vo = (BalanceAllTypeVO) JSONObject.toBean(object, BalanceAllTypeVO.class);
        return vo;
    }

    @Override
    public BalanceVO getTotalBalance(Long memberId) {
        QueryWrapper<UcWallet> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UcWallet::getMemberId, memberId);
        List<UcWallet> wallets = list(wrapper);
        BigDecimal totalUsd = BigDecimal.ZERO;
        for (UcWallet wallet : wallets) {
            totalUsd = totalUsd.add(getTotalBalance(wallet).multiply(ratePool.getRate(wallet.getSymbol())));
        }
        BigDecimal totalCny = totalUsd.multiply(ratePool.getUsdCnyRate());
        totalUsd.setScale(2, RoundingMode.HALF_UP);
        totalCny.setScale(2, RoundingMode.HALF_UP);
        return new BalanceVO(totalUsd, totalCny);
    }

    @Override
    public BalanceVO getTotalBalance(Long memberId, WalletTypeEnum type) {
        UcWallet wallet = getWallet(memberId, type);
        Assert.notNull(wallet);
        BigDecimal totalUsd = getTotalBalance(wallet).multiply(ratePool.getRate(wallet.getSymbol()));
        BigDecimal totalCny = totalUsd.multiply(ratePool.getUsdCnyRate());
        totalUsd.setScale(2, RoundingMode.HALF_UP);
        totalCny.setScale(2, RoundingMode.HALF_UP);
        return new BalanceVO(totalUsd, totalCny);
    }

    @Override
    public List<ExchangeOptionVO> exchangeOptions(Long memberId) {
        WalletAllTypeVO wallet = getWalletsEntity(memberId);
        ArrayList<ExchangeOptionVO> re = new ArrayList<>();
        // BTE->USDT
        ExchangeOptionVO option1 = new ExchangeOptionVO();
        option1.setFrom(wallet.getPLATFORM());
        option1.setTo(new ArrayList<ExchangeTunnelVO>() {{
            add(getExchangeTunnel(wallet.getPLATFORM(), wallet.getQUANT()));
        }});
        re.add(option1);
        // FIL->USDT
        ExchangeOptionVO option2 = new ExchangeOptionVO();
        option2.setFrom(wallet.getMINING());
        option2.setTo(new ArrayList<ExchangeTunnelVO>() {{
            add(getExchangeTunnel(wallet.getMINING(), wallet.getQUANT()));
        }});
        re.add(option2);
        // USDT->FIL
        ExchangeOptionVO option3 = new ExchangeOptionVO();
        option3.setFrom(wallet.getQUANT());
        option3.setTo(new ArrayList<ExchangeTunnelVO>() {{
            add(getExchangeTunnel(wallet.getQUANT(), wallet.getMINING()));
        }});
        re.add(option3);
        return re;
    }

    @Override
    public UcWallet getWallet(Long memberId, WalletTypeEnum type) {
        QueryWrapper<UcWallet> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UcWallet::getMemberId, memberId).eq(UcWallet::getType, type);
        UcWallet wallet = getOne(wrapper);
        return wallet;
    }

    @Override
    public List<UcWallet> getMasterWallets(Long memberId) {
        QueryWrapper<UcWallet> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UcWallet::getMemberId, memberId);
        wrapper.lambda().eq(UcWallet::getMaster, true);
        return list(wrapper);
    }

    @Override
    public UcWallet getMasterWallet(String symbol, Long memberId) {
        QueryWrapper<UcWallet> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UcWallet::getMemberId, memberId);
        wrapper.lambda().eq(UcWallet::getMaster, true);
        wrapper.lambda().eq(UcWallet::getSymbol, symbol);
        return getOne(wrapper);
    }

    private ExchangeTunnelVO getExchangeTunnel(UcWallet from, UcWallet to) {
        ExchangeTunnelVO tunnel = new ExchangeTunnelVO();

        BeanUtils.copyProperties(to, tunnel);
        tunnel.setRate(ratePool.getRate(from.getSymbol(), to.getSymbol()));
        HomeCoin coin = coinService.getById(to.getSymbol());
        tunnel.setFee(coin.getExchangeFee());
        return tunnel;
    }

    public WalletAllTypeVO walletsToVo(List<UcWallet> wallets) {
        HashMap<String, UcWallet> walletMap = new HashMap<>();
        for (UcWallet wallet : wallets) {
            walletMap.put(wallet.getType().getName(), wallet);
        }
        JSONObject object = JSONObject.fromObject(walletMap);
        WalletAllTypeVO vo = (WalletAllTypeVO) JSONObject.toBean(object, WalletAllTypeVO.class);
        return vo;
    }

    public HashMap<String, UcWallet> walletsToMap(List<UcWallet> wallets) {
        HashMap<String, UcWallet> walletMap = new HashMap<>();
        for (UcWallet wallet : wallets) {
            walletMap.put(wallet.getType().getName(), wallet);
        }
        return walletMap;
    }

    @Override
    public WalletAllTypeVO getWalletsEntity(Long memberId) {
        QueryWrapper<UcWallet> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UcWallet::getMemberId, memberId);
        List<UcWallet> wallets = list(wrapper);
        WalletAllTypeVO walletMap = walletsToVo(wallets);
        return walletMap;
    }

    @Override
    public HashMap<String, UcWallet> getWalletsMap(Long memberId) {
        QueryWrapper<UcWallet> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UcWallet::getMemberId, memberId);
        List<UcWallet> wallets = list(wrapper);
        return walletsToMap(wallets);
    }

    @Override
    public List<TransferOptionVO> transferOptions(Long memberId, String symbol) {
        WalletAllTypeVO walletMap = getWalletsEntity(memberId);
        switch (symbol) {
            case "USDT":
                return usdTransferOptions(walletMap);
            case "FIL":
                return filTransferOptions(walletMap);
            case "BTE":
                return bteTransferOptions(walletMap);
            default:
                return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transfer(Long memberId, WalletTypeEnum from, WalletTypeEnum to, BigDecimal amount) {
        Boolean aBoolean = BalanceUtil.decreaseBalance(memberId, from, amount, TransactionTypeEnum.TRANSFER);
        Assert.isTrue(aBoolean, "insufficient_balance");
        BalanceUtil.increaseBalance(memberId, to, amount, TransactionTypeEnum.TRANSFER);
        // 记录订单
        UcTransfer transfer = new UcTransfer();
        UcMember member = memberService.getMemberById(memberId);
        transfer.setMemberId(memberId);
        transfer.setNickname(member.getNickname());
        transfer.setEmail(member.getEmail());
        transfer.setPhone(member.getPhone());
        transfer.setAmount(amount);
        transfer.setFromType(from);
        transfer.setToType(to);
        //暂时不用
//        transfer.setSymbol(null);
        transferService.save(transfer);
        if (to.equals(WalletTypeEnum.QUANT_SERVICE)) {
            // 充值服务费事件
            ServiceChargeEvent serviceChargeEvent = new ServiceChargeEvent(applicationContext, memberId, amount);
            applicationContext.publishEvent(serviceChargeEvent);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void exchange(Long memberId, WalletTypeEnum from, WalletTypeEnum to, BigDecimal amount) {
        UcWallet fromWallet = getWallet(memberId, from);
        Assert.notNull(fromWallet);
        UcWallet toWallet = getWallet(memberId, to);
        Assert.notNull(toWallet);
        BigDecimal exchangeRate = ratePool.getRate(fromWallet.getSymbol(), toWallet.getSymbol());
        // 兑换成的余额
        BigDecimal toAmount = amount.multiply(exchangeRate);
        // 手续费
        HomeCoin toCoin = coinService.getById(toWallet.getSymbol());
        BigDecimal exchangeFee = toCoin.getExchangeFee();
        BigDecimal feeAmount = toAmount.multiply(exchangeFee);
        // 实际到账金额
        toAmount = toAmount.subtract(feeAmount);
        // 扣减from
        BalanceUtil.decreaseBalance(memberId, from, amount, TransactionTypeEnum.EXCHANGE);
        // 增加to
        BalanceUtil.increaseBalance(memberId, to, toAmount, feeAmount, TransactionTypeEnum.EXCHANGE, BigDecimal.ZERO, BigDecimal.ZERO, null);
        // 记录订单
        UcExchange exchange = new UcExchange();
        UcMember member = memberService.getMemberById(memberId);
        exchange.setMemberId(memberId);
        exchange.setNickname(member.getNickname());
        exchange.setPhone(member.getPhone());
        exchange.setEmail(member.getEmail());
        exchange.setFromType(from);
        exchange.setFromAmount(amount);
        exchange.setFromSymbol(fromWallet.getSymbol());
        exchange.setToType(to);
        exchange.setToAmount(toAmount);
        exchange.setToSymbol(toWallet.getSymbol());
        exchange.setFeeAmount(feeAmount);
        exchange.setFeeSymbol(toWallet.getSymbol());
        exchangeService.save(exchange);
    }

    @Override
    public UcWallet getBySymbolAndAddress(String symbol, String address) {
        QueryWrapper<UcWallet> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UcWallet::getSymbol, symbol);
        wrapper.lambda().eq(UcWallet::getAddress, address);
        return getOne(wrapper);
    }

    @Override
    public UcWallet getByAddress(String address) {
        QueryWrapper<UcWallet> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UcWallet::getAddress, address);
        return getOne(wrapper);
    }

    @Override
    public void freezeService(Long memberId, BigDecimal amount) {
        Boolean freezeBalance = BalanceUtil.freezeBalance(memberId, WalletTypeEnum.QUANT_SERVICE, amount, TransactionTypeEnum.FROZEN_QUANT_SERVICE, null);
        Assert.isTrue(freezeBalance, "insufficient_balance");
    }

    /**
     * 获取平台服务费比例
     *
     * @return
     */
    @Override
    public BigDecimal getPlatformServiceRate() {
        return baseMapper.getPlatformServiceRate();
    }

    /**
     * 平台收取服务费
     *
     * @param memberId 用户id
     * @param profit   利润
     * @param orderId 订单id
     * @param amount 金额
     * @return 服务费是否收取成功：true：成功， false：失败
     */
    @Override
    @Transactional
    public UserChargesResp platformChargesServiceFee(Long memberId, BigDecimal profit, String orderId, BigDecimal amount) {
        UserChargesResp resp = new UserChargesResp();
        if (ObjectUtils.isEmpty(profit) || profit.compareTo(BigDecimal.ZERO) <= 0) {
            log.info("无需收取平台收取服务费(无正向收益)：订单={}，会员={}，收益={}", orderId, memberId, profit);
            resp.setServiceFeeIsSuccess(true);
            resp.setActualCalcCommissionProfit(profit);
            return resp;
        }
        // 平台服务费比例
        BigDecimal chargeServiceRate = baseMapper.getPlatformServiceRate();
        UcWallet masterWallet = getMasterWallet(UnitEnum.USDT.getName(), memberId);
        // 需要收取的服务费
        BigDecimal serviceCharge = profit.multiply(chargeServiceRate).setScale(8, BigDecimal.ROUND_HALF_UP);
        if (masterWallet.getBalance().add(masterWallet.getTicket()).compareTo(serviceCharge) >= 0) {
            // 用户扣费
//            subtractBalance(masterWallet.getId(), serviceCharge);
            UserChargesResp userChargesResp = userCharges(masterWallet, serviceCharge, profit);
            // 平台收费
            platformCharges(serviceCharge);
            String remark = "平台收取服务费：用户=" + memberId + ", 订单=" + orderId + ", 金额=" + amount + ", 获取利润=" + profit
                    + ", 平台收取服务费=" + serviceCharge + "(服务费比例=" + chargeServiceRate + ")";
            // 记录资金流水
            transactionService.addTransactionRecord(memberId, WalletTypeEnum.QUANT_SERVICE,
                    TransactionTypeEnum.CHARGE_QUANT_SERVICE, serviceCharge.multiply(new BigDecimal("-1")),
                    BigDecimal.ZERO, TransactionStatusEnum.SUCCESS, masterWallet.getBalance().add(masterWallet.getTicket()),
                    masterWallet.getBalance().add(masterWallet.getTicket()).subtract(serviceCharge), remark, null);
            userChargesResp.setServiceFeeIsSuccess(true);
            return userChargesResp;
        } else {
            // 如果用户钱包不够服务费，记录欠款
            memberService.addArrears(memberId, serviceCharge);
            resp.setServiceFeeIsSuccess(false);
            return resp;
        }
    }

    /**
     * 用户扣费
     * @param wallet 钱包
     * @param amount 需要扣费的金额
     * @param profit 收益
     * @return
     */
    @Override
    public UserChargesResp userCharges(UcWallet wallet, BigDecimal amount, BigDecimal profit) {
        Long walletId = wallet.getId();
        UserChargesResp resp = new UserChargesResp();
        if (wallet.getTicket().compareTo(BigDecimal.ZERO) == 1) {
            // 如果有奖励金额，先扣除奖励金额
            if (wallet.getTicket().compareTo(amount) >= 0) {
                // 奖励金额完全够扣情况
                baseMapper.subtractTicket(walletId, amount);
                resp.setDeductionAward(amount);
                resp.setActualDeductionBalance(BigDecimal.ZERO);
                resp.setActualCalcCommissionProfit(BigDecimal.ZERO);
            } else {
                // 全部扣除奖励金额，剩下的扣余额
                // 需要扣除的余额 = 需要扣费的金额 - 钱包奖励金额
                BigDecimal needDeductBalance = amount.subtract(wallet.getTicket());
                baseMapper.subtractTicket(walletId, wallet.getTicket());
                baseMapper.subtractBalance(walletId, needDeductBalance);
                // 实际应计算佣金收益 = 需要扣除的余额 / 需要扣费的金额 * 收益
                BigDecimal actualCalcCommissionProfit = needDeductBalance.divide(amount, 8, BigDecimal.ROUND_HALF_UP).multiply(profit);
                resp.setDeductionAward(wallet.getTicket());
                resp.setActualDeductionBalance(needDeductBalance);
                resp.setActualCalcCommissionProfit(actualCalcCommissionProfit);
            }
        } else {
            // 只扣除余额情况
            baseMapper.subtractBalance(walletId, amount);
            resp.setDeductionAward(BigDecimal.ZERO);
            resp.setActualDeductionBalance(amount);
            resp.setActualCalcCommissionProfit(profit);
        }
        return resp;
    }

    /**
     * 减少余额
     *
     * @param walletId 钱包id
     * @param amount   金额
     */
    @Override
    public void subtractBalance(Long walletId, BigDecimal amount) {
        baseMapper.subtractBalance(walletId, amount);
    }

    /**
     * 增加余额
     *
     * @param walletId 钱包id
     * @param amount   金额
     */
    @Override
    public void addBalance(Long walletId, BigDecimal amount) {
        baseMapper.addBalance(walletId, amount);
    }

    /**
     * 平台收费
     *
     * @param amount 金额
     */
    @Override
    public void platformCharges(BigDecimal amount) {
        baseMapper.platformCharges(amount);
        // 统计：增加公司收益
        statisticsService.addCompanyProfit(amount);
    }

    /**
     * 平台扣费
     *
     * @param amount 金额
     */
    @Override
    public void platformDeduction(BigDecimal amount) {
        baseMapper.platformDeduction(amount);
        // 统计：减少公司收益
        statisticsService.subtractCompanyProfit(amount);
    }

    /**
     * 更新利润
     *
     * @param memberId 用户id
     * @param orderId  订单id
     * @param profit   利润
     */
    @Override
    public void updateProfit(Long memberId, String orderId, BigDecimal profit) {
        // 更新钱包表利润
        baseMapper.updateWalletProfit(memberId, profit);
        Integer tradeType = baseMapper.selTradeTypeByOrderId(orderId);
        // 更新uc_quant表利润
        if (TradeTypeEnum.SWAP.getValue() == tradeType) {
            baseMapper.updateSwapProfit(memberId, profit);
        } else if (TradeTypeEnum.EXCHANGE.getValue() == tradeType) {
            baseMapper.updateExchangeProfit(memberId, profit);
        }
    }

    /**
     * 手动增加余额
     *
     * @param memberId
     * @param amount
     */
    @Override
    public void manualAddBalance(Long memberId, BigDecimal amount) {
        if (ObjectUtils.isEmpty(amount) || amount.compareTo(BigDecimal.ZERO) < 1) {
            throw new ApiException("请输入正确的金额");
        }
        // 生成手动充值的唯一标记
        String txId = "manualRecharge-" + System.currentTimeMillis() + "-" + UUID.randomUUID();
        UcWallet masterWallet = this.getMasterWallet(UnitEnum.USDT.getName(), memberId);
        if (ObjectUtils.isEmpty(masterWallet.getAddress())) {
            throw new ApiException("请先让该用户去充币界面获取钱包地址");
        }
        boolean manualRechargeResult = rechargeService.saveRecharge(masterWallet, masterWallet.getAddress(), amount, txId, RechargeTypeEnum.MANUAL);
        if (! manualRechargeResult) {
            throw new ApiException("充值失败，请重试");
        }
        String remark = "管理员手动充币：用户=" + memberId + ", 充币=" + amount;
        // 增加用户余额(并记录资金流水)
        BalanceUtil.increaseBalance(memberId, masterWallet.getType(), amount, BigDecimal.ZERO, TransactionTypeEnum.RECHARGE,
                masterWallet.getBalance().add(masterWallet.getTicket()), masterWallet.getBalance().add(masterWallet.getTicket()).add(amount), remark);
        Thread thread = new Thread("后台手动充值后补缴欠款") {
            @Override
            public void run() {
                // 充值后补缴欠款
                profitDetailService.repayArrears(memberId, true);
            }
        };
        thread.start();
    }

    /**
     * 手动减少余额
     *
     * @param memberId
     * @param amount
     */
    @Override
    public void manualSubtractBalance(Long memberId, BigDecimal amount) {
        UcWallet masterWallet = this.getMasterWallet(UnitEnum.USDT.getName(), memberId);
        baseMapper.subtractBalance(masterWallet.getId(), amount);
        // 记录资金明细
        String remark = "管理员手动减币：用户：" + memberId + "，金额：" + amount;
        // 记录流水
        transactionService.addTransactionRecord(memberId, WalletTypeEnum.QUANT_SERVICE, TransactionTypeEnum.WITHDRAWAL,
                amount.multiply(new BigDecimal("-1")), BigDecimal.ZERO, TransactionStatusEnum.SUCCESS,
                masterWallet.getBalance(), masterWallet.getBalance().subtract(amount), remark, null);
    }

    /**
     * usdt 的划转可选项
     *
     * @param walletMap
     */
    private List<TransferOptionVO> usdTransferOptions(WalletAllTypeVO walletMap) {
        ArrayList<TransferOptionVO> re = new ArrayList<>();
//         资产账户转服务费账户
        TransferOptionVO option = new TransferOptionVO();
        option.setFrom(walletMap.getQUANT());
        option.setTo(new ArrayList<UcWallet>() {{
            add(walletMap.getQUANT_SERVICE());
        }});
        re.add(option);
        // 量化社区账户转资产账户
        TransferOptionVO option2 = new TransferOptionVO();
        option2.setFrom(walletMap.getQUANT_COMMUNITY());
        option2.setTo(new ArrayList<UcWallet>() {{
            add(walletMap.getQUANT());
        }});
        re.add(option2);
        return re;
    }

    /**
     * FIL 的划转可选项
     *
     * @param walletMap
     */
    private List<TransferOptionVO> filTransferOptions(WalletAllTypeVO walletMap) {
        ArrayList<TransferOptionVO> re = new ArrayList<>();
        // 矿机收益账户转资产账户
        TransferOptionVO option = new TransferOptionVO();
        option.setFrom(walletMap.getMINING_PROFIT());
        option.setTo(new ArrayList<UcWallet>() {{
            add(walletMap.getMINING());
        }});
        re.add(option);
        // 矿机社区账户转资产账户
        TransferOptionVO option2 = new TransferOptionVO();
        option2.setFrom(walletMap.getMINING_COMMUNITY());
        option2.setTo(new ArrayList<UcWallet>() {{
            add(walletMap.getMINING());
        }});
        re.add(option2);
        return re;
    }

    /**
     * BTE 的划转可选项
     *
     * @param walletMap
     */
    private List<TransferOptionVO> bteTransferOptions(WalletAllTypeVO walletMap) {
        ArrayList<TransferOptionVO> re = new ArrayList<>();
        // 平台分红账户转资产账户
        TransferOptionVO option = new TransferOptionVO();
        option.setFrom(walletMap.getPLATFORM_SHARE());
        option.setTo(new ArrayList<UcWallet>() {{
            add(walletMap.getPLATFORM());
        }});
        re.add(option);
        return re;
    }

    /**
     *  获取某个用户的奖励金额
     * @param memberId
     * @param type
     * @return
     */
    @Override
    public BalanceVO getAwardBalance(Long memberId, WalletTypeEnum type) {
        UcWallet wallet = getWallet(memberId, type);
        Assert.notNull(wallet);
        BigDecimal awardUsd = wallet.getTicket().multiply(ratePool.getRate(wallet.getSymbol()));
        BigDecimal awardCny=awardUsd.multiply(ratePool.getUsdCnyRate());
        BigDecimal usd = awardUsd.setScale(2, RoundingMode.HALF_UP);
        BigDecimal cny = awardCny.setScale(2, RoundingMode.HALF_UP);
        return new BalanceVO(usd,cny);
    }

    /**
     *  冻结用户服务费
     * @param memberId
     * @param type
     * @param amount
     */
    @Override
    public void freeze(Long memberId, WalletTypeEnum type, BigDecimal amount) {
        baseMapper.freezeBalance(memberId,type,amount);
    }

    /**
     *  减少用户冻结
     * @param memberId
     * @param type
     * @param amount
     */
    @Override
    public void decreaseFrozen(Long memberId, WalletTypeEnum type, BigDecimal amount) {
        baseMapper.decreaseFrozen(memberId,WalletTypeEnum.QUANT_SERVICE,amount);
    }

    /**
     * 判断服务费
     *
     * @param memberId
     * @return
     */
    @Override
    public void checkMoneyExist(Long memberId) {
        UcWallet wallet = getWallet(memberId, WalletTypeEnum.QUANT_SERVICE);
        UcMember member = memberService.getById(memberId);
        BigDecimal balance = wallet.getBalance().add(wallet.getTicket());
        if (balance.compareTo(BigDecimal.ZERO) <= 0 || member.getUnPay().compareTo(BigDecimal.ZERO) == 1) {
            throw new ApiException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.QuantMemberService_001));
        }
    }

}
