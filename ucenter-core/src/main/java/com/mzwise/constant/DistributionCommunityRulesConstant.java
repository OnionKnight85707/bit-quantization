package com.mzwise.constant;

import io.swagger.annotations.ApiModel;

import java.math.BigDecimal;

/**
 * @Author piao
 * @Date 2021/05/21
 */
@ApiModel(value = "社区奖 比率规则")
public class DistributionCommunityRulesConstant {
    /**
     * 0级 社区奖
     */
    public static final BigDecimal COMMUNITY_RATE_OF_LEVEL0 = BigDecimal.ZERO;
    /**
     * 1级 社区奖
     */
    public static final BigDecimal COMMUNITY_RATE_OF_LEVEL1 = new BigDecimal("0.10");
    /**
     * 2级 社区奖
     */
    public static final BigDecimal COMMUNITY_RATE_OF_LEVEL2 = new BigDecimal("0.15");
    /**
     * 3级 社区奖
     */
    public static final BigDecimal COMMUNITY_RATE_OF_LEVEL3 = new BigDecimal("0.20");
    /**
     * 4级 社区奖
     */
    public static final BigDecimal COMMUNITY_RATE_OF_LEVEL4 = new BigDecimal("0.25");
    /**
     * 5级 社区奖
     */
    public static final BigDecimal COMMUNITY_RATE_OF_LEVEL5 = new BigDecimal("0.30");
    /**
     * 6级 社区奖
     */
    public static final BigDecimal COMMUNITY_RATE_OF_LEVEL6 = new BigDecimal("0.30");
    /**
     * 7级 社区奖
     */
    public static final BigDecimal COMMUNITY_RATE_OF_LEVEL7 = new BigDecimal("0.30");
}
