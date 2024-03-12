package com.mzwise.modules.ucenter.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author piao
 * @Date 2021/03/09
 */
@Data
public class ResourceVO {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("资源名字")
    private String name;

    @ApiModelProperty("资源路径")
    private String url;

    @ApiModelProperty("资源描述")
    private String description;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("资源所属分类")
    private Long categoryId;

    @ApiModelProperty("分类名字")
    private String categoryName;
}
