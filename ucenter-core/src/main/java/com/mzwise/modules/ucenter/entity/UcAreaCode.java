package com.mzwise.modules.ucenter.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("uc_area_code")
@ApiModel(value = "UcAreaCode对象",description = "")
public class UcAreaCode implements Serializable {

    @TableId("id")
    private Long id;

    @ApiModelProperty(value = "区号")
    private String areaCode;

    @ApiModelProperty(value = "国家/地区")
    private String region;

    @ApiModelProperty(value = "洲")
    private String continent;

    @ApiModelProperty(value = "主流国家")
    private Integer majorNational;
}
