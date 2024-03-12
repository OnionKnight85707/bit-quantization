package com.mzwise.modules.ucenter.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author piao
 * @Date 2021/06/01
 */
@Data
@ApiModel("会员详情——充提币")
public class AdminMemberDepositAndWithdrawalVO {
    @ApiModelProperty("充值金额")
    private BigDecimal amountOfRecharge;

    @ApiModelProperty("充值笔数")
    private Integer numberOfRecharge;

    @ApiModelProperty("提币金额")
    private BigDecimal amountOfWithdrawal;

    @ApiModelProperty("提币笔数")
    private Integer numberOfWithdrawal;
}
