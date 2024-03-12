package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.modules.ucenter.entity.UcProfitDetail;
import com.mzwise.modules.ucenter.vo.DailyEarningsStatisticsVo;
import com.mzwise.modules.ucenter.vo.ProfitDetailsVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 用户收益详情接口
 * @Author LiangZaiChao
 * @Date 2022/7/5 11:43
 */
public interface UcProfitDetailService extends IService<UcProfitDetail> {

    /**
     * 记录收益详情
     * @param serviceFeeIsSuccess 服务费是否收取成功：true：成功， false：失败
     * @param memberId 用户id
     * @param quantId 量化id
     * @param orderId 订单id
     * @param profit 利润
     * @param amount 金额
     */
    void recordDetail(boolean serviceFeeIsSuccess, Long memberId, Long quantId, String orderId, BigDecimal profit, BigDecimal amount);

    /**
     * 查询昨天交易汇总
     * @return
     */
    List<DailyEarningsStatisticsVo> findYesterdayEarningsStatistics();

    /**
     * 补缴欠款
     * @param memberId 用户id
     * @param isProcessPartnerCommission 是否处理合伙人佣金
     * @return 补发是否成功：true成功
     */
    void repayArrears(Long memberId, boolean isProcessPartnerCommission);

    /**
     * 收益详情
     * @param pageNum
     * @param pageSize
     * @param memberId
     * @return
     */
    Page<ProfitDetailsVo> profitDetails(Integer pageNum, Integer pageSize, Long memberId);

    /**
     * 按日统计收益
     * @param pageNum
     * @param pageSize
     * @param memberId
     * @return
     */
    Page<ProfitDetailsVo> profitDetailsForDay(Integer pageNum, Integer pageSize, Long memberId);

    /**
     * 月度统计收益
     * @param pageNum
     * @param pageSize
     * @param memberId
     * @return
     */
    Page<ProfitDetailsVo> profitDetailsForMonth(Integer pageNum, Integer pageSize, Long memberId);

    /**
     * 年度统计收益
     * @param pageNum
     * @param pageSize
     * @param memberId
     * @return
     */
    Page<ProfitDetailsVo> profitDetailsForYear(Integer pageNum, Integer pageSize, Long memberId);

}
