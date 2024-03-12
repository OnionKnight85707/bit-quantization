package com.mzwise.modules.ucenter.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "用户合伙人参数")
public class UcenterPartnerParam {

    @ApiModelProperty(value = "用户id",required = true)
    private Long id;

//    @ApiModelProperty(value = "用户名（登录用）", required = true)
//    private String username;
//
//    @ApiModelProperty(value = "上级id",required = true)
//    private Long parentId;

    @ApiModelProperty(value = "是否开通合伙人",required = true)
    private Boolean isPartner;

    @ApiModelProperty(value = "合伙人返佣比例",required = true)
    private BigDecimal partnerCommissionRate;
}


