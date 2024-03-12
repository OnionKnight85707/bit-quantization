package com.mzwise.modules.ucenter.dto;

import com.mzwise.constant.WalletTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferParam {

    @ApiModelProperty("划转从..，如：QUANT")
    private WalletTypeEnum from;

    @ApiModelProperty("划转至..，如：QUANT_SERVICE")
    private WalletTypeEnum to;

    @ApiModelProperty("划转币种(可不传)")
    private String symbol;

    @ApiModelProperty("划转金额")
    private BigDecimal amount;
}
