package com.mzwise.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 收益结算方式类型枚举
 */
@AllArgsConstructor
@Getter
public enum SettleTypeEnum implements IEnum<Integer> {

    /**
     * 实时结算
     */
    BY_REALTIME(1, "BY_REALTIME"),

    /**
     * 冻结结算
     */
    BY_FREEZE(2, "BY_FREEZE");

    private final Integer value;
    private final String name;

}
