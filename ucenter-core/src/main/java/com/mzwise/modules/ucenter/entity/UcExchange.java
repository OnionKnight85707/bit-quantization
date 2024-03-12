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
@TableName("uc_exchange")
@ApiModel(value="UcExchange对象", description="")
public class UcExchange implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long memberId;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "from 账户")
    private WalletTypeEnum fromType;

    @ApiModelProperty(value = "from 币种")
    private String fromSymbol;

    @ApiModelProperty(value = "from 金额")
    private BigDecimal fromAmount;

    @ApiModelProperty(value = "to 账户")
    private WalletTypeEnum toType;

    @ApiModelProperty(value = "to 币种")
    private String toSymbol;

    @ApiModelProperty(value = "to 金额")
    private BigDecimal toAmount;

    @ApiModelProperty(value = "手续费 金额")
    private BigDecimal feeAmount;

    @ApiModelProperty(value = "手续费 币种")
    private String feeSymbol;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


}
