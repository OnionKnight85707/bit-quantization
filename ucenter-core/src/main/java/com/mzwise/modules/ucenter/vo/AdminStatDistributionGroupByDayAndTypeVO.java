package com.mzwise.modules.ucenter.vo;

import com.mzwise.constant.DistributionProfitTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * @Author piao
 * @Date 2021/06/21
 */
@Data
@Deprecated
public class AdminStatDistributionGroupByDayAndTypeVO {
    @ApiModelProperty("日期")
    private Date dayDate;

    @ApiModelProperty("金额")
    private BigDecimal amount;
}
