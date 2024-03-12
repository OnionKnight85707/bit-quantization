package com.binance.client.examples.exchange.market;

import com.binance.client.examples.exchange.constants.PrivateConfig;
import com.binance.client.spot.SpotRequestClient;
import com.binance.client.spot.SpotRequestOptions;

public class GetSymbolOrderBookTicker {
    public static void main(String[] args) {
        SpotRequestOptions options = new SpotRequestOptions();
        SpotRequestClient spotRequestClient = SpotRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);
        System.out.println(spotRequestClient.getSymbolOrderBookTicker("BTCUSDT"));
        // System.out.println(syncRequestClient.getSymbolOrderBookTicker(null));
    }
}
