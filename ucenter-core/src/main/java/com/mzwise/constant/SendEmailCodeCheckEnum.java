package com.mzwise.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 验证数据库是否存在邮箱枚举
 * @Author 666
 * @Date 2022/08/23
 */
@AllArgsConstructor
@Getter
public enum SendEmailCodeCheckEnum implements IEnum<Integer> {

    /**
     * 直接跳过不校验
     */
    PASS(0, "PASS"),

    /**
     * 校验存在才通过
     */
    CHECK(1, "CHECK"),

    /**
     * 校验不存在才通过
     */
    CHECK_NOTEXIST(2, "CHECK_NOTEXIST");

    private final Integer value;
    private final String name;

}
