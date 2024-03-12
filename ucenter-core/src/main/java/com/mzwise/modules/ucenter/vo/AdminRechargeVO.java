package com.mzwise.modules.ucenter.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author piao
 * @Date 2021/06/01
 */
@Data
public class AdminRechargeVO {
    @ApiModelProperty("充值金额")
    private BigDecimal amountOfRecharge;

    @ApiModelProperty("充值笔数")
    private Integer numberOfRecharge;
}
