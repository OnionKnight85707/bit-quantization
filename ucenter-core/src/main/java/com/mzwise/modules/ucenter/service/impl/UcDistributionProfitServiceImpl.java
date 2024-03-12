package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.constant.DistributionProfitTypeEnum;
import com.mzwise.constant.UnitEnum;
import com.mzwise.constant.WalletTypeEnum;
import com.mzwise.modules.ucenter.entity.UcDistributionProfit;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.entity.UcWallet;
import com.mzwise.modules.ucenter.mapper.UcDistributionProfitMapper;
import com.mzwise.modules.ucenter.service.UcDistributionProfitService;
import com.mzwise.modules.ucenter.service.UcHistoryTotalProfitService;
import com.mzwise.modules.ucenter.service.UcMemberService;
import com.mzwise.modules.ucenter.service.UcWalletService;
import com.mzwise.modules.ucenter.vo.QuantifiedCommissionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-05-25
 */
@Service
public class UcDistributionProfitServiceImpl extends ServiceImpl<UcDistributionProfitMapper, UcDistributionProfit> implements UcDistributionProfitService {
    @Autowired
    private UcMemberService memberService;
    @Autowired
    private UcDistributionProfitMapper distributionProfitMapper;
    @Autowired
    private UcWalletService walletService;
    @Autowired
    private UcHistoryTotalProfitService historyTotalProfitService;

    @Override
    public void create(Long memberId, Long benefitMemberId, BigDecimal amount, DistributionProfitTypeEnum type) {
        UcDistributionProfit distributionProfit = new UcDistributionProfit();
        UcMember member = memberService.getById(memberId);

        UcMember benefitMember = memberService.getById(benefitMemberId);
        if (member == null || benefitMember == null) {
            return;
        }
        distributionProfit.setMemberId(memberId);
        distributionProfit.setMemberNickname(member.getNickname());
        distributionProfit.setBenefitMemberId(benefitMember.getId());
        distributionProfit.setBenefitMemberNickname(benefitMember.getNickname());
        distributionProfit.setBenefitAmount(amount);
        distributionProfit.setUnit(UnitEnum.USDT);
        distributionProfit.setType(type);
        save(distributionProfit);
    }

    @Override
    public QuantifiedCommissionVO showQuantifiedCommission(Long memberId) {
        QuantifiedCommissionVO quantifiedCommissionVO = new QuantifiedCommissionVO();
        BigDecimal todayShare = distributionProfitMapper.statTodayProfitByType(memberId, DistributionProfitTypeEnum.SHARE);
        BigDecimal todayCommunity = distributionProfitMapper.statTodayProfitByType(memberId, DistributionProfitTypeEnum.COMMUNITY);
        BigDecimal todayDividends = distributionProfitMapper.statTodayProfitByType(memberId, DistributionProfitTypeEnum.DIVIDENDS);
        BigDecimal usdtBalance = BigDecimal.ZERO;
        BigDecimal historyTotalProfitAmount = BigDecimal.ZERO;

        UcWallet wallet = walletService.getWallet(memberId, WalletTypeEnum.QUANT_COMMUNITY);
        if (wallet != null) {
            usdtBalance = wallet.getBalance();
            historyTotalProfitAmount = wallet.getTotalProfit();
        }
        quantifiedCommissionVO.setTodaySharingAward(todayShare);
        quantifiedCommissionVO.setTodayCommunityAward(todayCommunity);
        quantifiedCommissionVO.setTodayDividendAward(todayDividends);
        quantifiedCommissionVO.setQuantifyCommunityBalance(usdtBalance);
        quantifiedCommissionVO.setHistoryQuantifyCommunityProfit(historyTotalProfitAmount);

        return quantifiedCommissionVO;
    }


}
