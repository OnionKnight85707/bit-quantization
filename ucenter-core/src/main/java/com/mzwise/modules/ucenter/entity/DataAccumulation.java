package com.mzwise.modules.ucenter.entity;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class DataAccumulation implements Serializable {

    @ApiModelProperty(value = "累计注册用户")
    private Long registeredTotal;

    @ApiModelProperty(value = "累计赠送USDT")
    private BigDecimal freeUSDTTotal;

    @ApiModelProperty(value = "累计开启策略用户")
    private Long openStrategyUsersTotal;

    @ApiModelProperty("累计做单收益")
    private BigDecimal orderProfitTotal;

    @ApiModelProperty("累计网络充值数量")
    private BigDecimal networkRechargeTotal;

    @ApiModelProperty("累计后台充值数量")
    private BigDecimal managementRechargeTotal;

    @ApiModelProperty("累计提币数量")
    private BigDecimal WithdrawTotal;

    @ApiModelProperty("累计合伙人")
    private BigDecimal partnerCommissionTotal;

    @ApiModelProperty("用户钱包累计余额")
    private BigDecimal userWalletBalanceTotal;

    @ApiModelProperty("用户钱包累计赠送券余额")
    private BigDecimal userWalletTicketTotal;

    @ApiModelProperty("公司收益")
    private BigDecimal companyEarningsTotal;


}
