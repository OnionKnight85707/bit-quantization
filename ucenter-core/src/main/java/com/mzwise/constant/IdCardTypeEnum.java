package com.mzwise.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author piao
 */
@AllArgsConstructor
@Getter
public enum IdCardTypeEnum implements IEnum<Integer> {
    /**
     * 身份证
     */
    IDENTITY_CARD(1 , "IDENTITY_CARD"),
    /**
     * 护照
     */
    PASSPORT(2, "PASSPORT"),
    /**
     * 其他
     */
    OTHER(3, "OTHER");

    private final Integer value;

    private final String name;

}
