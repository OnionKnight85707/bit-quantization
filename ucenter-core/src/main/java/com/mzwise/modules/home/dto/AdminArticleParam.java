package com.mzwise.modules.home.dto;

import com.mzwise.constant.ArticleTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author piao
 * @Date 2021/06/02
 */
@ApiModel("后台新增财经新闻")
@Data
public class AdminArticleParam {
    @ApiModelProperty(value = "语言", required = true)
    private String language;

    @ApiModelProperty(value = "分类", required = true)
    private ArticleTypeEnum type;

    @ApiModelProperty(value = "图片", required = true)
    private String pic;

    @ApiModelProperty(value = "标题", required = true)
    private String title;

    @ApiModelProperty(value = "详情", required = true)
    private String content;

    @ApiModelProperty("是否置顶")
    private Boolean top;
}
