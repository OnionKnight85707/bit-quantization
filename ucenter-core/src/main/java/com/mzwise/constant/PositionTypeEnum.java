package com.mzwise.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 策略模板仓位类型枚举
 */
@AllArgsConstructor
@Getter
public enum PositionTypeEnum implements IEnum<Integer> {

    /**
     *按照输入金额
     */
    BY_INPUT(1, "BY_INPUT"),

    /**
     *按照钱包余额
     */
    BY_WALLET(2, "BY_WALLET");

    private final Integer value;
    private final String name;

}
