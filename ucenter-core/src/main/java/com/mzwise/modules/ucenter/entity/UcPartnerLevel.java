package com.mzwise.modules.ucenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mzwise.constant.PartnerLevelEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 合伙人等级实体
 * @author 666
 * @since 2022-08-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("uc_partner_level")
@ApiModel(value="UcPartnerLevel对象", description="")
public class UcPartnerLevel implements Serializable {

    private static final long serialVersionUID = -4559875682448989514L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "等级")
    private PartnerLevelEnum level;

    @ApiModelProperty(value = "适用于一层")
    private Boolean applyLayer;

    @ApiModelProperty(value = "适用于多层")
    private Boolean applyMultilayer;

    @ApiModelProperty(value = "佣金比例")
    private BigDecimal commissionRate;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

}
