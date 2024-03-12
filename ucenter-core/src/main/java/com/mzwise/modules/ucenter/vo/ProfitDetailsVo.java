package com.mzwise.modules.ucenter.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 盈利详情响应
 * @author: David Liang
 * @create: 2022-07-23 14:24
 */
@Data
public class ProfitDetailsVo implements Serializable {

    private static final long serialVersionUID = -3431954893723616652L;

    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "收益")
    private BigDecimal profit;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

}
