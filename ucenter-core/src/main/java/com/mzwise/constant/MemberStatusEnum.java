package com.mzwise.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Administrator
 */

@AllArgsConstructor
@Getter
public enum MemberStatusEnum implements IEnum<Integer> {
    /**
     * 不可用
     */
    DISABLED(0, "DISABLED"),
    /**
     * 可用
     */
    NORMAL(1, "NORMAL");
    private final Integer value;
    private final String name;
}