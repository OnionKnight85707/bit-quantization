package com.mzwise.modules.ucenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mzwise.constant.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author admin
 * @since 2021-03-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("uc_member")
@ApiModel(value = "UcMember对象", description = "")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UcMember implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户名（登录用）")
    private String username;

    @ApiModelProperty(value = "登录密码")
    private String password;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "区号")
    private String areaCode;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "是否开启手机验证")
    private Boolean phoneVerify;

    @ApiModelProperty(value = "是否开启邮箱验证")
    private Boolean emailVerify;

    @ApiModelProperty(value = "设备id ")
    private String clientId;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "交易密码")
    private String payPassword;

    @ApiModelProperty(value = "是否设置交易密码")
    private Boolean isSetPayPassword;

    @ApiModelProperty(value = "身份证号")
    private String idNumber;

    @ApiModelProperty(value = "上级id")
    private Long parentId;

    @ApiModelProperty(value = "上级昵称")
    private String parentNickname;

    @ApiModelProperty(value = "是否为有效用户")
    private Boolean isEffective;

    @ApiModelProperty(value = "生效时间")
    private Date effectiveTime;

    @ApiModelProperty(value = "量化等级")
    private QuantificationLevelEnum quantificationLevel;

    @ApiModelProperty("矿机是否激活")
    private Boolean miningIsEff;

    @ApiModelProperty(value = "下一级人员数量")
    private Integer firstLevelNum;

    @ApiModelProperty(value = "登录次数")
    private Integer loginCount;

    @ApiModelProperty(value = "邀请码")
    private String promotionCode;

    @ApiModelProperty(value = "跟随人数")
    private Integer numberOfFollow;

    @ApiModelProperty(value = "语言_国家")
    private String languageCountry;

    @ApiModelProperty(value = "实名状态,1:未实名，2：审核中，3：已实名，4：实名审核失败")
    private RealNameStatusEnum realNameStatus;

    @ApiModelProperty(value = "是否绑定银行卡")
    private Boolean isBindBankCard;

    @ApiModelProperty(value = "是否已填写过风险测评(弃用)")
    private Boolean isCompletedQuestionnaire;

    @ApiModelProperty(value = "注册时间")
    private Date registrationTime;

    @ApiModelProperty(value = "上次登录时间")
    private Date lastLoginTime;

    @ApiModelProperty(value = "上次登录ip")
    private String lastLoginIp;

    @ApiModelProperty(value = "ip地区")
    private String ipRegion;

    @ApiModelProperty(value = "账号状态，0：不可用，1：可用")
    private MemberStatusEnum status;

    @ApiModelProperty(value = "账户禁用说明")
    private String disableInstructions;

    @ApiModelProperty(value = "风险类型: CAUTIOUS(谨慎型),STEADY(稳健型),BALANCED(平衡型),AGGRESSIVE(进取型),RADICAL(激进型)")
    private RiskTypeEnum riskType;

    @ApiModelProperty(value = "是否是合伙人")
    private Boolean isPartner;

    @ApiModelProperty(value = "合伙人等级id")
    private Integer partnerLevelId;

    @ApiModelProperty(value = "合伙人等级")
    @TableField(exist = false)
    private PartnerLevelEnum partnerLevel;

    @ApiModelProperty(value = "合伙人佣金比例")
    private BigDecimal partnerCommissionRate;

    @ApiModelProperty(value = "合伙人总返佣")
    private BigDecimal partnerTotalCommission;

    @ApiModelProperty(value = "是否绑定谷歌身份验证器")
    private Boolean isBindGoogleAuthenticator;

    @ApiModelProperty(value = "欠费金额")
    private BigDecimal unPay;

    @ApiModelProperty(value = "欠费提醒次数")
    private Integer reminderTimes;

    @ApiModelProperty(value = "用户等级")
    private Integer userTypeId;

    @ApiModelProperty(value = "上次登录时间")
    private Date modifyTime;

}
