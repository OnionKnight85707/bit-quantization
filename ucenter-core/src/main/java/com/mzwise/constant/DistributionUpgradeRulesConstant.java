package com.mzwise.constant;

import io.swagger.annotations.ApiModel;

/**
 * @Author piao
 * @Date 2021/05/21
 */
@ApiModel(value = "拉人升级规则")
public class DistributionUpgradeRulesConstant {

    /**
     * 0级升到1级 需要有7个直属0级
     */
    public static final Integer ZERO2ONE = 3;

    /**
     * 1级升到2级 需要有3个不同的1级团队
     */
    public static final Integer ONE2TWO = 2;

    /**
     * 2级升到3级 需要有3个不同的2级团队
     */
    public static final Integer TWO2THREE = 2;

    /**
     * 3级升到4级 需要有3个不同的3级团队
     */
    public static final Integer THREE2FOUR = 2;

    /**
     * 4级升到5级 需要有3个不同的4级团队
     */
    public static final Integer FOUR2FIVE = 2;

    /**
     * 5级升到6级 需要有3个不同的5级团队
     */
    public static final Integer FIVE2SIX = 2;

    /**
     * 6级升到7级 需要有3个不同的6级团队
     */
    public static final Integer SIX2SEVEN = 2;

}
