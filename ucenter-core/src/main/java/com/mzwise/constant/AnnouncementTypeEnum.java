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
public enum AnnouncementTypeEnum implements IEnum<Integer> {
    /**
     * 存取款安排
     */
    DEPOSIT_AND_WITHDRAWAL_ARRANGEMENTS(1,"DEPOSIT_AND_WITHDRAWAL_ARRANGEMENTS"),

    /**
     * 交易时间安排
     */
    TRADING_SCHEDULE_ARRANGEMENTS(2,"TRADING_SCHEDULE_ARRANGEMENTS"),

    /**
     * 合约到期
     */
    CONTRACT_EXPIRED(3,"CONTRACT_EXPIRED"),

    /**
     * 其他
     */
    OTHER(4,"OTHER");

    private final Integer value;
    private final String name;
}


