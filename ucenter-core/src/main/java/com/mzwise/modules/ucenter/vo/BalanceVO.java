package com.mzwise.modules.ucenter.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BalanceVO {

    @ApiModelProperty(value = "usdt")
    private BigDecimal usdt = BigDecimal.ZERO;

    @ApiModelProperty(value = "cny")
    private BigDecimal cny = BigDecimal.ZERO;

    public BalanceVO() {
    }

    public BalanceVO(BigDecimal usdt, BigDecimal cny) {
        this.usdt = usdt;
        this.cny = cny;
    }
}
