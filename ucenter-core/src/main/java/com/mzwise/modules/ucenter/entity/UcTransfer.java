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
@TableName("uc_transfer")
@ApiModel(value="UcTransfer对象", description="")
public class UcTransfer implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long memberId;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "from 账户")
    private WalletTypeEnum fromType;

    @ApiModelProperty(value = "to 账户")
    private WalletTypeEnum toType;

    @ApiModelProperty(value = "币种")
    private String symbol;

    @ApiModelProperty(value = "划转金额")
    private BigDecimal amount;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


}
