package com.mzwise.modules.distribution.service.Impl;

import com.mzwise.common.component.RatePool;
import com.mzwise.common.util.BalanceUtil;
import com.mzwise.common.util.WalletProfitUtil;
import com.mzwise.constant.DistributionBTECommRulesConstant;
import com.mzwise.constant.TransactionTypeEnum;
import com.mzwise.constant.WalletTypeEnum;
import com.mzwise.modules.distribution.service.BTECommunityAwardService;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.service.UcBteDividendsRecordService;
import com.mzwise.modules.ucenter.service.UcHistoryTotalProfitService;
import com.mzwise.modules.ucenter.service.UcMemberService;
import com.mzwise.modules.ucenter.service.UcServiceChargeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @Author piao
 * @Date 2021/05/24
 */
@Service
public class BTECommunityAwardServiceImpl implements BTECommunityAwardService {
    @Autowired
    private UcServiceChargeRecordService serviceChargeRecordService;
    @Autowired
    private UcMemberService memberService;
    @Autowired
    private RatePool ratePool;
    @Autowired
    private UcBteDividendsRecordService bteDividendsRecordService;
    @Autowired
    private UcHistoryTotalProfitService historyTotalProfitService;

    @Override
    public void addBTECommunityAward() {
        BigDecimal serviceChargeLastWeek = serviceChargeRecordService.sumServiceChargeLastWeek();
        if (serviceChargeLastWeek.compareTo(BigDecimal.ZERO) < 0) {
            return;
        }
        List<UcMember> ucMembers = memberService.queryAllEffectiveMember();
        if (ucMembers.size() == 0) {
            return;
        }
        BigDecimal amountPerPerson = serviceChargeLastWeek.divide(BigDecimal.valueOf(ucMembers.size()), 4, RoundingMode.FLOOR);
        BigDecimal rate = ratePool.getRate("USDT", "BTE");
        BigDecimal bteProfit = amountPerPerson.multiply(rate).multiply(DistributionBTECommRulesConstant.BTE_COMMUNITY_RATE);
        if (bteProfit.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }
        for (UcMember ucMember : ucMembers) {
            BalanceUtil.increaseBalance(ucMember.getId(), WalletTypeEnum.PLATFORM_SHARE, bteProfit, TransactionTypeEnum.QUANTITATIVE_PLATFORM_CURRENCY_DIVIDENDS);
            // 增加平台币收益记录
            bteDividendsRecordService.create(ucMember.getId(), bteProfit);
            // 增加平台币 今日收益及累计收益
            WalletProfitUtil.add(ucMember.getId(), WalletTypeEnum.PLATFORM_SHARE, bteProfit);
            // 增加平台币 累计收益 暂不用
//            historyTotalProfitService.savaOrUpdate(ucMember.getId(), WalletTypeEnum.PLATFORM_SHARE, bteProfit);
        }
    }
}
