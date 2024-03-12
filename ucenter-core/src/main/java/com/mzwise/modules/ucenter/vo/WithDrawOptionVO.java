package com.mzwise.modules.ucenter.vo;

import com.mzwise.modules.ucenter.entity.UcWallet;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Data
public class WithDrawOptionVO extends UcWallet {

    public WithDrawOptionVO(UcWallet wallet, Integer withdrawScale, BigDecimal withdrawFee) {
        BeanUtils.copyProperties(wallet, this);
        this.withdrawScale = withdrawScale;
        this.withdrawFee = withdrawFee;
    }

    @ApiModelProperty(value = "提币精度")
    private Integer withdrawScale;

    @ApiModelProperty(value = "提币手续费")
    private BigDecimal withdrawFee;
}
