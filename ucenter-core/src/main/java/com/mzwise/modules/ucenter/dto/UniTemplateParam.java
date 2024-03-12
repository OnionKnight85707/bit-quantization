package com.mzwise.modules.ucenter.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mzwise.common.exception.ApiException;
import com.mzwise.constant.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 参数模块
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UniTemplateParam implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "说明")
    @NotNull
    private String name;

    @ApiModelProperty(value = "杠杆倍数，目前支持1 2 5  这 3个值")
    @NotNull
    private Integer leverage;

    @ApiModelProperty(value = "首单类型 1 按照百分比， 2按照具体数值金额")
    @NotNull
    private TemplateFirstTypeEnum firstType;

    @ApiModelProperty(value = "首单值，如果是百分比，比如 3% ，就存 0.03,如果first_type=2则具体数值比如 100")
    @NotNull
    private BigDecimal firstValue;

    @ApiModelProperty(value = "仓位方式(1:显示仓位金额,2:显示钱包金额)")
    @NotNull
    private PositionTypeEnum positionType;

    @ApiModelProperty(value = "最少启动仓位")
    @NotNull
    private BigDecimal position;

    @ApiModelProperty(value = "开平方式： 1按照外部信号， 2按照追踪止盈")
    @NotNull
    private TemplateStopModeEnum openCloseMode;

    @ApiModelProperty(value = "如果开平方式为1，则这个为空，如果为2，则里面有2个值，止盈比例和回撤，中间用英文逗号分开，比如0.03,0.5")
    private String stopParam;

    @ApiModelProperty(value = "补仓次数，如果没有补仓，则为0")
    private Integer coverTimes = 0;

    @ApiModelProperty(value = "补仓参数，仓位百分比和 价格百分比，如果cover_times为3，则都为3个  价格 和 间隔")
    private String coverParam;

    @ApiModelProperty(value = "模板使用说明")
    private String memo;

    @ApiModelProperty(value = "收益结算方式(1:实时结算，2:冻结结算)")
    private SettleTypeEnum settleType;

    @ApiModelProperty("预期收益")
    private BigDecimal expectedRatio;

    @ApiModelProperty("收益平仓(结算收益),后台可以添加多个收益,用逗号分隔")
    private String closeRatio;

    @ApiModelProperty("循环模式:(1:循环一次,2:循环多次)")
    private LoopModeEnum loopMode;

    @ApiModelProperty("冻结平仓收益")
    private BigDecimal frozenRatio;

    @ApiModelProperty("外部信号类型:0只读,1正常")
    private OutSignalTypeEnum outSignalType;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}
