package com.mzwise.modules.ucenter.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "收益概览")
public class UserProfitGeneralVO {

    @ApiModelProperty(value = "各项总收益")
    private ProfitWithSymbolVO total;

    @ApiModelProperty(value = "量化历史收益")
    private ProfitWithSymbolVO quant = new ProfitWithSymbolVO(BigDecimal.ZERO, BigDecimal.ZERO, "USDT");

    @ApiModelProperty(value = "社区量化历史收益")
    private ProfitWithSymbolVO quantCommunity;

    @ApiModelProperty(value = "个人挖矿历史收益")
    private ProfitWithSymbolVO miningProfit;

    @ApiModelProperty(value = "社区挖矿历史收益")
    private ProfitWithSymbolVO miningCommunity;

    @ApiModelProperty(value = "平台分红收益")
    private ProfitWithSymbolVO platformShare;

}
