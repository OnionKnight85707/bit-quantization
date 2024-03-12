package com.mzwise.modules.ucenter.entity;

import com.baomidou.mybatisplus.annotation.*;
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
 * @since 2021-05-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("uc_invite_record")
@ApiModel(value = "UcInviteRecord对象", description = "")
public class UcInviteRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "下级会员id")
    private Long subMemberId;

    @ApiModelProperty(value = "下级会员昵称")
    private String subMemberNickname;

    @ApiModelProperty(value = "上级会员id")
    private Long supMemberId;

    @ApiModelProperty(value = "上级会员昵称")
    private String supMemberNickname;

    @ApiModelProperty(value = "是否为有效用户")
    private Boolean isEffective;

    @ApiModelProperty(value = "生效时间")
    private Date effectiveTime;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
