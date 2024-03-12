package com.mzwise.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 策略模板止盈止损方式枚举
 * @Author LiangZaiChao
 * @Date 2022/8/3 11:14
 */
@AllArgsConstructor
@Getter
public enum TemplateStopModeEnum implements IEnum<Integer> {

    // 按照外部信号
    EXTERNAL_SIGNAL(1, "EXTERNAL_SIGNAL"),

    // 按照追踪止盈
    TRAILING_TAKE_PROFIT(2, "TRAILING_TAKE_PROFIT");

    private final Integer value;
    private final String name;

}
