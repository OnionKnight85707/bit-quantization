package com.mzwise.modules.home.dto;

import com.mzwise.constant.IntroductionTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author piao
 * @Date 2021/03/10
 */
@Data
public class HomeIntroductionParam {
    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "语言")
    private String language;

    @ApiModelProperty(value = "分类")
    private IntroductionTypeEnum type;
}
