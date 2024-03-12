package com.mzwise.common.dto;

/**
 * 用户自定义验证方式
 */
public interface CustomVerifyParam {


//    @ApiModelProperty("手机验证码")
//    private String phoneCode;
//
//    @ApiModelProperty("邮箱验证码")
//    private String emailCode;

    String getPhoneCode();

    String getEmailCode();

    String getGoogleCode();
}