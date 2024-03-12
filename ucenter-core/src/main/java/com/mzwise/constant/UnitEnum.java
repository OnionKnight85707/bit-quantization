package com.mzwise.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author piao
 * @Date 2021/05/21
 */
@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
@Getter
@AllArgsConstructor
@ApiModel("币种类型")
public enum UnitEnum implements IEnum<Integer> {
    @ApiModelProperty("USDT")
    USDT(0, "USDT"),


    FIL(1,"FIL"),
    BTE(2, "BTE");

    private final Integer value;
    private final String name;
}
