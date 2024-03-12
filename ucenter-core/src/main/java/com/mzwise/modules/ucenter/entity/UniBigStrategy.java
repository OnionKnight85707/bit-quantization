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
 * 大类策略表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("uni_big_strategy")
@ApiModel(value="UniBigStrategy对象", description="")
public class UniBigStrategy implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "大类策略描述")
    private String name;

    @ApiModelProperty(value = "排序，值越小排在最前面，默认1")
    private Integer sort;

}
