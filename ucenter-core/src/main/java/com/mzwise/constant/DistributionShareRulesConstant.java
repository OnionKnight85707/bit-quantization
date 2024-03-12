package com.mzwise.constant;

import io.swagger.annotations.ApiModel;

import java.math.BigDecimal;

/**
 * @Author piao
 * @Date 2021/05/21
 */
@ApiModel(value = "分享奖规则")
public class DistributionShareRulesConstant {
    public static final Integer DIRECT_PUSH_NUMBER_FOR_FIRST = 1;
    public static final Integer DIRECT_PUSH_NUMBER_FOR_SECOND = 2;

    /**
     * 拿第一层比例
     */
    public static final BigDecimal SHARE_RATE_OF_LEVEL1 = new BigDecimal("0.1");
    /**
     * 拿第二层比例
     */
    public static final BigDecimal SHARE_RATE_OF_LEVEL2 = new BigDecimal("0.1");
}
