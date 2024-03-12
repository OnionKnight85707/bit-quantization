package com.mzwise.modules.ucenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.modules.ucenter.entity.UcProfitDetail;
import com.mzwise.modules.ucenter.vo.DailyEarningsStatisticsVo;
import com.mzwise.modules.ucenter.vo.ProfitDetailsVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 用户收益详情mapper
 * @Author LiangZaiChao
 * @Date 2022/7/5 11:46
 */
public interface UcProfitDetailMapper extends BaseMapper<UcProfitDetail> {

    /**
     * 查询昨天交易汇总
     * @return
     */
    List<DailyEarningsStatisticsVo> selYesterdayEarningsStatistics();

    /**
     * 设置全部为已扣费
     * @param memberId
     */
    void setUpAllDeductions(@Param("memberId") Long memberId);

    /**
     * 总盈利（查询全部大于0的收益）
     * @param quantId
     * @return
     */
    BigDecimal queryAllProfit(@Param("quantId") Long quantId);

    /**
     * 收益详情
     * @param page
     * @param memberId
     * @return
     */
    Page<ProfitDetailsVo> profitDetails(Page<ProfitDetailsVo> page, @Param("memberId") Long memberId);

    /**
     * 按日统计收益
     * @param page
     * @param memberId
     * @return
     */
    Page<ProfitDetailsVo> profitDetailsForDay(Page<ProfitDetailsVo> page, @Param("memberId") Long memberId);

    /**
     * 月度统计收益
     * @param page
     * @param memberId
     * @return
     */
    Page<ProfitDetailsVo> profitDetailsForMonth(Page<ProfitDetailsVo> page, @Param("memberId") Long memberId);

    /**
     * 年度统计收益
     * @param page
     * @param memberId
     * @return
     */
    Page<ProfitDetailsVo> profitDetailsForYear(Page<ProfitDetailsVo> page, @Param("memberId") Long memberId);

}
