package com.mzwise.modules.ucenter.dto;

import com.mzwise.constant.WalletTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExchangeParam {

    @ApiModelProperty("兑换从..，如：QUANT")
    private WalletTypeEnum from;

    @ApiModelProperty("兑换至..，如：FIL")
    private WalletTypeEnum to;

    @ApiModelProperty("兑换金额")
    private BigDecimal amount;
}
