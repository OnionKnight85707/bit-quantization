package com.mzwise.modules.ucenter.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 每日收益汇总响应
 * @Author LiangZaiChao
 * @Date 2022/7/5 15:28
 */
@Data
public class DailyEarningsStatisticsVo {

    @ApiModelProperty(value = "用户id")
    private Long memberId;

    @ApiModelProperty(value = "收益汇总")
    private BigDecimal profitSum;

    @ApiModelProperty(value = "交易额汇总")
    private BigDecimal amountSum;

}
