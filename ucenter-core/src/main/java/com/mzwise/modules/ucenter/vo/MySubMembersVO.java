package com.mzwise.modules.ucenter.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 分页查看下级
 */
@Data
public class MySubMembersVO implements Serializable {

    @ApiModelProperty(value = "下级会员id")
    private Long id;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "下级会员昵称")
    private String nickName;

    @ApiModelProperty(value = "是否开通合伙人",required = true)
    private Boolean isPartner;

    @ApiModelProperty(value = "合伙人佣金比例",required = true)
    private BigDecimal partnerCommissionRate;

}
