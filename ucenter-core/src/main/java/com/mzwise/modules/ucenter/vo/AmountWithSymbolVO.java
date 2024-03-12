package com.mzwise.modules.ucenter.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "金额及单位")
public class AmountWithSymbolVO {

    @ApiModelProperty(value = "余额")
    private BigDecimal amount;

    @ApiModelProperty(value = "单位")
    private String symbol;

    public AmountWithSymbolVO() {
    }

    public AmountWithSymbolVO(BigDecimal amount, String symbol) {
        this.amount = amount;
        this.symbol = symbol;
    }
}
