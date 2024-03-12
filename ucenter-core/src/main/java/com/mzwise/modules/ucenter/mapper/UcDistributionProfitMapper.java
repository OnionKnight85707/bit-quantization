package com.mzwise.modules.ucenter.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.constant.DistributionProfitTypeEnum;
import com.mzwise.modules.ucenter.entity.UcDistributionProfit;
import com.mzwise.modules.ucenter.vo.AdminDistributionProfitRecordVO;
import com.mzwise.modules.ucenter.vo.AdminStatDistributionGroupByDayAndTypeVO;
import com.mzwise.modules.ucenter.vo.AdminStatDistributionProfitEachDayVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-05-25
 */
public interface UcDistributionProfitMapper extends BaseMapper<UcDistributionProfit> {

    /**
     * 根据类型统计今日的量化分销收益
     *
     * @param memberId
     * @param typeEnum
     * @return
     */
    BigDecimal statTodayProfitByType(@Param("memberId") Long memberId, @Param("typeEnum") DistributionProfitTypeEnum typeEnum);

    @Deprecated
    Page<AdminStatDistributionGroupByDayAndTypeVO> statDistributionProfitGroupByTypeEachDay(Page<AdminStatDistributionGroupByDayAndTypeVO> page,
                                                                                            @Param("beginDate") Date beginDate,
                                                                                            @Param("endDate") Date endDate,
                                                                                            @Param("days") Long days,
                                                                                            @Param("type") Integer type);

    /**
     * 统计每天量化分销收益
     *
     * @param page
     * @param beginDate
     * @param endDate
     * @param days
     * @param showShareProfit
     * @param showCommunityProfit
     * @param showDividendsProfit
     * @param showTotalProfit
     * @return
     */
    Page<AdminStatDistributionProfitEachDayVO> statDistributionProfitEachDay(Page<AdminStatDistributionProfitEachDayVO> page,
                                                                             @Param("beginDate") Date beginDate,
                                                                             @Param("endDate") Date endDate,
                                                                             @Param("days") Long days,
                                                                             @Param("showShareProfit") Boolean showShareProfit,
                                                                             @Param("showCommunityProfit") Boolean showCommunityProfit,
                                                                             @Param("showDividendsProfit") Boolean showDividendsProfit,
                                                                             @Param("showTotalProfit") Boolean showTotalProfit);

    /**
     * 分页条件查询量化分销记录
     *
     * @param page
     * @param nickname
     * @param phone
     * @param email
     * @param wrapper
     * @return
     */
    Page<AdminDistributionProfitRecordVO> listProfitRecord(Page<AdminDistributionProfitRecordVO> page,
                                                           @Param("nickname") String nickname,
                                                           @Param("phone") String phone,
                                                           @Param("email") String email,
                                                           @Param(Constants.WRAPPER) Wrapper wrapper);
}
