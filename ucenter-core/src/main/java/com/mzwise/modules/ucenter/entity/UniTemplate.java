package com.mzwise.modules.ucenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mzwise.common.exception.ApiException;
import com.mzwise.constant.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 参数模块
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("uni_template")
@ApiModel(value="UniTemplate对象", description="")
public class UniTemplate implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "说明")
    private String name;

    @ApiModelProperty(value = "杠杆倍数，目前支持1 2 5  这 3个值")
    private Integer leverage;

    @ApiModelProperty(value = "首单类型 1 按照百分比， 2按照具体数值金额")
    private TemplateFirstTypeEnum firstType;

    @ApiModelProperty(value = "首单值，如果是百分比，比如 3% ，就存 0.03,如果first_type=2则具体数值比如 100")
    private BigDecimal firstValue;

    @ApiModelProperty(value = "仓位方式(1:显示仓位金额,2:显示钱包金额)")
    private PositionTypeEnum positionType;

    @ApiModelProperty(value = "最少启动仓位")
    private BigDecimal position;

    @ApiModelProperty(value = "开平方式： 1按照外部信号， 2按照追踪止盈")
    private TemplateStopModeEnum openCloseMode;

    @ApiModelProperty(value = "如果开平方式为1，则这个为空，如果为2，则里面有2个值，止盈比例和回撤，中间用英文逗号分开，比如0.03,0.5")
    private String stopParam;

    @ApiModelProperty(value = "补仓次数，如果没有补仓，则为0")
    private Integer coverTimes;

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

    @ApiModelProperty("外部信号类型:0内部信号,1正常信号,2只读信号")
    private OutSignalTypeEnum outSignalType;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 校验首单方式
     * @param firstType
     * @param firstValue
     */
    public static void checkFirstType(TemplateFirstTypeEnum firstType, BigDecimal firstValue) {
        if (TemplateFirstTypeEnum.BY_PERCENT == firstType) {
            if (firstValue.compareTo(BigDecimal.ZERO) == -1 || firstValue.compareTo(new BigDecimal("1")) == 1) {
                throw new ApiException("首单值 百分比参数有误");
            }
        }
    }

    /**
     * 校验止盈止损
     * @param stopModeEnum 止盈止损方式
     * @param stopParam 止盈止损参数
     */
    public static void checkStopMode(TemplateStopModeEnum stopModeEnum, String stopParam) {
        if (TemplateStopModeEnum.TRAILING_TAKE_PROFIT == stopModeEnum) {
            String[] split = stopParam.split(",");
            if (split.length != 2) {
                throw new ApiException("stopParam(止盈止损)参数有误");
            }
        }
    }

    /**
     * 校验补仓
     * @param coverTimes 补仓次数
     * @param coverParam 补仓参数
     */
    public static void checkCover(int coverTimes, String coverParam) {
        if (coverTimes != 0) {
            String[] split = coverParam.split(",");
            if (split.length != 2) {
                throw new ApiException("coverParam(补仓参数)价格或间隔缺失");
            }
            if (split[0].split("_").length != coverTimes) {
                throw new ApiException("coverParam(补仓参数)价格长度有误");
            }
            if (split[1].split("_").length != coverTimes) {
                throw new ApiException("coverParam(补仓参数)间隔长度有误");
            }
        }
    }

    public static void checkInputNumber(Integer leverage,BigDecimal position){
        if (leverage<0 || leverage==0){
            throw new ApiException("杠杆倍数必须大于0");
        }
        if (position.compareTo(BigDecimal.ZERO)<1){
            throw new ApiException("仓位不能为负数");
        }
    }

    public static void  checkFrozenNumber(BigDecimal expectedRatio,String closeRatio,BigDecimal frozenRatio){
        if(expectedRatio.compareTo(BigDecimal.ZERO)<1){
            throw new ApiException("预期收益不能为负数");
        }

        String substring = closeRatio.substring(0, closeRatio.length());
        String[] split = substring.split(",");
        for (String temp:split){
            if(temp.equals("0")){
                throw  new ApiException("结算收益不能为0");
            }
        }
        if (frozenRatio.compareTo(BigDecimal.ZERO)<1){
            throw new ApiException("冻结收益不能为负数");
        }
    }

}
