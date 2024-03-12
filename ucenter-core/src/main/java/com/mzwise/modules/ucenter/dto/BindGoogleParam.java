package com.mzwise.modules.ucenter.dto;

import com.mzwise.common.dto.PayPasswordParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class BindGoogleParam  implements  PayPasswordParam {

    @NotEmpty
    @ApiModelProperty(value = "谷歌私钥", required = true)
    private String googleKey;

    @NotEmpty
    @ApiModelProperty(value = "谷歌验证码", required = true)
    private String code;

    @NotEmpty
    @ApiModelProperty(value = "资金密码", required = true)
    private String payPassword;

    @Override
    public String getPayPassword() {
        return payPassword;
    }
}
