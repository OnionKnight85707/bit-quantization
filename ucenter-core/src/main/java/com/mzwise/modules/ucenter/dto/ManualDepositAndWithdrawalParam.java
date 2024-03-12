package com.mzwise.modules.ucenter.dto;

import com.mzwise.constant.UnitEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author piao
 * @Date 2021/06/01
 */
@Data
@ApiModel("手动充提币参数")
public class ManualDepositAndWithdrawalParam {
    @ApiModelProperty("会员id")
    private Long memberId;

    @ApiModelProperty("充币减币金额")
    private BigDecimal amount;

    @ApiModelProperty("充提币币种")
    private UnitEnum unitEnum;
}
