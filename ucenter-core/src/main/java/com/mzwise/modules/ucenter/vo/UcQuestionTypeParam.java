package com.mzwise.modules.ucenter.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UcQuestionTypeParam {


    private Integer id;

    @ApiModelProperty(value = "语言")
    private String language;


    @ApiModelProperty(value = "对应的语言分类名称")
    private String name;

    @ApiModelProperty(value = "排序")
    private Integer sort;
}
