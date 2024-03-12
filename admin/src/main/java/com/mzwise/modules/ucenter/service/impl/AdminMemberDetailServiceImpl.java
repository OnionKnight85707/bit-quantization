package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.common.exception.ApiException;
import com.mzwise.common.util.BalanceUtil;
import com.mzwise.constant.TransactionTypeEnum;
import com.mzwise.constant.UnitEnum;
import com.mzwise.constant.WalletTypeEnum;
import com.mzwise.modules.quant.entity.UcQuant;
import com.mzwise.modules.quant.mapper.UcQuantMapper;
import com.mzwise.modules.quant.service.UcQuantService;
import com.mzwise.modules.quant.vo.AdminQuantMemberVO;
import com.mzwise.modules.ucenter.dto.ManualDepositAndWithdrawalParam;
import com.mzwise.modules.ucenter.entity.UcWallet;
import com.mzwise.modules.ucenter.mapper.UcMemberMapper;
import com.mzwise.modules.ucenter.mapper.UcRechargeMapper;
import com.mzwise.modules.ucenter.mapper.UcWithdrawMapper;
import com.mzwise.modules.ucenter.service.AdminMemberDetailService;
import com.mzwise.modules.ucenter.service.UcProfitService;
import com.mzwise.modules.ucenter.service.UcWalletService;
import com.mzwise.modules.ucenter.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @Author piao
 * @Date 2021/05/31
 */
@Service
public class AdminMemberDetailServiceImpl implements AdminMemberDetailService {
    @Autowired
    private UcMemberMapper memberMapper;
    @Autowired
    private UcWalletService walletService;
    @Autowired
    private UcWithdrawMapper withdrawMapper;
    @Autowired
    private UcRechargeMapper rechargeMapper;
    @Autowired
    private UcProfitService profitService;
    @Autowired
    private UcQuantService quantRecordService;


    @Override
    public AdminMemberHomePageVO showHomePage(Long memberId) {
        return memberMapper.showMemberHomePage(memberId);
    }

    @Override
    public AdminMemberAssetsVO showMemberAssets(Long memberId) {
        BalanceVO totalBalance = walletService.getTotalBalance(memberId);
//        UcWallet quantWallet = walletService.getWallet(memberId, WalletTypeEnum.QUANT);
//        UcWallet bteWallet = walletService.getWallet(memberId, WalletTypeEnum.PLATFORM);
//        UcWallet quantCommunityWallet = walletService.getWallet(memberId, WalletTypeEnum.QUANT_COMMUNITY);
//        UcWallet bteDividendWallet = walletService.getWallet(memberId, WalletTypeEnum.PLATFORM_SHARE);
        UcWallet serviceFeeWallet = walletService.getWallet(memberId, WalletTypeEnum.QUANT_SERVICE);
//        if (quantWallet == null || bteWallet == null || quantCommunityWallet == null || bteDividendWallet == null || serviceFeeWallet == null) {
        if (serviceFeeWallet == null) {   //当前只有量化服务费钱包
            throw new ApiException("用户尚未开通钱包功能");
        }

        AdminMemberAssetsVO memberAssetsVO = new AdminMemberAssetsVO();
//        memberAssetsVO.setQuantifyAssets(quantWallet.getBalance());
//        memberAssetsVO.setBteAssets(bteWallet.getBalance());
        memberAssetsVO.setServiceFeeAssets(serviceFeeWallet.getBalance());
        memberAssetsVO.setQuantifyAssets(serviceFeeWallet.getBalance());
//        memberAssetsVO.setQuantifyCommunityAssets(quantCommunityWallet.getBalance());
//        memberAssetsVO.setBteDividendAssets(bteDividendWallet.getBalance());
        memberAssetsVO.setConvertedToUSDTAssets(totalBalance.getUsdt());
        memberAssetsVO.setConvertedToCNYAssets(totalBalance.getCny());
        return memberAssetsVO;
    }

    @Override
    public AdminMemberDepositAndWithdrawalVO showDepositAndWithdrawal(Long memberId) {
        AdminRechargeVO adminRechargeVO = rechargeMapper.calRechargeNumAndAmount(memberId);
        AdminWithdrawalVO adminWithdrawalVO = withdrawMapper.calWithdrawalNumAndAmount(memberId);

        AdminMemberDepositAndWithdrawalVO adminDepositAndWithdrawalVO = new AdminMemberDepositAndWithdrawalVO();
        adminDepositAndWithdrawalVO.setAmountOfRecharge(adminRechargeVO.getAmountOfRecharge());
        adminDepositAndWithdrawalVO.setNumberOfRecharge(adminRechargeVO.getNumberOfRecharge());
        adminDepositAndWithdrawalVO.setAmountOfWithdrawal(adminWithdrawalVO.getAmountOfWithdrawal());
        adminDepositAndWithdrawalVO.setNumberOfWithdrawal(adminWithdrawalVO.getNumberOfWithdrawal());
        return adminDepositAndWithdrawalVO;
    }

    @Override
    public AdminMemberProfitVO showMemberProfitVO(Long memberId) {
        AdminMemberProfitVO memberProfitVO = new AdminMemberProfitVO();
        UserProfitGeneralVO userProfitGeneralVO = profitService.general(memberId);
        UcQuant ucQuant = quantRecordService.getByMemberId(memberId);
        if (ucQuant == null) {
            ucQuant = new UcQuant();
            ucQuant.setMemberId(memberId);
            ucQuant.setExchangeTotalProfit(BigDecimal.ZERO);
            ucQuant.setExchangePosition(BigDecimal.ZERO);
            ucQuant.setSwapPosition(BigDecimal.ZERO);
        }
        memberProfitVO.setUserQuant(ucQuant);
        memberProfitVO.setUserProfitGeneralVO(userProfitGeneralVO);
        return memberProfitVO;
    }

    @Override
    public Boolean manualRecharge(ManualDepositAndWithdrawalParam param) {
        Long memberId = param.getMemberId();
        BigDecimal amount = param.getAmount();
        UnitEnum unitEnum = param.getUnitEnum();

        switch (unitEnum) {
            case USDT:
                return BalanceUtil.increaseBalance(memberId, WalletTypeEnum.QUANT, amount, TransactionTypeEnum.ADMIN_DEPOSIT);
            case BTE:
                return BalanceUtil.increaseBalance(memberId, WalletTypeEnum.PLATFORM, amount, TransactionTypeEnum.ADMIN_DEPOSIT);
            default:
                throw new ApiException("不支持该币种");
        }
    }

    @Override
    public Boolean manualWithdrawal(ManualDepositAndWithdrawalParam param) {
        Long memberId = param.getMemberId();
        BigDecimal amount = param.getAmount();
        UnitEnum unitEnum = param.getUnitEnum();
        UcWallet quantWallet = walletService.getWallet(memberId, WalletTypeEnum.QUANT);
        UcWallet bteWallet = walletService.getWallet(memberId, WalletTypeEnum.PLATFORM);

        switch (unitEnum) {
            case USDT:
                if (quantWallet.getBalance().compareTo(amount) < 0) {
                    throw new ApiException("该用户可用USDT余额不足");
                }
                return BalanceUtil.decreaseBalance(memberId, WalletTypeEnum.QUANT, amount, TransactionTypeEnum.ADMIN_DEPOSIT);
            case BTE:
                if (bteWallet.getBalance().compareTo(amount) < 0) {
                    throw new ApiException("该用户可用BTE余额不足");
                }
                return BalanceUtil.decreaseBalance(memberId, WalletTypeEnum.PLATFORM, amount, TransactionTypeEnum.ADMIN_DEPOSIT);
            default:
                throw new ApiException("不支持该币种");
        }
    }




}
