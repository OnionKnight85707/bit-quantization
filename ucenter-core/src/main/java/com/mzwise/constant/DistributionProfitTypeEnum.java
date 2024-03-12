package com.mzwise.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author piao
 * @Date 2021/05/25
 */
@Getter
@AllArgsConstructor
public enum DistributionProfitTypeEnum implements IEnum<Integer> {
    /**
     * 分享奖
     */
    SHARE(0, "SHARE"),
    /**
     * 社区奖
     */
    COMMUNITY(1, "COMMUNITY"),
    /**
     * 分红奖
     */
    DIVIDENDS(2, "DIVIDENDS");


    private final Integer value;
    private final String name;

}
