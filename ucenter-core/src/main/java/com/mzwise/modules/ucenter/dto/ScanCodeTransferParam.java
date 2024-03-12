package com.mzwise.modules.ucenter.dto;

import com.mzwise.constant.UnitEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author piao
 * @Date 2021/06/02
 */
@Data
@ApiModel("扫码转账所需参数")
public class ScanCodeTransferParam {
    @ApiModelProperty(value = "会员id", required = true)
    private Long memberId;

    @ApiModelProperty(value = "转账币种", required = true)
    private UnitEnum unitEnum;

    @ApiModelProperty(value = "转账金额", required = true)
    private BigDecimal amount;

    @ApiModelProperty(value = "备注", required = false)
    private String remark;

    @ApiModelProperty(value = "资金密码", required = true)
    private String payPassword;
}
