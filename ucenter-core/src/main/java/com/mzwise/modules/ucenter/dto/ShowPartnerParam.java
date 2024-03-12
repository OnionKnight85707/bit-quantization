package com.mzwise.modules.ucenter.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "回显合伙人参数",description = "")
public class ShowPartnerParam {



    @ApiModelProperty(value = "是否开通合伙人",required = true)
    private Boolean isPartner;

    @ApiModelProperty(value = "合伙人佣金比例",required = true)
    private BigDecimal partnerCommissionRate;
}


