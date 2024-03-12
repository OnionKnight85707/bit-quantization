package com.mzwise.modules.ucenter.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CheckServiceChargeVO {

    @ApiModelProperty(value = "用户id")
    private Long id;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "欠费金额")
    private BigDecimal unPay;

    @ApiModelProperty(value = "欠费提醒次数")
    private Integer reminderTimes;

}
