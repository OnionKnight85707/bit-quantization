package com.mzwise.modules.ucenter.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * @Author piao
 * @Date 2021/06/10
 */
@Data
@ApiModel("统计每日出入金情况")
public class AdminStatRechargeAndWithdrawalEachDayVO {
    @ApiModelProperty("日期")
    private Date dayDate;

    @ApiModelProperty("入金金额")
    private BigDecimal amountOfRecharge;

    @ApiModelProperty("出金金额")
    private BigDecimal amountOfWithdrawal;
}
