package com.mzwise.modules.home.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
 * @since 2021-07-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("home_coin_rate")
@ApiModel(value="HomeCoinRate对象", description="")
public class HomeCoinRate implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId
    @ApiModelProperty(value = "币种")
    private String coin;

    @ApiModelProperty(value = "汇率")
    private BigDecimal rate;


}
