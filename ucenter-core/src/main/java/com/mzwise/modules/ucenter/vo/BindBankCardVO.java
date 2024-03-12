package com.mzwise.modules.ucenter.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author Administrator
 * @Date 2021/02/22
 */
@Data
public class BindBankCardVO {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "会员id")
    private Long memberId;

    @ApiModelProperty(value = "会员昵称")
    private String nickname;

    @ApiModelProperty(value = "银行名称，如中国工商银行")
    private String bankName;

    @ApiModelProperty(value = "银行卡号")
    private String bankCardNumber;

    @ApiModelProperty(value = "开户地址，如中国 湖北省武汉市xx区")
    private String accountOpeningAddress;

    @ApiModelProperty(value = "开户支行名称")
    private String accountOpeningBranch;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}
