package com.mzwise.modules.home.dto;

import com.mzwise.constant.CarousePositionTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Administrator
 * @Date 2021/01/15
 */
@Data
public class HomeCarouselPicParam {
    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "点击轮播图跳转的链接")
    private String link;

    @ApiModelProperty(value = "语言")
    private String language;

    @ApiModelProperty(value = "上传的图片位置 （如是首页轮播还是那个页面的图片）")
    private CarousePositionTypeEnum position;

    @ApiModelProperty(value = "图片url")
    private String picUrl;

    @ApiModelProperty(value = "是否显示")
    private Boolean isShow;
}
