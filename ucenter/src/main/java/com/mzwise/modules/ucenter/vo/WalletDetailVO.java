package com.mzwise.modules.ucenter.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WalletDetailVO {

    @ApiModelProperty(value = "总资产")
    private BalanceVO total;

    @ApiModelProperty(value = "各账户资产")
    private BalanceAllTypeVO items;

    public WalletDetailVO(BalanceVO total, BalanceAllTypeVO items) {
        this.total = total;
        this.items = items;
    }
}
