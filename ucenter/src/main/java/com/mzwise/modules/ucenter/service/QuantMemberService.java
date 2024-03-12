package com.mzwise.modules.ucenter.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.mzwise.common.api.CommonResult;
import com.mzwise.common.api.ResultCode;
import com.mzwise.common.component.LocaleMessageDecorateSourceService;
import com.mzwise.common.exception.ApiException;
import com.mzwise.common.exception.ExceptionCodeConstant;
import com.mzwise.common.util.BalanceUtil;
import com.mzwise.common.util.LocaleSourceUtil;
import com.mzwise.common.util.WalletProfitUtil;
import com.mzwise.constant.TransactionStatusEnum;
import com.mzwise.constant.TransactionTypeEnum;
import com.mzwise.constant.WalletTypeEnum;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.entity.UcWallet;
import com.mzwise.quant.rpc.Member;
import com.mzwise.quant.rpc.RiskTypeParamEnum;
import com.mzwise.quant.rpc.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.mzwise.common.api.ResultCode.CUSTOM10003;

/**
 * <p>
 *  量化收费服务
 * </p>
 *
 * @author admin
 * @since 2021-05-20
 */

@Service(interfaceClass = MemberService.class)
@Component
@Slf4j
public class QuantMemberService implements MemberService {

    @Autowired
    private UcWalletService walletService;
    @Autowired
    private UcChargeService ucChargeService;
    @Autowired
    private UcMemberService memberService;
    @Autowired
    private UcTransactionService ucTransactionService;
    @Autowired
    private LocaleMessageDecorateSourceService localeMessageDecorateSourceService;
    @Override
    public Member memberInfo(Long memberId) {
        UcMember ucMember = memberService.getById(memberId);
        Member member = new Member();
        BeanUtils.copyProperties(ucMember, member);
        if (ucMember.getRiskType()!=null) {
            member.setRiskType(RiskTypeParamEnum.valueOf(ucMember.getRiskType().getName()));
        }
        return member;
    }

    @Override
    public CommonResult onActive(Long memberId, BigDecimal amount) {
        // 步骤一： 扣除服务费
        UcWallet wallet = walletService.getWallet(memberId, WalletTypeEnum.QUANT_SERVICE);
        if (wallet.getBalance().compareTo(amount)<0) {
            BigDecimal restAmount = amount.subtract(wallet.getBalance());
            try {
                walletService.transfer(memberId, WalletTypeEnum.QUANT, WalletTypeEnum.QUANT_SERVICE, restAmount);
            } catch (Exception e) {
                return CommonResult.failed(ResultCode.CUSTOM10002, String.format(LocaleSourceUtil.getMessage("active_robot_insufficient_balance"), restAmount.setScale(2, RoundingMode.HALF_UP)));
            }
        }
        // 步骤二： 同步用户激活状态
        memberService.active(memberId);
        return CommonResult.success();
    }

    @Override
    public CommonResult onCheckFee(Long memberId, BigDecimal amount) {
        Boolean re = BalanceUtil.checkFrozen(memberId, WalletTypeEnum.QUANT_SERVICE, amount);
        if (!re) {
            return CommonResult.failed("not-enough");
        }
        return CommonResult.success();
    }

    @Override
    public CommonResult onInsureFee(Long memberId, BigDecimal amount, String tid) {
        UcWallet wallet = walletService.getWallet(memberId, WalletTypeEnum.QUANT_SERVICE);
        if (wallet == null) {
            throw new ApiException("用户：" + memberId + "，钱包类型：" + WalletTypeEnum.QUANT_SERVICE +  "，钱包为空");
        }
        if (wallet.getFrozen().compareTo(amount)>=0) {
            return CommonResult.success();
        }
        BigDecimal need = amount.subtract(wallet.getFrozen());
        Boolean freezen = BalanceUtil.freezeBalance(memberId, WalletTypeEnum.QUANT_SERVICE, need, TransactionTypeEnum.FROZEN_QUANT_SERVICE, null);
        if (!freezen) {
            return CommonResult.failed(CUSTOM10003, String.format(LocaleSourceUtil.getMessage("quant_service_insufficient"), need.setScale(2, RoundingMode.UP).toString()));
        }
        return CommonResult.success();
    }

    @Override
    public CommonResult onReturnFee(Long memberId, @RequestParam(value = "amount") BigDecimal rate, String tid) {
        UcWallet wallet = walletService.getWallet(memberId, WalletTypeEnum.QUANT_SERVICE);
        BigDecimal back = wallet.getFrozen().multiply(rate);
        Boolean freezen = BalanceUtil.thawBalance(memberId, WalletTypeEnum.QUANT_SERVICE, back, TransactionTypeEnum.FROZEN_QUANT_SERVICE);
        if (!freezen) {
            return CommonResult.failed("a_serious_error_occurred_in_the_service_fee_refund");
        }
        return CommonResult.success();
    }

    @Override
    public CommonResult onRecordProfit(Long memberId, BigDecimal todayProfit, BigDecimal totalProfit) {
        Boolean re = WalletProfitUtil.setProfit(memberId, WalletTypeEnum.QUANT_SERVICE, todayProfit, totalProfit);
        if (re) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 判断服务费
     *
     * @param memberId
     * @return
     */
    @Override
    public void checkMoneyExist(Long memberId) {
        UcWallet wallet = walletService.getWallet(memberId, WalletTypeEnum.QUANT_SERVICE);
        UcMember member = memberService.getById(memberId);
        BigDecimal balance = wallet.getBalance().add(wallet.getTicket());
        if (balance.compareTo(BigDecimal.ZERO) <= 0 || member.getUnPay().compareTo(BigDecimal.ZERO) == 1) {
            throw new IllegalArgumentException(localeMessageDecorateSourceService.getSystemMessage(ExceptionCodeConstant.QuantMemberService_001));
        }
    }

    @Override
    public CommonResult onChargeServiceFee(Long memberId, BigDecimal amount, String tid) {
        // 收取服务费
        Boolean charge = ucChargeService.charge(memberId, amount);
        if (!charge) {
            return CommonResult.failed();
        }
        return CommonResult.success();
    }
}

