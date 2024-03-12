package com.mzwise.common.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FrozenSettleMsg {
    /**
     * 策略id
     */
    private String id;

    /**
     * 是否停止策略
     */
    private boolean stop;

    /**
     * 平仓的币种数量
     */
    private  int total;
}
