package com.mzwise.modules.ucenter.vo;

import com.mzwise.constant.WalletTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 兑换通道
 * @author wmf
 */
@Data
public class ExchangeTunnelVO {

    @ApiModelProperty("账户id")
    private Long id;

    @ApiModelProperty("账户类型")
    private WalletTypeEnum type;

    @ApiModelProperty("币种")
    private String symbol;

    @ApiModelProperty("账户现有可用余额")
    private BigDecimal balance;

    @ApiModelProperty("兑换汇率")
    private BigDecimal rate;

    @ApiModelProperty("兑换手续费率")
    private BigDecimal fee;
}
