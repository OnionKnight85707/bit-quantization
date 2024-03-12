package com.mzwise;

import com.huobi.client.TradeClient;
import com.huobi.client.req.trade.SubOrderUpdateV2Request;
import com.huobi.constant.HuobiOptions;

public class SubHuobi {
    public static void main(String[] args) {
        TradeClient tradeService = TradeClient.create(HuobiOptions.builder()
                .apiKey("82daa658-b61872db-e180879a-frbghq7rnm")
                .secretKey("2dcc2de4-9a024b45-df959c0e-6bf51")
                .build());
        tradeService.subOrderUpdateV2(SubOrderUpdateV2Request.builder().symbols("*").build(), orderUpdateV2Event -> {

            System.out.println(orderUpdateV2Event.toString());

        });
    }
}
