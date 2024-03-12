package com.mzwise.modules.ucenter.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.mzwise.constant.WalletTypeEnum;
import com.mzwise.constant.WithdrawStatusEnum;
import com.mzwise.constant.WithdrawStatusEnum;
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
@TableName("uc_withdraw")
@ApiModel(value = "UcWithdraw对象", description = "")
public class UcWithdraw implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long memberId;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "账户类型")
    private WalletTypeEnum walletType;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "申请总数量")
    private BigDecimal amount;

    @ApiModelProperty(value = "预计到账数量/到账数量")
    private BigDecimal arrivedAmount;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "完成时间")
    private Date dealTime;

    @ApiModelProperty(value = "手续费")
    private BigDecimal fee;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "提币状态")
    private WithdrawStatusEnum status;

    @ApiModelProperty(value = "币种")
    private String symbol;

    @ApiModelProperty(value = "链上交易id")
    private String txid;

    @ApiModelProperty(value = "拒绝理由")
    private String refuseReason;

    @ApiModelProperty("创建日期")
    private java.sql.Date createDate;
}
