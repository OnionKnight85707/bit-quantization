package com.mzwise.modules.ucenter.dto;

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
public class BindEmailParam {
    @NotEmpty
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;
    @NotEmpty
    @ApiModelProperty(value = "验证码", required = true)
    private String code;
}
