package com.mzwise.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 外部信号类型枚举
 * @Author LiangZaiChao
 * @Date 2022/8/16 16:05
 */
@AllArgsConstructor
@Getter
public enum OutSignalTypeEnum implements IEnum<Integer> {

    /**
     *  内部信号
     */
    INNER(0,"INNER"),


    /**
     * 正常
     */
    NORMAL(1, "NORMAL"),

    /**
     * 只读
     */
    READ_ONLY(2, "READ_ONLY");


    private final Integer value;

    private final String name;

}
