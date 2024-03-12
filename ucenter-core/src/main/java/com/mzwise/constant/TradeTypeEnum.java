package com.mzwise.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TradeTypeEnum implements IEnum<Integer> {
    /**
     * 现货
     */
    EXCHANGE(0, "EXCHANGE"),
    /**
     * 合约
     */
    SWAP(1, "SWAP");

    private final Integer value;
    private final String name;
}