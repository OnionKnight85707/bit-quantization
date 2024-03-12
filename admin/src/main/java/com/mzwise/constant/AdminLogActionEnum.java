package com.mzwise.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 管理员日志操作动作枚举
 * @author: David Liang
 * @create: 2022-07-21 16:00
 */
@AllArgsConstructor
@Getter
public enum AdminLogActionEnum {

    ADD(1, "新增"),

    UPDATE(2, "修改"),

    DELETE(3, "删除");

    private final Integer value;
    private final String name;
}
