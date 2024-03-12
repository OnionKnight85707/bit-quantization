package com.mzwise.constant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * @Author piao
 * @Date 2021/05/21
 */
@ApiModel(value = "股东/董事分红奖规则")
public class DistributionDividendsRulesConstant {
    @ApiModelProperty("股东分红 比率")
    public static final BigDecimal RATE_OF_STOCKHOLDER = new BigDecimal("0.04");
    /**
     * 董事
     */
    @ApiModelProperty("董事分红 比率")
    public static final BigDecimal RATE_OF_DIRECTOR = new BigDecimal("0.06");


}
