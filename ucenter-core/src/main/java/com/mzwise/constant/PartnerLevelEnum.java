package com.mzwise.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 合伙人等级枚举
 * @author 666
 * @since 2022-08-20
 */
@AllArgsConstructor
@Getter
public enum PartnerLevelEnum implements IEnum<Integer> {

    /**
     * 白银合伙人
     */
    SILVER(1, "SILVER"),

    /**
     * 黄金合伙人
     */
    GOLD(2, "GOLD"),

    /**
     * 钻石合伙人
     */
    DIAMOND(3, "DIAMOND");

    private final Integer value;
    private final String name;

    public static PartnerLevelEnum getPartnerLevelEnum(Integer value) {
        PartnerLevelEnum[] values = PartnerLevelEnum.values();
        for (PartnerLevelEnum temp : values) {
            if (temp.getValue().equals(value)) {
                return temp;
            }
        }
        return null;
    }

}
