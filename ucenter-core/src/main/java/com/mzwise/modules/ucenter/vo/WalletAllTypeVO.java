package com.mzwise.modules.ucenter.vo;

import com.mzwise.modules.ucenter.entity.UcWallet;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WalletAllTypeVO {

    @ApiModelProperty(value = "USD")
    private UcWallet QUANT;

    @ApiModelProperty(value = "FIL")
    private UcWallet MINING;

    @ApiModelProperty(value = "BTE")
    private UcWallet PLATFORM;

    @ApiModelProperty(value = "量化社区余额账户")
    private UcWallet QUANT_COMMUNITY;


    @ApiModelProperty(value = "服务费")
    private UcWallet QUANT_SERVICE;

    @ApiModelProperty(value = "矿机市场奖励余额账户")
    private UcWallet MINING_COMMUNITY;

    @ApiModelProperty(value = "挖矿可提现余额账户")
    private UcWallet MINING_PROFIT;

    @ApiModelProperty(value = "平台币分红账户")
    private UcWallet PLATFORM_SHARE;
}
