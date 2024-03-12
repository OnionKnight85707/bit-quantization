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
public enum CommonQuestionTypeEnum implements IEnum<Integer> {
    /**
     * 交易问题
     */
    TRANSACTION_PROBLEM(1,"TRANSACTION_PROBLEM"),

    /**
     * 资金问题
     */
    FUNDING_PROBLEM(2,"FUNDING_PROBLEM"),

    /**
     * 账户问题
     */
    ACCOUNT_PROBLEM(3,"ACCOUNT_PROBLEM"),

    /**
     * 其他问题
     */
    OTHER_PROBLEM(4,"OTHER_PROBLEM");

    private final Integer value;
    private final String name;
}
