package com.mzwise.constant;

import com.baomidou.mybatisplus.core.enums.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author Administrator
 * @Date 2021/01/15
 */
@AllArgsConstructor
@Getter
public enum CarousePositionTypeEnum implements IEnum<Integer> {
    /**
     * 首页轮播图
     */
    HOME_PAGE_CAROUSE(1, "HOME_PAGE_CAROUSE"),
    /**
     * 行情
     */
    MARKET(2, "MARKET");


    private final Integer value;
    private final String name;

}
