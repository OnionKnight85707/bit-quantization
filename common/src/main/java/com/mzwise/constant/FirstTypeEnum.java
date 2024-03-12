package com.mzwise.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;


/**
 * 首单 方式：  1 按照百分比   2按照金额USDT
 */

@AllArgsConstructor
@Getter
public enum FirstTypeEnum {

    /**
     *按照百分比
     */
    BY_PERCENT(1, "BY_PERCENT"),


    /**
     *按照金额USDT
     */
    BY_AMOUNT(2, "BY_AMOUNT");

    private final Integer value;

    private final String name;

    public static  FirstTypeEnum fromValue(Integer v)
    {
        return Arrays.stream(FirstTypeEnum.values()).filter(a->a.getValue().equals(v)).findFirst().orElseThrow(()->new IllegalArgumentException("不支持的FirstTypeEnum类型"));
    }
}