package com.mzwise.modules.ucenter.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author piao
 * @Date 2021/03/10
 */
@Data
@ApiModel(description = "展示用户填写过的风险测评")
public class AdminQuestionnaireResultsVO {
    @ApiModelProperty(value = "id")
    private Long questionnaireId;

    @ApiModelProperty("会员id")
    private Long memberId;

    @ApiModelProperty(value = "题干")
    private String subject;

    @ApiModelProperty(value = "选项，json数组")
    private String options;

    @ApiModelProperty(value = "回答")
    private Integer answerIndex;

    @ApiModelProperty(value = "语言")
    private String language;

    @ApiModelProperty("答题时间")
    private Date createTime;
}
