package com.mzwise.modules.ucenter.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author piao
 * @Date 2021/05/26
 */
@Data
@ApiModel(value = "量化佣金")
public class QuantifiedCommissionVO {
    @ApiModelProperty("今日分享奖")
    private BigDecimal todaySharingAward;
    @ApiModelProperty("今日社区奖")
    private BigDecimal todayCommunityAward;
    @ApiModelProperty("今日分红奖")
    private BigDecimal todayDividendAward;
    @ApiModelProperty("量化社区余额")
    private BigDecimal quantifyCommunityBalance;
    @ApiModelProperty("量化社区历史总收益")
    private BigDecimal historyQuantifyCommunityProfit;


}
