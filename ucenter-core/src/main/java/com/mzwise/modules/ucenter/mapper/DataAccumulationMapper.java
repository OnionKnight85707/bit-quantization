package com.mzwise.modules.ucenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mzwise.modules.ucenter.entity.DataAccumulation;

import java.math.BigDecimal;

public interface DataAccumulationMapper extends BaseMapper<DataAccumulation> {


    /**
     *   累计注册用户
     * @return
     */
    Long registeredTotal();

    /**
     *  累计开启策略用户
     * @return
     */
    Long openStrategyUsersTotal();

    /**
     *  累计做单收益
     * @return
     */
    BigDecimal orderProfitTotal();

    /**
     *  累计网络充值数量
     * @return
     */
    BigDecimal networkRechargeTotal();

    /**
     *  累计后台充值数量
     * @return
     */
    BigDecimal managementRechargeTotal();

    /**
     * 公司收益 (钱包表platform = 1 的 balance )
     * @return
     */
    BigDecimal getAllBalance();

    /**
     * 用户钱包累计余额 （钱包表的balance）
     * @return
     */
    BigDecimal getAllUserBalance();

    /**
     * 累计提币数量 (uc_withdraw status为成功的)
     * @return
     */
    BigDecimal getAllWithdrawalAmount();

    /**
     * 累计合伙人分红数量 (uc_member表的partner_total_commission)
     * @return
     */
    BigDecimal getAllPartnerTotalCommission();

    /**
     * 累计赠送USDT
     * @return
     */
    BigDecimal getAllGivenUSDT();

    /**
     * 用户钱包累计赠送券余额
     * @return
     */
    BigDecimal getAllTotalTicket();

}
