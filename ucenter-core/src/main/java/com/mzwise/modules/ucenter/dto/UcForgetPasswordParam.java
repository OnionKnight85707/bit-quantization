package com.mzwise.modules.ucenter.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UcForgetPasswordParam {

    @ApiModelProperty("新交易密码")
    private String newPassword;

    @ApiModelProperty("邮箱验证码")
    private String inputCode;

}
