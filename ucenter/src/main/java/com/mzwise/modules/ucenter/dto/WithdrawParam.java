package com.mzwise.modules.ucenter.dto;

import com.mzwise.annotation.CustomVerifyRequired;
import com.mzwise.common.dto.CustomVerifyParam;
import com.mzwise.common.dto.PayPasswordParam;
import com.mzwise.constant.WalletTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawParam implements PayPasswordParam, CustomVerifyParam {

    @ApiModelProperty("可选项中的type")
    private WalletTypeEnum walletType;

    @ApiModelProperty("充币金额")
    private BigDecimal amount;

    @ApiModelProperty("充币地址")
    private String address;

    @ApiModelProperty("交易密码")
    private String payPassword;

    @ApiModelProperty("手机验证码")
    private String phoneCode;

    @ApiModelProperty("邮箱验证码")
    private String emailCode;

    @ApiModelProperty("谷歌验证码")
    private String googleCode;
}
