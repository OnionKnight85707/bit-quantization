package com.mzwise.modules.home.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.mzwise.constant.ArticleTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author admin
 * @since 2021-05-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("home_article")
@ApiModel(value = "HomeArticle对象", description = "")
public class HomeArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "语言")
    private String language;

    @ApiModelProperty(value = "复用分类")
    private ArticleTypeEnum type;

    @ApiModelProperty(value = "图片")
    private String pic;

    @ApiModelProperty(value = "跳转链接")
    private String url;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "详情")
    private String content;

    @ApiModelProperty("是否置顶")
    private Boolean top;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
