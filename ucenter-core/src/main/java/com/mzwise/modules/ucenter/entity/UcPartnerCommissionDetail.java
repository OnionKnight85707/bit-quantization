package com.mzwise.modules.ucenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 合伙人佣金明细表
 * @Author: LiangZaiChao
 * @Date: 2022/6/10 14:37
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("uc_partner_commission_detail")
@ApiModel(value = "合伙人佣金明细", description = "")
public class UcPartnerCommissionDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long memberId;

    @ApiModelProperty(value = "订单id")
    private Long quantOrderId;

    @ApiModelProperty(value = "佣金")
    private BigDecimal commission;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

}
