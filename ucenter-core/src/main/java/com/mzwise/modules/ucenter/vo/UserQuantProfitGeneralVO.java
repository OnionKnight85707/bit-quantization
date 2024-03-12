package com.mzwise.modules.ucenter.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "量化收益数据")
public class UserQuantProfitGeneralVO {
    /**
     * 需要立即计算
     */
    @ApiModelProperty(value = "现持仓总收益")
    private BigDecimal current = BigDecimal.ZERO;

    @ApiModelProperty(value = "现货持仓金额")
    private BigDecimal exchangePosition = BigDecimal.ZERO;

    @ApiModelProperty(value = "现货总收益")
    private BigDecimal exchangeTotalProfit = BigDecimal.ZERO;

    @ApiModelProperty(value = "现货今日收益")
    private BigDecimal exchangeTodayProfit = BigDecimal.ZERO;

    @ApiModelProperty(value = "合约持仓金额")
    private BigDecimal swapPosition = BigDecimal.ZERO;

    @ApiModelProperty(value = "合约总收益")
    private BigDecimal swapTotalProfit = BigDecimal.ZERO;

    @ApiModelProperty(value = "合约今日收益")
    private BigDecimal swapTodayProfit = BigDecimal.ZERO;

    @ApiModelProperty(value = "量化天数")
    private Integer quantDay = 0;

    @ApiModelProperty(value = "收益单位")
    private String symbol = "USDT";

}
