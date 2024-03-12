package com.mzwise.modules.ucenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mzwise.modules.ucenter.dto.UcStatisticsParam;
import com.mzwise.modules.ucenter.entity.UcStatistics;
import com.mzwise.modules.ucenter.vo.UcStatisticsVo;
import org.apache.ibatis.annotations.Param;
import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author 666
 * @since 2022-08-01
 */
public interface UcStatisticsMapper extends BaseMapper<UcStatistics> {

    /**
     * 分页查看统计数据
     * @param page
     * @param beginTime
     * @param endTime
     * @return
     */
    Page<UcStatisticsVo> listAllStatistics(Page<UcStatisticsParam> page, @Param("beginTime")String beginTime, @Param("endTime")String endTime);

    /**
     *  新增每日数据统计
     */
    void resetEverydayDataStatistics();

    /**
     * 增加注册用户数
     */
    int addRegisterNum();

    /**
     * 增加网络充币数量
     * @param amount
     */
    int addRechargeOnline(@Param("amount") BigDecimal amount);

    /**
     * 增加后台充币数量
     * @param amount
     */
    int addRechargeBackstage(@Param("amount") BigDecimal amount);

    /**
     * 增加提币(成功)数量
     * @param amount
     */
    int addWithdrawSuccess(@Param("amount") BigDecimal amount);

    /**
     * 增加用户合约收益
     * @param amount
     */
    int addUserSwapProfit(@Param("amount") BigDecimal amount);

    /**
     * 增加公司收益
     * @param amount
     */
    int addCompanyProfit(@Param("amount") BigDecimal amount);

    /**
     * 减少公司收益
     * @param amount
     */
    int subtractCompanyProfit(@Param("amount") BigDecimal amount);

    /**
     * 增加合伙人佣金
     * @param amount
     */
    int addPartnerCommission(@Param("amount") BigDecimal amount);

}
