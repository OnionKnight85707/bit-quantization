package com.mzwise.modules.ucenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户收益
 * @Author LiangZaiChao
 * @Date 2022/7/5 11:35
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("uc_profit")
@ApiModel(value = "UcProfit对象", description = "")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UcProfit implements Serializable {

    private static final long serialVersionUID = -4146578361086619533L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long memberId;

    @ApiModelProperty(value = "当天的收益统计汇总")
    private BigDecimal profit;

    @ApiModelProperty(value = "当天的成交额汇总")
    private BigDecimal amount;

    @ApiModelProperty(value = "统计哪天的,一般今天1点统计昨天的，填写昨天的日期")
    private LocalDate profitDay;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}
