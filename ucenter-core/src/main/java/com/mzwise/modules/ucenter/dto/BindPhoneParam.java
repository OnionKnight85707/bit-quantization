package com.mzwise.modules.ucenter.dto;

import com.mzwise.common.dto.PayPasswordParam;
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
public class BindPhoneParam implements PayPasswordParam {

    @NotEmpty
    @ApiModelProperty(value = "区号", required = true)
    private String areaCode;

    @NotEmpty
    @ApiModelProperty(value = "手机号", required = true)
    private String phone;

    @NotEmpty
    @ApiModelProperty(value = "验证码", required = true)
    private String code;

    @NotEmpty
    @ApiModelProperty(value = "资金密码", required = true)
    private String payPassword;

    @Override
    public String getPayPassword() {
        return payPassword;
    }
}
