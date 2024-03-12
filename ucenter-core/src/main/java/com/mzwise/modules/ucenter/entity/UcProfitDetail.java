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
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用户收益详情
 * @Author LiangZaiChao
 * @Date 2022/7/5 11:35
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("uc_profit_detail")
@ApiModel(value = "UcProfitDetail对象", description = "")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UcProfitDetail implements Serializable {

    private static final long serialVersionUID = 250237366605355787L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long memberId;

    @ApiModelProperty(value = "策略id")
    private Long quantId;

    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "收益")
    private BigDecimal profit;

    @ApiModelProperty(value = "交易额 USDT")
    private BigDecimal amount;

    @ApiModelProperty(value = "是否扣费：0没扣费，1已扣费")
    private Boolean isDeduct;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}
