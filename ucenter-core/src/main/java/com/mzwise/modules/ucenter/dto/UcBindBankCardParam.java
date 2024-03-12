package com.mzwise.modules.ucenter.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Administrator
 * @Date 2021/01/15
 */
@Data
public class UcBindBankCardParam {
    @ApiModelProperty(value = "银行名称，如中国工商银行",required = true)
    private String bankName;

    @ApiModelProperty(value = "银行卡号",required = true)
    private String bankCardNumber;

    @ApiModelProperty(value = "开户地址，如中国 湖北省武汉市xx区",required = true)
    private String accountOpeningAddress;

    @ApiModelProperty(value = "开户支行名称",required = true)
    private String accountOpeningBranch;
}
