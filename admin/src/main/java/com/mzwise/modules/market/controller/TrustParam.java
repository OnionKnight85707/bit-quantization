package com.mzwise.modules.market.controller;

import com.mzwise.constant.TradeTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author wmf
 * @Date 2021/7/14 11:42
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrustParam {
    /**
     * 类型
     */
    @ApiModelProperty(value = "交易方式EXCHANGE 现货 SWAP 合约")
    private TradeTypeEnum tradeType ;
    /**
     * 币种
     */
    @ApiModelProperty(value = "币种 如：BTC-USDT")
    private String symbol;
    /**
     * 买入策略
     */
    @ApiModelProperty(value = "策略（1,2,3,4）")
    private List<Integer> sids;
}