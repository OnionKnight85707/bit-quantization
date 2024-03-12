package com.mzwise.modules.distribution.service.Impl;

import com.mzwise.common.util.BalanceUtil;
import com.mzwise.common.util.WalletProfitUtil;
import com.mzwise.constant.DistributionProfitTypeEnum;
import com.mzwise.constant.DistributionShareRulesConstant;
import com.mzwise.constant.TransactionTypeEnum;
import com.mzwise.constant.WalletTypeEnum;
import com.mzwise.modules.distribution.service.ShareService;
import com.mzwise.modules.ucenter.entity.UcMember;
import com.mzwise.modules.ucenter.service.UcDistributionProfitService;
import com.mzwise.modules.ucenter.service.UcHistoryTotalProfitService;
import com.mzwise.modules.ucenter.service.UcMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;

/**
 * @Author piao
 * @Date 2021/05/21
 */
@Service
public class ShareServiceImpl implements ShareService {
    @Autowired
    private UcMemberService memberService;
    @Autowired
    private UcDistributionProfitService distributionProfitService;
    @Autowired
    private UcHistoryTotalProfitService historyTotalProfitService;

    @Override
    @Async
    public void addShareAmount(Long subMemberId, BigDecimal amount) {
        UcMember member = memberService.getById(subMemberId);
        Assert.notNull(member, "member is null");
        Long parentId = member.getParentId();
        // 如果有上级且上级满足直推人数条件 上级获得一定比例分享收益
        if (parentId != null && !parentId.equals(0L)) {
            UcMember supMember = memberService.getById(parentId);
            if (supMember != null && supMember.getIsEffective() && supMember.getFirstLevelNum() >= DistributionShareRulesConstant.DIRECT_PUSH_NUMBER_FOR_FIRST) {
                BigDecimal level1Profit = amount.multiply(DistributionShareRulesConstant.SHARE_RATE_OF_LEVEL1);
                if (level1Profit.compareTo(BigDecimal.ZERO) > 0) {
                    BalanceUtil.increaseBalance(supMember.getId(), WalletTypeEnum.QUANT_COMMUNITY, level1Profit, TransactionTypeEnum.SHARE_PROFIT);
                    // 增加分享记录
                    distributionProfitService.create(subMemberId, supMember.getId(), level1Profit, DistributionProfitTypeEnum.SHARE);
                    // 增加今日及历史总收益
                    WalletProfitUtil.add(supMember.getId(), WalletTypeEnum.QUANT_COMMUNITY, level1Profit);
                    // 增加历史收益 暂注掉
                    //historyTotalProfitService.savaOrUpdate(supMember.getId(), WalletTypeEnum.QUANT_COMMUNITY, level1Profit);
                }
            }

            // 如果有上级的上级 且上级的上级满足直推人数条件 上级获得一定比例分享收益
            if (supMember != null && supMember.getParentId() != null && supMember.getParentId() != 0L) {
                UcMember supMember2 = memberService.getById(supMember.getParentId());
                if (supMember2 != null && supMember2.getIsEffective() && supMember2.getFirstLevelNum() >= DistributionShareRulesConstant.DIRECT_PUSH_NUMBER_FOR_SECOND) {
                    BigDecimal level2Profit = amount.multiply(DistributionShareRulesConstant.SHARE_RATE_OF_LEVEL2);
                    if (level2Profit.compareTo(BigDecimal.ZERO) > 0) {
                        BalanceUtil.increaseBalance(supMember2.getId(), WalletTypeEnum.QUANT_COMMUNITY, level2Profit, TransactionTypeEnum.SHARE_PROFIT);
                        // 增加分享记录
                        distributionProfitService.create(subMemberId, supMember2.getId(), level2Profit, DistributionProfitTypeEnum.SHARE);
                        // 增加今日及历史总收益
                        WalletProfitUtil.add(supMember2.getId(), WalletTypeEnum.QUANT_COMMUNITY, level2Profit);
                        // 增加历史收益 暂注掉
//                        historyTotalProfitService.savaOrUpdate(supMember2.getId(), WalletTypeEnum.QUANT_COMMUNITY, level2Profit);
                    }
                }
            }
        }
    }


}
