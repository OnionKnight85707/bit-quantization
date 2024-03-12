package com.binance.client.examples.swap.trade;

import com.alibaba.fastjson.JSONArray;
import com.binance.client.future.FutureRequestOptions;
import com.binance.client.future.FutureRequestClient;
import com.binance.client.examples.swap.constants.PrivateConfig;


/**
 * @author : wangwanlu
 * @since : 2020/4/7, Tue
 **/
public class BatchCancelOrders {

    public static void main(String[] args) {
        FutureRequestOptions options = new FutureRequestOptions();
        FutureRequestClient futureRequestClient = FutureRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);

        // batch cancel by order ids
        JSONArray orderIds = new JSONArray();
        orderIds.add(180L);
        orderIds.add(181L);
        System.out.println(futureRequestClient.batchCancelOrders("BTCUSDT", orderIds.toJSONString(), null));

        // batch cancel by client order ids
//        JSONArray origClientOrderIds = new JSONArray();
//        origClientOrderIds.add("cli_order_001");
//        origClientOrderIds.add("cli_order_002");
//        System.out.println(syncRequestClient.batchCancelOrders("BTCUSDT", null, origClientOrderIds.toJSONString()));
    }
}
