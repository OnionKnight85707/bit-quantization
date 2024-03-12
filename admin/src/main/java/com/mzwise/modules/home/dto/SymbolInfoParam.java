package com.mzwise.modules.home.dto;

import com.mzwise.constant.PlatformEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author wmf
 * @Date 2021/7/20 12:19
 * @Description
 */
@Data
public class SymbolInfoParam {
    @ApiModelProperty(value = "交易对")
    private String symbolPair;

    @ApiModelProperty(value = "平台")
    private PlatformEnum platform;
}
