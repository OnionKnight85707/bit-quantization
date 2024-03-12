package com.mzwise.modules.ucenter.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.modules.ucenter.entity.UcRecharge;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzwise.modules.ucenter.vo.AdminRechargeVO;
import com.mzwise.modules.ucenter.vo.AdminStatRechargeAndWithdrawalEachDayVO;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2021-03-31
 */
public interface UcRechargeMapper extends BaseMapper<UcRecharge> {
    /**
     * 查询某个会员的总充值金额及笔数
     *
     * @param memberId
     * @return
     */
    AdminRechargeVO calRechargeNumAndAmount(@Param("memberId") Long memberId);

    /**
     * 统计每日出入金
     *
     * @param beginDate
     * @param endDate
     * @param days
     * @param page
     * @param showRecharge
     * @param showWithdrawal
     * @return
     */
    Page<AdminStatRechargeAndWithdrawalEachDayVO> statRechargeAndWithdrawalEachDay(Page<AdminStatRechargeAndWithdrawalEachDayVO> page,
                                                                                   @Param("beginDate") Date beginDate,
                                                                                   @Param("endDate") Date endDate,
                                                                                   @Param("days") Long days,
                                                                                   @Param("showRecharge") Boolean showRecharge,
                                                                                   @Param("showWithdrawal") Boolean showWithdrawal);

}
