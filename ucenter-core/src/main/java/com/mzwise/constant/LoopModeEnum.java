package com.mzwise.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 循环模式类型枚举
 */
@AllArgsConstructor
@Getter
public enum LoopModeEnum implements IEnum<Integer> {

    /**
     * 循环一次
     */
    LOOP_ONCE(1, "LOOP_ONCE"),

    /**
     * 循环多次
     */
    LOOP_MULTIPLE(2, "LOOP_MULTIPLE");

    private final Integer value;
    private final String name;

}
