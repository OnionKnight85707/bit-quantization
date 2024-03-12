package com.mzwise.modules.ucenter.dto;

import com.mzwise.constant.AnnouncementTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Administrator
 * @Date 2021/01/14
 */
@Data
public class UcAnnouncementParam {
    @ApiModelProperty(value = "语言",required = true)
    private String language;

    @ApiModelProperty(value = "是否置顶",required = true)
    private Boolean isTop;

    @ApiModelProperty(value = "是否显示",required = true)
    private Boolean isShow;

    @ApiModelProperty(value = "总标题",required = true)
    private String title;

    @ApiModelProperty(value = "标签",required = true)
    private AnnouncementTypeEnum label;

    @ApiModelProperty(value = "内容",required = true)
    private String content;

}
