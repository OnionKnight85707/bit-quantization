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

/**
 * 用户类别表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("uni_user_type")
@ApiModel(value="UniUserType对象", description="")
public class UniUserType implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "名称说明")
    private String name;

    @ApiModelProperty(value = "会员上限")
    private BigDecimal upperLimit;

    @ApiModelProperty(value = "会员下限")
    private BigDecimal lowerLimit;

    @ApiModelProperty(value = "说明")
    private String memo;

}
