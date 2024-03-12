package com.mzwise.modules.ucenter.vo;

import com.mzwise.constant.MemberStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author piao
 * @Date 2021/05/28
 */
@Data
@ApiModel("后台会员管理类")
public class AdminMemberVO {
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "邀请码")
    private String promotionCode;

    @ApiModelProperty(value = "上级id")
    private Long parentId;

    @ApiModelProperty(value = "上级昵称")
    private String parentNickname;

    @ApiModelProperty(value = "下一级人员数量")
    private Integer firstLevelNum;

    @ApiModelProperty("矿机是否激活")
    private Boolean miningIsEff;

    @ApiModelProperty("量化是否激活")
    private Boolean isEffective;

    @ApiModelProperty("量化账户资产")
    private BigDecimal quantifyAccount;

    @ApiModelProperty("平台币账户资产")
    private BigDecimal bteAccount;

    @ApiModelProperty("服务费账户")
    private BigDecimal serviceFeeAccount;

    @ApiModelProperty(value = "注册时间")
    private Date registrationTime;

    @ApiModelProperty(value = "账号状态，0：不可用，1：可用")
    private MemberStatusEnum status;

    @ApiModelProperty(value = "账户禁用说明")
    private String disableInstructions;

    @ApiModelProperty(value = "是否是合伙人")
    private Boolean isPartner;

    @ApiModelProperty(value = "合伙人等级id")
    private Integer partnerLevelId;

    @ApiModelProperty(value = "合伙人佣金比例")
    private BigDecimal partnerCommissionRate;

    @ApiModelProperty(value = "合伙人总返佣")
    private BigDecimal partnerTotalCommission;

    @ApiModelProperty(value = "上次登录时间")
    private Date lastLoginTime;

    @ApiModelProperty(value = "上次登录ip")
    private String lastLoginIp;

    @ApiModelProperty(value = "ip地区")
    private String ipRegion;

    @Override
    public String toString() {
        return "AdminMemberVO{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", promotionCode='" + promotionCode + '\'' +
                ", parentId=" + parentId +
                ", parentNickname='" + parentNickname + '\'' +
                ", firstLevelNum=" + firstLevelNum +
                ", miningIsEff=" + miningIsEff +
                ", isEffective=" + isEffective +
                ", quantifyAccount=" + quantifyAccount +
                ", bteAccount=" + bteAccount +
                ", serviceFeeAccount=" + serviceFeeAccount +
                ", registrationTime=" + registrationTime +
                ", status=" + status +
                ", disableInstructions='" + disableInstructions + '\'' +
                ", isPartner=" + isPartner +
                ", partnerCommissionRate=" + partnerCommissionRate +
                ", partnerTotalCommission=" + partnerTotalCommission +
                ", lastLoginTime=" + lastLoginTime +
                ", lastLoginIp='" + lastLoginIp + '\'' +
                ", ipRegion='" + ipRegion + '\'' +
                '}';
    }
}
