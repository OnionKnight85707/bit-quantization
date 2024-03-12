package com.mzwise.modules.ucenter.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.mzwise.constant.TransactionTypeEnum;
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
@TableName("uc_transaction")
@ApiModel(value="UcTransaction对象", description="")
public class UcTransaction implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "会员id")
    private Long memberId;

    @ApiModelProperty(value = "账户类型")
    private WalletTypeEnum walletType;

    @ApiModelProperty(value = "交易类型")
    private TransactionTypeEnum type;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "手续费")
    private BigDecimal fee = BigDecimal.ZERO;

    @ApiModelProperty(value = "变动前金额")
    private BigDecimal amountBeforeChange = BigDecimal.ZERO;

    @ApiModelProperty(value = "变动后金额")
    private BigDecimal amountAfterChange = BigDecimal.ZERO;

    @ApiModelProperty(value = "状态，1：审核中，2：成功，3：失败")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "给用户备注")
    private String remarkUser;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private Integer refId;

}
