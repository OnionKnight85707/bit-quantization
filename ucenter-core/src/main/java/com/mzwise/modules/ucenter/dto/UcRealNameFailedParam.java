package com.mzwise.modules.ucenter.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class UcRealNameFailedParam {
    @ApiModelProperty(value = "实名认证id")
    private Long realNameVerifiedId;

    @ApiModelProperty(value = "实名认证失败原因")
    private String failedReason;
}
