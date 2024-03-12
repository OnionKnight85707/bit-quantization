package com.mzwise.modules.ucenter.vo;

import com.mzwise.constant.TransactionTypeEnum;
import com.mzwise.constant.WalletTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author piao
 * @Date 2021/06/01
 */
@Data
@ApiModel("后台资产——资产记录")
public class AdminAssetsRecordVO {
    @ApiModelProperty("会员id")
    private Long memberId;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "账户类型")
    private WalletTypeEnum walletType;

    @ApiModelProperty(value = "交易类型")
    private TransactionTypeEnum type;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
