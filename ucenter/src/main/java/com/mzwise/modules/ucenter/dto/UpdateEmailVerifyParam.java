package com.mzwise.modules.ucenter.dto;

import com.mzwise.common.dto.CustomVerifyParam;
import com.mzwise.common.dto.MemberUpdateParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateEmailVerifyParam implements CustomVerifyParam, MemberUpdateParam {

    @ApiModelProperty("手机验证码")
    private String phoneCode;

    @ApiModelProperty("邮箱验证码")
    private String emailCode;

    @ApiModelProperty("谷歌验证码")
    private String googleCode;

    @ApiModelProperty(value = "是否开启邮箱验证")
    private Boolean emailVerify;
}
