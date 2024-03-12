package com.mzwise.modules.ucenter.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Value;

@Data
public class WalletGeneralVO {
    /**总资产**/
    @ApiModelProperty(value = "总资产")
    private BalanceVO total;

    /**服务费**/
    @ApiModelProperty(value = "服务费")
    private BalanceVO service;

    /**奖励金额**/
    @ApiModelProperty(value = "奖励金额")
    private BalanceVO award;

    public WalletGeneralVO(BalanceVO total, BalanceVO service,BalanceVO award) {
        this.total = total;
        this.service = service;
        this.award=award;
    }
}
