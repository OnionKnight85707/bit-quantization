package com.mzwise.modules.quant.dto;

import com.mzwise.modules.quant.entity.QuantStrategyFUTURE;
import com.mzwise.modules.quant.entity.QuantStrategyTrend;
import com.mzwise.modules.quant.entity.QuantStrategyUnified;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 修改自设指标参数
 * @Author LiangZaiChao
 * @Date 2022/6/23 10:38
 */
@Data
public class UpdateQuantStrategyParam {

    @NotNull
    private Long id;

    @ApiModelProperty(value = "杠杆倍数")
    private Integer leverage;

    @ApiModelProperty(value = "仓位金额")
    private BigDecimal position;

    @ApiModelProperty(value = "合约对冲参数，选合约对冲提交")
    private QuantStrategyFUTURE future;

    @ApiModelProperty(value = "趋势策略参数")
    private QuantStrategyTrend trend;

    @ApiModelProperty(value = "统一策略参数")
    private QuantStrategyUnified unified;

}
