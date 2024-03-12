package com.mzwise.modules.home.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author piao
 * @Date 2021/03/10
 */
@Data
public class HomeQuestionnaireParam {
    @ApiModelProperty(value = "题干", required = true)
    private String subject;

    @ApiModelProperty(value = "选项，json数组", required = true)
    private String options;

    @ApiModelProperty(value = "语言", required = true)
    private String language;

    @ApiModelProperty(value = "排序", required = false)
    private Integer sort;

    @ApiModelProperty(value = "选项对应的分数")
    private String scores;

    @ApiModelProperty(value = "跳转，跳转, 如 {0: 6} 代表选A(第0个选项)跳转至第6题")
    private String jumpOption;
}
