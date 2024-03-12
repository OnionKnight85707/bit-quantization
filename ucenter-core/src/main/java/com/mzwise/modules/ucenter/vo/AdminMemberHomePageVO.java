package com.mzwise.modules.ucenter.vo;

import com.mzwise.constant.RiskTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author piao
 * @Date 2021/05/31
 */
@Data
@ApiModel("后台用户详情-主页")
public class AdminMemberHomePageVO {
    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "邀请码")
    private String promotionCode;

    @ApiModelProperty(value = "风险评测结果")
    private RiskTypeEnum riskType;

}
