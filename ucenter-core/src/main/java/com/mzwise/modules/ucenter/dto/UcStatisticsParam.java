package com.mzwise.modules.ucenter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author 666
 * @since 2022-08-01
 */
@Data
@ApiModel("后台展示量化订单列表")
public class UcStatisticsParam {

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    @ApiModelProperty(value = "开始时间")
    private String beginTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

}
