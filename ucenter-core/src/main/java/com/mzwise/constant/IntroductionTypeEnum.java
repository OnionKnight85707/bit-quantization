package com.mzwise.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author Administrator
 * @Date 2021/01/14
 */
@AllArgsConstructor
@Getter
public enum IntroductionTypeEnum implements IEnum<Integer> {
    /**
     * 系统说明
     */
    SYSTEM(0, "SYSTEM"),

    AGREEMENT(1, "AGREEMENT");

    private final Integer value;
    private final String name;
}
