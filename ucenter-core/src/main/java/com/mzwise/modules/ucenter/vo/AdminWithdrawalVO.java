package com.mzwise.modules.ucenter.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author piao
 * @Date 2021/06/01
 */
@Data
public class AdminWithdrawalVO {
    @ApiModelProperty("提币金额")
    private BigDecimal amountOfWithdrawal;

    @ApiModelProperty("提币笔数")
    private Integer numberOfWithdrawal;
}
