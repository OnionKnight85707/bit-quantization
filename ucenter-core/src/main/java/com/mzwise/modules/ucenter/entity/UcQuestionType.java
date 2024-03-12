package com.mzwise.modules.ucenter.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.mzwise.constant.CommonQuestionTypeEnum;
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
@TableName("uc_question_type")
@ApiModel(value="UcQuestionType对象", description="")
public class UcQuestionType implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "语言")
    private String language;


    @ApiModelProperty(value = "对应的语言分类名称")
    private String name;

    @ApiModelProperty(value = "排序")
    private Integer sort;


}
