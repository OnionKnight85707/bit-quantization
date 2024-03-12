package com.mzwise.modules.home.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author piao
 * @Date 2021/03/10
 */
@Data
@ApiModel(description = "填写风险测评参数")
public class AdminQuestionnaireResultParam {

    @ApiModelProperty(value = "风险测评id", required = true)
    private Long questionnaireId;

    @ApiModelProperty(value = "选项数组下标", required = true)
    private Integer answerIndex;

}
