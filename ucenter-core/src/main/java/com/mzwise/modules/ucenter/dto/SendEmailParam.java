package com.mzwise.modules.ucenter.dto;

import com.mzwise.constant.SendEmailCodeCheckEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SendEmailParam {

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "地区码")
    private String areaCode;

    @ApiModelProperty(value = "数据库是否存在该邮箱")
    private SendEmailCodeCheckEnum checkEmail;
}
