package com.mzwise.modules.ucenter.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.mzwise.constant.RechargeTypeEnum;
import com.mzwise.constant.WalletTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author admin
 * @since 2021-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("uc_recharge")
@ApiModel(value = "UcRecharge对象", description = "")
public class UcRecharge implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("充币地址")
    private String address;

    @ApiModelProperty("会员id")
    private Long memberId;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty("币种")
    private String symbol;

    @ApiModelProperty("充值钱包类型")
    private WalletTypeEnum walletType;

    @ApiModelProperty("充值类型")
    private RechargeTypeEnum rechargeType;

    @ApiModelProperty("充值金额")
    private BigDecimal amount;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("链上交易id")
    private String txid;

    @ApiModelProperty("到账时间")
    private Date arrivalTime;

    @ApiModelProperty("创建日期")
    private java.sql.Date createDate;


}
