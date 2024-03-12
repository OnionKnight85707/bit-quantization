package com.mzwise.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author Administrator
 * @Date 2021/01/29
 */
@AllArgsConstructor
@Getter
public enum RealNameStatusEnum implements IEnum<Integer> {
    /**
     * 未实名
     */
    UN_REAL_NAMED(1,"UN_REAL_NAMED"),
    /**
     * 待审核
     */
    CHECKING(2,"CHECKING"),
    /**
     * 已实名
     */
    REAL_NAMED(3,"REAL_NAMED"),
    /**
     * 实名认证失败
     */
    REAL_NAME_FAILED(4,"REAL_NAME_FAILED")
    ;

    private final Integer value;
    private final String name;
}
