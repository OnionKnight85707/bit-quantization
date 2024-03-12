package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.component.LocaleMessageDecorateSourceService;
import com.mzwise.common.exception.ApiException;
import com.mzwise.common.exception.ExceptionCodeConstant;
import com.mzwise.common.util.BalanceUtil;
import com.mzwise.constant.TransactionTypeEnum;
import com.mzwise.constant.UnitEnum;
import com.mzwise.constant.WalletTypeEnum;
import com.mzwise.constant.WithdrawStatusEnum;
import com.mzwise.modules.home.entity.HomeCoin;
import com.mzwise.modules.home.service.HomeCoinService;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.entity.UcWallet;
import com.mzwise.modules.ucenter.entity.UcWithdraw;
import com.mzwise.modules.ucenter.mapper.UcWithdrawMapper;
import com.mzwise.modules.ucenter.service.BiPayService;
import com.mzwise.modules.ucenter.service.UcMemberService;
import com.mzwise.modules.ucenter.service.UcWalletService;
import com.mzwise.modules.ucenter.service.UcWithdrawService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.modules.ucenter.vo.WithDrawOptionVO;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-03-31
 */
@Service
public class UcWithdrawServiceImpl extends ServiceImpl<UcWithdrawMapper, UcWithdraw> implements UcWithdrawService {
    @Autowired
    private HomeCoinService coinService;
    @Autowired
    private UcWalletService walletService;
    @Autowired
    private BiPayService biPayService;
    @Autowired
    private UcMemberService memberService;
    @Autowired
    private LocaleMessageDecorateSourceService localeMessageDecorateSourceService;
    @Override
    public List<WithDrawOptionVO> getOptions(Long memberId) {
        HashMap<String, HomeCoin> coins = coinService.listCanMap(HomeCoin::getCanWithdraw);
        List<UcWallet> masterWallets = walletService.getMasterWallets(memberId);
        ArrayList<WithDrawOptionVO> re = new ArrayList<>();
        for (UcWallet wallet : masterWallets) {
            HomeCoin coin = coins.get(wallet.getSymbol());
            if (coin != null) {
                re.add(new WithDrawOptionVO(wallet, coin.getWithdrawScale(), coin.getWithdrawFee()));
            }
        }
        return re;
    }

    @Override
    public Page<UcWithdraw> list(Long memberId, Integer pageNum, Integer pageSize) {
        Page<UcWithdraw> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UcWithdraw> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UcWithdraw::getMemberId, memberId)
                .orderByDesc(UcWithdraw::getCreateTime);
        return page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void apply(HttpServletRequest request, Long memberId, WalletTypeEnum walletType, String address, BigDecimal amount) {
        UcWallet wallet = walletService.getWallet(memberId, walletType);
        Assert.isTrue(wallet.getBalance().compareTo(amount) > 0, localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcWithdrawServiceImpl_002));
        HomeCoin coin = coinService.getById(wallet.getSymbol());
        // 校验提币数量是否满足最小提币数量
        BigDecimal minWithdrawAmount = coin.getMinWithdrawAmount();
        if(amount.compareTo(minWithdrawAmount) == -1){
           // throw new ApiException(ExceptionCodeConstant.UcWithdrawServiceImpl_001+minWithdrawAmount.setScale(1));
            throw new ApiException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcWithdrawServiceImpl_001));
        }
        BigDecimal fee = coin.getWithdrawFee().multiply(amount);
        UcWallet ucWallet = walletService.getMasterWallet(UnitEnum.USDT.getName(), memberId);
        BigDecimal beforeAmount = ucWallet.getBalance();
        BigDecimal afterAmount = beforeAmount.subtract(amount);
        String remark = "用户申请提币：用户=" + memberId + "，提币=" + amount + "，手续费=" + fee;
        Boolean freeze = BalanceUtil.freezeBalance(memberId, walletType, amount, fee, TransactionTypeEnum.WITHDRAWAL, beforeAmount, afterAmount, remark);
        Assert.isTrue(freeze,localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.UcWithdrawServiceImpl_002));
        UcWithdraw ucWithdraw = new UcWithdraw();
        UcMember member = memberService.getMemberById(memberId);
        ucWithdraw.setMemberId(memberId);
        ucWithdraw.setPhone(member.getPhone());
        ucWithdraw.setEmail(member.getEmail());
        ucWithdraw.setNickname(member.getNickname());
        ucWithdraw.setWalletType(walletType);
        ucWithdraw.setSymbol(wallet.getSymbol());
        ucWithdraw.setAmount(amount);
        ucWithdraw.setAddress(address);
        ucWithdraw.setFee(fee);
        ucWithdraw.setArrivedAmount(amount.subtract(fee));
        ucWithdraw.setStatus(WithdrawStatusEnum.WAITING);
        save(ucWithdraw);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean success(Long memberId, Long withdrawId, String txid, BigDecimal amount, BigDecimal fee, Long adminId) {
        UcWithdraw withdraw = getById(withdrawId);
        if (!withdraw.getStatus().equals(WithdrawStatusEnum.WAITING) && !withdraw.getStatus().equals(WithdrawStatusEnum.PASS)) {
            return false;
        }
        UcWallet wallet = walletService.getMasterWallet(UnitEnum.USDT.getName(), memberId);
        withdraw.setTxid(txid);
        withdraw.setStatus(WithdrawStatusEnum.DONE);
        if (!updateById(withdraw)) {
            return false;
        }
        String remark = "提币审核成功：用户=" + memberId + "，预计到账金额=" + withdraw.getArrivedAmount() + "，手续费=" + withdraw.getFee() + "，审核人员=" + adminId;
        return BalanceUtil.decreaseFrozen(withdraw.getMemberId(), withdraw.getWalletType(), withdraw.getAmount(), TransactionTypeEnum.WITHDRAWAL,
                wallet.getBalance(), wallet.getBalance(), remark);
    }

    /**
     * 提币失败
     * @param withdrawId 提现id
     * @param adminId 审核人员id
     * @param refuseReason 拒绝原因
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean fail(Long withdrawId, Long adminId, String refuseReason) {
        UcWithdraw withdraw = getById(withdrawId);
        if (!withdraw.getStatus().equals(WithdrawStatusEnum.WAITING) && !withdraw.getStatus().equals(WithdrawStatusEnum.PASS)) {
            return false;
        }
        withdraw.setStatus(WithdrawStatusEnum.FAIL);
        withdraw.setArrivedAmount(BigDecimal.ZERO);
        if (!updateById(withdraw)) {
            return false;
        }
        UcWallet wallet = walletService.getMasterWallet(UnitEnum.USDT.getName(), withdraw.getMemberId());
        BigDecimal beforeAmount = wallet.getBalance();
        BigDecimal afterAmount = wallet.getBalance().add(withdraw.getAmount());
        String remark = "提币审核失败：用户=" + withdraw.getMemberId() + "，审核人员=" + adminId + "，原因=" + refuseReason;
        return BalanceUtil.thawBalance(withdraw.getMemberId(), withdraw.getWalletType(), withdraw.getAmount(), BigDecimal.ZERO,
                TransactionTypeEnum.WITHDRAWAL_FAILED_REFUND, beforeAmount, afterAmount, remark);
    }
}
