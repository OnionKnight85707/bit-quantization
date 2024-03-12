package com.mzwise.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RiskTypeEnum implements IEnum<Integer> {
    // 风险类型: CAUTIOUS(谨慎型),STEADY(稳健型),BALANCED(平衡型),AGGRESSIVE(进取型),RADICAL(激进型)
    /**
     * 谨慎型(1-20分)
     */
    CAUTIOUS(0, "CAUTIOUS"),
    /**
     * 稳健型(20-40分)
     */
    STEADY(1, "STEADY"),
    /**
     * 平衡型(40-60分)
     */
    BALANCED(2, "BALANCED"),
    /**
     * 进取型(60-80分)
     */
    AGGRESSIVE(3, "AGGRESSIVE"),
    /**
     * 激进型(80-100分)
     */
    RADICAL(4, "RADICAL");
    private final Integer value;
    private final String name;
}
