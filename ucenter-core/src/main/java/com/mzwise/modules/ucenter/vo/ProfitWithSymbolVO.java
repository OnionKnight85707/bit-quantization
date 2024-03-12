package com.mzwise.modules.ucenter.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "收益及单位")
public class ProfitWithSymbolVO {

    @ApiModelProperty(value = "总收益")
    private BigDecimal total;

    @ApiModelProperty(value = "今日收益")
    private BigDecimal today;

    @ApiModelProperty(value = "单位")
    private String symbol;

    public ProfitWithSymbolVO() {
    }

    public ProfitWithSymbolVO(BigDecimal total, BigDecimal today, String symbol) {
        this.total = total;
        this.today = today;
        this.symbol = symbol;
    }
}
