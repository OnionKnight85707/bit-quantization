package com.mzwise.modules.ucenter.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mzwise.constant.TransactionTypeEnum;
import com.mzwise.constant.WalletTypeEnum;
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
 * @since 2021-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("uc_partner_stat")
@ApiModel(value="uc_partner_stat", description="")
public class UcPartnerStat implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "会员id")
    private Long memberId;


    @ApiModelProperty(value = "交易类型")
    private Integer type;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;


    @ApiModelProperty(value = "备注")
    private String statDay;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


}
