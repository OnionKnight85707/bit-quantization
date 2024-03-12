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
public enum ArticleTypeEnum implements IEnum<Integer> {
    /**
     * 财经日志
     */
    FINANCE(1, "FINANCE"),

    /**
     * 文档教程
     */
    API_TUTORIAL(2, "API_TUTORIAL");

    private final Integer value;
    private final String name;
}


