package com.mzwise.modules.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 用户登录参数
 * Created by admin on 2018/4/26.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UmsAdminLoginParam {
    @NotEmpty
    @ApiModelProperty(value = "用户名",required = true)
    private String username;
    @NotEmpty
    @ApiModelProperty(value = "密码",required = true)
    private String password;

    @NotEmpty
    @ApiModelProperty(value = "验证码",required = true)
    private String code;
}