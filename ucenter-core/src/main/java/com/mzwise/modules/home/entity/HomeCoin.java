package com.mzwise.modules.home.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author admin
 * @since 2021-03-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("home_coin")
@ApiModel(value="HomeCoin对象", description="")
public class HomeCoin implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId
    @ApiModelProperty(value = "币种")
    private String symbol;

    private String name;

    @ApiModelProperty(value = "精度")
    private String scale;

    @ApiModelProperty(value = "是否可以提币")
    private Boolean canWithdraw;

    @ApiModelProperty(value = "提币精度")
    private Integer withdrawScale;

    @ApiModelProperty(value = "提币手续费")
    private BigDecimal withdrawFee;

    @ApiModelProperty(value = "是否可以充值")
    private Boolean canRecharge;

    @ApiModelProperty(value = "是否是平台币")
    private Boolean isPlatformCoin;

    @ApiModelProperty(value = "兑换手续费")
    private BigDecimal exchangeFee;

    @ApiModelProperty(value = "优盾编码")
    @JsonIgnore
    private String udunCode;

    @ApiModelProperty(value = "优盾子编码")
    @JsonIgnore
    private String udunSubCode;

    @ApiModelProperty(value = "最小提币数量")
    private BigDecimal minWithdrawAmount;

    @ApiModelProperty(value = "最小提币手续费")
    private BigDecimal minWithdrawFee;

}
