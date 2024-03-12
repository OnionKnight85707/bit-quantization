package com.mzwise.common.vo;

import lombok.Data;

@Data
public class LianxuData {

    /**
     * 20 线 连续 在60上面 多少根K线
     */

    private int ava20AboveAva60Times;


    /**
     * 20 线 连续 在60下面 多少根K线
     */
    private int ava20belowAva60Times;

    public LianxuData(int ava20AboveAva60Times, int ava20belowAva60Times) {
        this.ava20AboveAva60Times = ava20AboveAva60Times;
        this.ava20belowAva60Times = ava20belowAva60Times;
    }
}
