package com.mzwise.modules.admin.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author piao
 * @Date 2021/03/11
 */
@Data
public class PasswordParam {
    @NotEmpty
    @ApiModelProperty("旧密码")
    private String oldPassword;

    @NotEmpty
    @ApiModelProperty("新密码")
    private String newPassword;
}
