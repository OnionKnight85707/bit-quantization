package com.mzwise.modules.ucenter.vo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class AreaCodeVO {

    @ApiModelProperty("区号")
    private String areaCode;

    @ApiModelProperty("地区")
    private String region;

    private Integer majorNational;
}
