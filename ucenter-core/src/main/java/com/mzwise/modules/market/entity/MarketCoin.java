package com.mzwise.modules.market.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.mzwise.constant.PlatformEnum;
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
 * @since 2021-04-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("market_coin")
@ApiModel(value="MarketCoin对象", description="")
@Deprecated
public class MarketCoin implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "币种")
    private String symbolPair;

    @ApiModelProperty(value = "平台")
    private PlatformEnum platform;

    @ApiModelProperty(value = "币种精度")
    private Integer coinScale;

    @ApiModelProperty(value = "基币精度")
    private Integer baseCoinScale;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
