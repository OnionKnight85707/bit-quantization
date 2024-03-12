package com.mzwise.modules.ucenter.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 重置密码参数
 * Created by admin on 2018/4/26.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UcMemberResetPasswordParam {
    @NotEmpty
    @ApiModelProperty(value = "手机/邮箱",required = true)
    private String account;
    @NotEmpty
    @ApiModelProperty(value = "验证码",required = true)
    private String code;
    @NotEmpty
    @ApiModelProperty(value = "密码",required = true)
    private String password;
}
