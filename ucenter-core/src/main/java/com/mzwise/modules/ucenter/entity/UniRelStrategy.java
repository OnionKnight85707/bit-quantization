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
 * 用户类别关联小类策略表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("uni_rel_strategy")
@ApiModel(value="UniRelStrategy对象", description="")
public class UniRelStrategy implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户类别id，uni_user_type的主键")
    private Integer userTypeId;

    @ApiModelProperty(value = "小类策略id，uni_small_strategy表的ID")
    private Integer strategyTypeId;

}
