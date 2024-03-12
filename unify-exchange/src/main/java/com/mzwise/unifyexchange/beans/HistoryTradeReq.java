package com.mzwise.unifyexchange.beans;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryTradeReq extends Common {

    /**
     * 交易对
     */
    private String symbol;

    /**
     * 如果startTime 和 endTime 均未发送, 只会返回最近7天的数据
     * startTime 和 endTime 的最大间隔为7天
     */
    private Long startTime;

    private Long endTime;

    /**
     * 返回该fromId及之后的成交，缺省返回最近的成交
     */
    private Long fromId;


    /**
     * 返回的结果集数量 默认值:500 最大值:1000.
     */
    private Integer limit=500;


}