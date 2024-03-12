package com.mzwise.modules.ucenter.vo;

import com.mzwise.constant.RiskTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Value;

/**
 * @Author wmf
 * @Date 2021/7/12 14:01
 * @Description
 */
@Data
public class MemberSimpleVO {

    @ApiModelProperty(value = "用户名（登录用）")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "风险类型: CAUTIOUS(谨慎型),STEADY(稳健型),BALANCED(平衡型),AGGRESSIVE(进取型),RADICAL(激进型)")
    private RiskTypeEnum riskType;

    @ApiModelProperty(value="是否绑定谷歌身份验证器")
    private Boolean isBindGoogleAuthenticator;

}
