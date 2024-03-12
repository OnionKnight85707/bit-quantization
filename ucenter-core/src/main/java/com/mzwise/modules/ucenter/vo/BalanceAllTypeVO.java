package com.mzwise.modules.ucenter.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BalanceAllTypeVO {

    @ApiModelProperty(value = "USD")
    private AmountWithSymbolVO QUANT;

    @ApiModelProperty(value = "FIL")
    private AmountWithSymbolVO MINING;

    @ApiModelProperty(value = "平台币")
    private AmountWithSymbolVO PLATFORM;

    @ApiModelProperty(value = "量化社区余额账户")
    private AmountWithSymbolVO QUANT_COMMUNITY;


    @ApiModelProperty(value = "服务费")
    private AmountWithSymbolVO QUANT_SERVICE;

    @ApiModelProperty(value = "矿机市场奖励余额账户")
    private AmountWithSymbolVO MINING_COMMUNITY;

    @ApiModelProperty(value = "挖矿可提现余额账户")
    private AmountWithSymbolVO MINING_PROFIT;

    @ApiModelProperty(value = "平台币分红账户")
    private AmountWithSymbolVO PLATFORM_SHARE;
}
