package com.mzwise.modules.distribution.service.Impl;

import com.mzwise.common.util.BalanceUtil;
import com.mzwise.common.util.WalletProfitUtil;
import com.mzwise.constant.*;
import com.mzwise.modules.distribution.service.DividendsService;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.service.UcDistributionProfitService;
import com.mzwise.modules.ucenter.service.UcHistoryTotalProfitService;
import com.mzwise.modules.ucenter.service.UcMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * @Author piao
 * @Date 2021/05/21
 */
@Service
public class DividendsServiceImpl implements DividendsService {
    @Autowired
    private UcMemberService memberService;
    @Autowired
    private UcDistributionProfitService distributionProfitService;
    @Autowired
    private UcHistoryTotalProfitService historyTotalProfitService;

    @Override
    @Async
    public void addDividendsProfit(Long memberId, BigDecimal amount) {
        List<UcMember> stockholderList = memberService.queryByQuantificationLevel(QuantificationLevelEnum.LEVEL6);
        List<UcMember> directorList = memberService.queryByQuantificationLevel(QuantificationLevelEnum.LEVEL7);
        for (UcMember stockholder : stockholderList) {
            BigDecimal stockholderProfit = amount.multiply(DistributionDividendsRulesConstant.RATE_OF_STOCKHOLDER).divide(BigDecimal.valueOf(stockholderList.size()), 4, RoundingMode.FLOOR);
            if (stockholderProfit.compareTo(BigDecimal.ZERO) > 0) {
                BalanceUtil.increaseBalance(stockholder.getId(), WalletTypeEnum.QUANT_COMMUNITY, stockholderProfit, TransactionTypeEnum.DIVIDENDS);
                // 增加收益记录
                distributionProfitService.create(memberId, stockholder.getId(), stockholderProfit, DistributionProfitTypeEnum.DIVIDENDS);
                // 增加今日收益及历史收益
                WalletProfitUtil.add(stockholder.getId(), WalletTypeEnum.QUANT_COMMUNITY, stockholderProfit);
                // 增加历史收益 暂注掉
//                historyTotalProfitService.savaOrUpdate(stockholder.getId(), WalletTypeEnum.QUANT_COMMUNITY, stockholderProfit);
            }
        }
        for (UcMember director : directorList) {
            BigDecimal directorProfit = amount.multiply(DistributionDividendsRulesConstant.RATE_OF_DIRECTOR.divide(BigDecimal.valueOf(directorList.size()), 4, RoundingMode.FLOOR));
            if (directorProfit.compareTo(BigDecimal.ZERO) > 0) {
                BalanceUtil.increaseBalance(director.getId(), WalletTypeEnum.QUANT_COMMUNITY, directorProfit, TransactionTypeEnum.DIVIDENDS);
                // 增加收益记录
                distributionProfitService.create(memberId, director.getId(), directorProfit, DistributionProfitTypeEnum.DIVIDENDS);
                // 增加今日收益及历史收益
                WalletProfitUtil.add(director.getId(), WalletTypeEnum.QUANT_COMMUNITY, directorProfit);
                // 增加历史收益 暂注掉
//                historyTotalProfitService.savaOrUpdate(director.getId(), WalletTypeEnum.QUANT_COMMUNITY, directorProfit);
            }
        }
    }
}
