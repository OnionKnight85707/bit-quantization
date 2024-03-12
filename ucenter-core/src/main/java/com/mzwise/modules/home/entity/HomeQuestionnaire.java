package com.mzwise.modules.home.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
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
 * @since 2021-03-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("home_questionnaire")
@ApiModel(value="HomeQuestionnaire对象", description="")
public class HomeQuestionnaire implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "题干")
    private String subject;

    @ApiModelProperty(value = "选项，json数组")
    private String options;

    @ApiModelProperty(value = "选项对应的分数")
    private String scores;

    @ApiModelProperty(value = "权重")
    private Integer weight;

    @ApiModelProperty(value = "跳转，跳转, 如 {0: 6} 代表选A(第0个选项)跳转至第6题")
    private String jumpOption;

    @ApiModelProperty(value = "语言")
    private String language;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "逻辑删除")
    private Boolean deleted;


}
