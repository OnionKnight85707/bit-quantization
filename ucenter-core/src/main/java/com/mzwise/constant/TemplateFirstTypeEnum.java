package com.mzwise.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 策略模板首单类型枚举
 * @Author LiangZaiChao
 * @Date 2022/8/3 11:14
 */
@AllArgsConstructor
@Getter
public enum TemplateFirstTypeEnum implements IEnum<Integer> {

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

}
