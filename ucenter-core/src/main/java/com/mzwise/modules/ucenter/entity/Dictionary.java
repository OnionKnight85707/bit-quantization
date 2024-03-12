package com.mzwise.modules.ucenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 *  字典表
 * </p>
 *
 * @author 666
 * @since 2022-08-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("dictionary")
@ApiModel(value="Dictionary对象", description="")
public class Dictionary implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "键")
    private String name;

    @ApiModelProperty(value = "值")
    private String val;

    @ApiModelProperty(value = "说明")
    private String explanation;

}
