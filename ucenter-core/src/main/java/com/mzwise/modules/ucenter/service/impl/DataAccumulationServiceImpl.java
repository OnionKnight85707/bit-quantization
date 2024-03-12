package com.mzwise.modules.ucenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mzwise.modules.ucenter.entity.DataAccumulation;
import com.mzwise.modules.ucenter.mapper.DataAccumulationMapper;
import com.mzwise.modules.ucenter.service.DataAccumulationService;
import com.mzwise.modules.ucenter.service.UcMemberService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DataAccumulationServiceImpl extends ServiceImpl<DataAccumulationMapper, DataAccumulation> implements DataAccumulationService {

    @Autowired
    private UcMemberService ucMemberService;
//    @Autowired
//    private QuantStrategyService

    /**
     *  累计注册用户
     * @return
     */
    @Override
    public Long registeredTotal() {
        return baseMapper.registeredTotal();
    }

    /**
     *  累计开启策略用户
     * @return
     */
    @Override
    public Long openStrategyUsersTotal() {
        return baseMapper.openStrategyUsersTotal();
    }

    /**
     *  累计做单收益
     * @return
     */
    @Override
    public BigDecimal orderProfitTotal() {
        return baseMapper.orderProfitTotal();
    }

    /**
     *  累计网络充值数量
     * @return
     */
    @Override
    public BigDecimal networkRechargeTotal() {
        return baseMapper.networkRechargeTotal();
    }

    /**
     *  累计后台充值数量
     * @return
     */
    @Override
    public BigDecimal managementRechargeTotal() {
        return baseMapper.managementRechargeTotal();
    }

    /**
     * 公司收益 (钱包表platform = 1 的 balance )
     *
     * @return
     */
    @Override
    public BigDecimal getAllBalance() {
        return this.baseMapper.getAllBalance();
    }

    /**
     * 用户钱包累计余额 （钱包表的balance）
     *
     * @return
     */
    @Override
    public BigDecimal getAllUserBalance() {
        return this.baseMapper.getAllUserBalance();
    }

    /**
     * 累计提币数量 (uc_withdraw status为成功的)
     *
     * @return
     */
    @Override
    public BigDecimal getAllWithdrawalAmount() {
        return this.baseMapper.getAllWithdrawalAmount();
    }

    /**
     * 累计合伙人分红数量 (uc_member表的partner_total_commission)
     *
     * @return
     */
    @Override
    public BigDecimal getAllPartnerTotalCommission() {
        return this.baseMapper.getAllPartnerTotalCommission();
    }

    /**
     * 累计赠送USDT
     *
     * @return
     */
    @Override
    public BigDecimal getAllGivenUSDT() {
        return this.baseMapper.getAllGivenUSDT();
    }

    /**
     * 用户钱包累计赠送券余额
     *
     * @return
     */
    @Override
    public BigDecimal getAllTotalTicket() {
        return this.baseMapper.getAllTotalTicket();
    }

}
