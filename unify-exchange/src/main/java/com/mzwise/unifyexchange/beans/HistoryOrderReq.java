package com.mzwise.unifyexchange.beans;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryOrderReq extends Common {

    /**
     * 交易对
     */
    private String symbol;


    private Long orderId;


    private String clientOrderId;

}