package com.mzwise.modules.ucenter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mzwise.modules.ucenter.dto.UcStatisticsParam;
import com.mzwise.modules.ucenter.entity.UcStatistics;
import com.mzwise.modules.ucenter.vo.UcStatisticsVo;

import java.math.BigDecimal;

/**
 * <p>
 *
 * </p>
 *
 * @author 666
 * @since 2022-08-01
 */
public interface UcStatisticsService extends IService<UcStatistics> {

    /**
     * 分页查看统计数据
     * @param param
     * @return
     */
    Page<UcStatisticsVo> listAllStatistics(UcStatisticsParam param);

    /**
     *  新增每日数据统计
     */
    void resetEverydayDataStatistics();

    /**
     * 增加注册用户数
     */
    void addRegisterNum();

    /**
     * 增加网络充币数量
     * @param amount
     */
    void addRechargeOnline(BigDecimal amount);

    /**
     * 增加后台充币数量
     * @param amount
     */
    void addRechargeBackstage(BigDecimal amount);

    /**
     * 增加提币(成功)数量
     * @param amount
     */
    void addWithdrawSuccess(BigDecimal amount);

    /**
     * 增加用户合约收益
     * @param amount
     */
    void addUserSwapProfit(BigDecimal amount);

    /**
     * 增加公司收益
     * @param amount
     */
    void addCompanyProfit(BigDecimal amount);

    /**
     * 减少公司收益
     * @param amount
     */
    void subtractCompanyProfit(BigDecimal amount);

    /**
     * 增加合伙人佣金
     * @param amount
     */
    void addPartnerCommission(BigDecimal amount);

}
