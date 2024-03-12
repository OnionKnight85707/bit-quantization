package com.mzwise.modules.home.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Administrator
 * @Date 2021/02/01
 */
@Data
public class HomeNewsParam {
    @ApiModelProperty(value = "语言",required = true)
    private String language;

    @ApiModelProperty(value = "是否显示")
    private Boolean isShow;

    @ApiModelProperty(value = "是否置顶")
    private Boolean isTop;

    @ApiModelProperty(value = "新闻标题",required = true)
    private String title;

    @ApiModelProperty(value = "新闻内容",required = true)
    private String content;

    @ApiModelProperty(value = "图片地址",required = true)
    private String  picUrl;
}
