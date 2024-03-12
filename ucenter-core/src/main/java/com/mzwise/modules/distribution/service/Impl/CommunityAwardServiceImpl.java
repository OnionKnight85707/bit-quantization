package com.mzwise.modules.distribution.service.Impl;


import com.mzwise.common.util.BalanceUtil;
import com.mzwise.common.util.WalletProfitUtil;
import com.mzwise.constant.DistributionProfitTypeEnum;
import com.mzwise.constant.QuantificationLevelEnum;
import com.mzwise.constant.TransactionTypeEnum;
import com.mzwise.constant.WalletTypeEnum;
import com.mzwise.modules.distribution.service.CommunityAwardService;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.service.UcDistributionProfitService;
import com.mzwise.modules.ucenter.service.UcHistoryTotalProfitService;
import com.mzwise.modules.ucenter.service.UcMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;


/**
 * @Author piao
 * @Date 2021/05/21
 */
@Service
public class CommunityAwardServiceImpl implements CommunityAwardService {
    @Autowired
    private UcMemberService memberService;
    @Autowired
    private UcDistributionProfitService distributionProfitService;

    @Override
    @Async
    public void addCommunityAward(Long memberId, BigDecimal amount) {
        LocalDateTime beginDate = LocalDateTime.now();
        System.out.println("开始执行 增加社区分红方法");
        BigDecimal subRate = BigDecimal.ZERO;
        UcMember member = memberService.getById(memberId);
        if (member == null) {
            return;
        }
        while (member.getParentId() != null && member.getParentId() != 0L) {
            UcMember supMember = memberService.getById(member.getParentId());
            if (supMember != null) {
                // 若上级为无效用户则继续
                if (!supMember.getIsEffective()) {
                    continue;
                }
                QuantificationLevelEnum supLevel = supMember.getQuantificationLevel();
                BigDecimal supRate = QuantificationLevelEnum.getCommunityAwardRateByLevel(supLevel);
                if (supRate.compareTo(subRate) > 0) {
                    BigDecimal actualRate = supRate.subtract(subRate);
                    BigDecimal profitAmount = actualRate.multiply(amount);
                    if (profitAmount.compareTo(BigDecimal.ZERO) > 0) {
                        BalanceUtil.increaseBalance(supMember.getId(), WalletTypeEnum.QUANT_COMMUNITY, profitAmount, TransactionTypeEnum.COMMUNITY_AWARD);
                        // 收益记录
                        distributionProfitService.create(memberId, supMember.getId(), profitAmount, DistributionProfitTypeEnum.COMMUNITY);
                        // 增加今日收益及历史收益
                        WalletProfitUtil.add(supMember.getId(), WalletTypeEnum.QUANT_COMMUNITY, profitAmount);
                        // 增加历史收益 暂注掉
//                        historyTotalProfitService.savaOrUpdate(supMember.getId(), WalletTypeEnum.QUANT_COMMUNITY, profitAmount);
                    }
                    subRate = supRate;
                }
                member = supMember;
            }
        }
        LocalDateTime endDate = LocalDateTime.now();
        System.out.println("结束执行 增加社区分红方法,方法耗时： " + Duration.between(beginDate, endDate).getSeconds());
    }


}
