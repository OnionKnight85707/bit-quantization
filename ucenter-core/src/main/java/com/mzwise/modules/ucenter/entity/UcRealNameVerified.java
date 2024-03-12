package com.mzwise.modules.ucenter.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mzwise.constant.RealNameStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("uc_real_name_verified")
@ApiModel(value="UcRealNameVerified对象", description="")
public class UcRealNameVerified implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "会员id")
    private Long memberId;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "证件类型")
    private Integer identityCardType;

    @ApiModelProperty(value = "身份证号")
    private String identityCardNumber;

    @ApiModelProperty(value = "身份证正面照片url")
    private String identityCardFront;

    @ApiModelProperty(value = "身份证背面照片url")
    private String identityCardBack;

    @ApiModelProperty(value = "实名认证状态")
    private RealNameStatusEnum status;

    @ApiModelProperty(value = "实名认证失败理由")
    private String failedReason;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
