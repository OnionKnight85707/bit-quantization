package com.mzwise.modules.quant.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.modules.quant.entity.UcQuant;
import com.mzwise.modules.quant.mapper.UcQuantMapper;
import com.mzwise.modules.quant.service.AdminQuantRankService;
import com.mzwise.modules.quant.service.UcQuantService;
import com.mzwise.modules.quant.vo.QuantRankVO;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class AdminQuantRankServiceImpl extends ServiceImpl<UcQuantMapper, UcQuant> implements AdminQuantRankService {

    @Value("${rank.id.list}")
    private String rankIdList;

    @Autowired
    private UcQuantService ucQuantService;

    /**
     * 查询排行榜用户
     *
     * @return
     */
    @Override
    public QuantRankVO queryRank() {
        QuantRankVO vo = new QuantRankVO();
        String[] s = rankIdList.split("#");
        Long[] ids = (Long[]) ConvertUtils.convert(s,Long.class);
        List<Long> idList = new ArrayList<>(Arrays.asList(ids));
        List<UcQuant> rankList = ucQuantService.listByIds(idList);
        vo.setRankList(rankList);
        return vo;
    }

    /**
     * 修改排行榜用户
     * @param memberId
     * @param swapTodayProfit
     * @param swapTotalProfit
     */
    public void modifyRank(Long memberId, BigDecimal swapTodayProfit, BigDecimal swapTotalProfit) {
        this.baseMapper.modifyRank(memberId, swapTodayProfit, swapTotalProfit);
    }

    /**
     * 随机修改排行榜全部用户
     *
     * @param memberId
     * @param swapTodayProfit
     * @param swapTotalProfit
     */
    @Override
    public void addRankProfit(Long memberId, BigDecimal swapTodayProfit, BigDecimal swapTotalProfit) {
        this.baseMapper.addRankProfit(memberId, swapTodayProfit, swapTotalProfit);
    }

}
