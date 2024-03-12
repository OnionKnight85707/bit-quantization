package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.constant.WalletTypeEnum;
import com.mzwise.modules.ucenter.entity.UcHistoryTotalProfit;
import com.mzwise.modules.ucenter.mapper.UcHistoryTotalProfitMapper;
import com.mzwise.modules.ucenter.service.UcHistoryTotalProfitService;
import com.mzwise.modules.ucenter.service.UcMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author admin
 * @since 2021-05-27
 */
@Service
public class UcHistoryTotalProfitServiceImpl extends ServiceImpl<UcHistoryTotalProfitMapper, UcHistoryTotalProfit> implements UcHistoryTotalProfitService {
    @Autowired
    private UcHistoryTotalProfitMapper historyTotalProfitMapper;
    @Autowired
    private UcMemberService memberService;

    @Override
    public void savaOrUpdate(Long memberId, WalletTypeEnum walletTypeEnum, BigDecimal amount) {
        UcHistoryTotalProfit historyTotalProfit = getByMemberId(memberId);
        if (historyTotalProfit == null) {
            historyTotalProfit = new UcHistoryTotalProfit();
            historyTotalProfit.setMemberId(memberId);
            save(historyTotalProfit);
        }
        switch (walletTypeEnum) {
            case QUANT_COMMUNITY:
                BigDecimal totalCommunityProfit = historyTotalProfit.getTotalCommunityProfit();
                historyTotalProfit.setTotalCommunityProfit(totalCommunityProfit.add(amount));
                break;
            case PLATFORM_SHARE:
                BigDecimal totalBteCommunityProfit = historyTotalProfit.getTotalBteCommunityProfit();
                historyTotalProfit.setTotalBteCommunityProfit(totalBteCommunityProfit.add(amount));
                break;
            default:
                break;
        }
        updateById(historyTotalProfit);
    }

    @Override
    public UcHistoryTotalProfit getByMemberId(Long memberId) {
        QueryWrapper<UcHistoryTotalProfit> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UcHistoryTotalProfit::getMemberId, memberId);
        return getOne(wrapper);
    }

    @Override
    public BigDecimal calMyTeamHistoryTotalProfit(List<Long> memberIdList) {
        return historyTotalProfitMapper.calMyTeamHistoryTotalProfit(memberIdList);
    }

    @Override
    public BigDecimal calMyTeamBTEHistoryTotalProfit(List<Long> memberIdList) {
        return historyTotalProfitMapper.calMyTeamBTEHistoryTotalProfit(memberIdList);
    }


}
