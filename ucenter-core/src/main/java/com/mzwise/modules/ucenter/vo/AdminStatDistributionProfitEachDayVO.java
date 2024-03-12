package com.mzwise.modules.ucenter.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * @Author piao
 * @Date 2021/06/21
 */
@Data
@ApiModel("统计每日量化分销受益")
public class AdminStatDistributionProfitEachDayVO {
    @ApiModelProperty("日期")
    private Date dayDate;

    @ApiModelProperty("分享奖")
    private BigDecimal amountOfShare;

    @ApiModelProperty("社区奖")
    private BigDecimal amountOfCommunity;

    @ApiModelProperty("分红奖")
    private BigDecimal amountOfDividend;

    @ApiModelProperty("总分享奖励")
    private BigDecimal amountOfTotal;
}
