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
public class UcMemberLoginByPhoneParam {
    @NotEmpty
    @ApiModelProperty(value = "手机", required = true)
    private String phone;
    @NotEmpty
    @ApiModelProperty(value = "验证码", required = true)
    private String code;
    @ApiModelProperty(value = "设备id", required = false)
    private String cid;
}
