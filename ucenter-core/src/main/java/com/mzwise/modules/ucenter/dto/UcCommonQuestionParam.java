package com.mzwise.modules.ucenter.dto;

import com.mzwise.constant.CommonQuestionTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Administrator
 * @Date 2021/01/14
 */
@Data
public class UcCommonQuestionParam {
    @ApiModelProperty(value = "语言",required = true)
    private String language;

    @ApiModelProperty(value = "是否置顶",required = true)
    private Boolean isTop;

    @ApiModelProperty(value = "是否显示",required = true)
    private Boolean isShow;

    @ApiModelProperty(value = "常见问题类型")
    private Integer type;

    @ApiModelProperty(value = "常见问题标题")
    private String title;

    @ApiModelProperty(value = "常见问题内容")
    private String content;

}
