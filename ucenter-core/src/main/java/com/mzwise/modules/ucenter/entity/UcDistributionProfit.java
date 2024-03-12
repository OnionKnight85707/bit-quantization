package com.mzwise.modules.ucenter.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mzwise.constant.DistributionProfitTypeEnum;
import com.mzwise.constant.UnitEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author admin
 * @since 2021-05-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("uc_distribution_profit")
@ApiModel(value = "UcDistributionProfit对象", description = "")
public class UcDistributionProfit implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "分销收益类型")
    private DistributionProfitTypeEnum type;

    @ApiModelProperty(value = "会员id")
    private Long memberId;

    @ApiModelProperty(value = "收益人id")
    private Long benefitMemberId;

    @ApiModelProperty(value = "会员昵称")
    private String memberNickname;

    @ApiModelProperty(value = "受益人昵称")
    private String benefitMemberNickname;

    @ApiModelProperty(value = "收益金额")
    private BigDecimal benefitAmount;

    @ApiModelProperty(value = "单位,0:USDT,1:FIL,2:BTE(平台币)")
    private UnitEnum unit;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
