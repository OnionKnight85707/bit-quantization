package com.mzwise.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户日志操作模块枚举
 * @author: David Liang
 * @create: 2022-07-26 18:52
 */
@AllArgsConstructor
@Getter
public enum MemberLogModuleEnum {

    RISK_ASSESSMENT(1, "风险评测");

    private final Integer value;
    private final String name;

}
