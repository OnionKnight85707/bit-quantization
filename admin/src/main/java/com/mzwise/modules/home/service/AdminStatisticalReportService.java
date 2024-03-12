package com.mzwise.modules.home.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.modules.quant.vo.AdminQuantOrderProfitGroupByCoinVO;
import com.mzwise.modules.quant.vo.AdminQuantProfitGroupByCoinAndPlatformVO;
import com.mzwise.modules.quant.vo.AdminStatQuantOrderEachDayVO;
import com.mzwise.modules.ucenter.vo.AdminStatDistributionProfitEachDayVO;
import com.mzwise.modules.ucenter.vo.AdminStatRechargeAndWithdrawalEachDayVO;

import java.sql.Date;
import java.util.List;

/**
 * @Author piao
 * @Date 2021/06/09
 */
public interface AdminStatisticalReportService {
    /**
     * 通过交易对统计量化收益
     *
     * @return
     */
    List<AdminQuantOrderProfitGroupByCoinVO> statQuantProfitGroupByCoin();

    /**
     * 通过交易对和平台 统计量化收益
     *
     * @return
     */
    List<AdminQuantProfitGroupByCoinAndPlatformVO> statQuantProfitGroupByCoinAndPlatform();

    /**
     * 统计每日出入金
     *
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<AdminStatRechargeAndWithdrawalEachDayVO> statRechargeAndWithdrawalEachDay(Date beginDate, Date endDate, Boolean showRecharge, Boolean showWithdrawal, Integer pageNum, Integer pageSize);

    /**
     * 统计每日订单收益
     *
     * @param beginDate
     * @param endDate
     * @param showProfit
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<AdminStatQuantOrderEachDayVO> statQuantOrderEachDay(Date beginDate,
                                                             Date endDate,
                                                             Boolean showProfit,
                                                             Integer pageNum,
                                                             Integer pageSize);

    /**
     * 统计每天量化分销收益
     *
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Deprecated
    Page<AdminStatDistributionProfitEachDayVO> statDistributionProfitByTypeEachDay(Date beginDate,
                                                                                   Date endDate,
                                                                                   Integer pageNum,
                                                                                   Integer pageSize);

    /**
     * 统计每天的量化分销收益
     *
     * @param beginDate
     * @param endDate
     * @param showShareProfit
     * @param showCommunityProfit
     * @param showDividendsProfit
     * @param showTotalProfit
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<AdminStatDistributionProfitEachDayVO> statDistributionProfitEachDay(Date beginDate,
                                                                             Date endDate,
                                                                             Boolean showShareProfit,
                                                                             Boolean showCommunityProfit,
                                                                             Boolean showDividendsProfit,
                                                                             Boolean showTotalProfit,
                                                                             Integer pageNum,
                                                                             Integer pageSize);
}
