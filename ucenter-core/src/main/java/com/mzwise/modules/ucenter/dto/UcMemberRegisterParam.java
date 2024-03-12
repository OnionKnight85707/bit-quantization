package com.mzwise.modules.ucenter.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * 用户登录参数
 * Created by admin on 2018/4/26.
 */
@Data
public class UcMemberRegisterParam {
//    @NotEmpty
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @NotEmpty
//    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,40}$", message = "用户密码长度8~40个字符，至少包含字母和数字")
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    @NotEmpty
    @ApiModelProperty(value = "验证码")
    private String code;
    @ApiModelProperty(value = "推荐码")
    private String promotionCode;
}
