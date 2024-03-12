package com.mzwise.modules.quant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.modules.quant.entity.UcQuant;
import com.mzwise.modules.quant.vo.QuantRankVO;

import java.math.BigDecimal;


public interface AdminQuantRankService extends IService<UcQuant> {

    /**
     * 查询排行榜用户
     * @return
     */
    QuantRankVO queryRank();

    /**
     * 修改排行榜用户
     * @param memberId
     * @param swapTodayProfit
     * @param swapTotalProfit
     */
    void modifyRank(Long memberId, BigDecimal swapTodayProfit, BigDecimal swapTotalProfit);

    /**
     * 随机修改排行榜全部用户
     * @param memberId
     * @param swapTodayProfit
     * @param swapTotalProfit
     */
    void addRankProfit(Long memberId, BigDecimal swapTodayProfit, BigDecimal swapTotalProfit);

}
