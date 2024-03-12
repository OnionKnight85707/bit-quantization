package com.binance.client.examples.exchange.trade;

import com.binance.client.examples.exchange.constants.PrivateConfig;
import com.binance.client.spot.SpotRequestClient;
import com.binance.client.spot.SpotRequestOptions;

public class GetAllOrders {
    public static void main(String[] args) {
        SpotRequestOptions options = new SpotRequestOptions();
        SpotRequestClient spotRequestClient = SpotRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);
        System.out.println(spotRequestClient.getAllOrders("BTCUSDT", null, null, null, 10));
        // System.out.println(syncRequestClient.getAllOrders("BTCUSDT", null, null, null, null));
    }
}