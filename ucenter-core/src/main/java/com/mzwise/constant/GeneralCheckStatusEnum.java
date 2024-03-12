package com.mzwise.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author Administrator
 * @Date 2021/01/20
 */
@AllArgsConstructor
@Getter
public enum GeneralCheckStatusEnum implements IEnum<Integer> {
    /**
     * 申请中
     */
    APPLYING(1,"APPLYING"),
    /**
     * 审核通过
     */
    PASSED(2,"PASSED"),
    /**
     * 审核不通过
     */
    FAILED(3,"FAILED");

    private final Integer value;
    private final String name;
}
