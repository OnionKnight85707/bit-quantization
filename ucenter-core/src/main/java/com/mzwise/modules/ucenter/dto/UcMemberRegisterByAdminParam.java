package com.mzwise.modules.ucenter.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author Administrator
 * @Date 2021/01/26
 */
@Data
public class UcMemberRegisterByAdminParam {
    @ApiModelProperty(value = "手机号",required = true)
    private String phone;

    @ApiModelProperty(value = "昵称",required = true)
    private String nickname;

    @ApiModelProperty(value = "上级用户",required = false)
    private Long inviterId;

    @ApiModelProperty(value = "会员等级表id",required = true)
    private Long memberLevelId;

    @ApiModelProperty(value = "是否是信号源",required = true)
    private Boolean isSignal;

    @ApiModelProperty(value = "赠金",required = true)
    private BigDecimal givenMoney;

}
