package com.mzwise.modules.ucenter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ApiModel(value = "合伙人参数", description = "")
public class UcPartnerParam {

    @ApiModelProperty(value = "用户id", required = true)
    @NotNull
    private Long id;

//    @ApiModelProperty(value = "用户名（登录用）", required = true)
//    private String username;
//
//    @ApiModelProperty(value = "上级id", required = true)
//    private Long parentId;

    @ApiModelProperty(value = "是否开通合伙人", required = true)
    @NotNull
    private Boolean isPartner;

    @ApiModelProperty(value = "合伙人等级id")
    private Integer partnerLevelId;

    @ApiModelProperty(value = "合伙人佣金比例", required = true)
    @NotNull
    private BigDecimal partnerCommissionRate;
}
