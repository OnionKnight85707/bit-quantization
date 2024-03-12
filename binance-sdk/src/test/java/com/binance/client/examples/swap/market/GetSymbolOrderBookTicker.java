package com.binance.client.examples.swap.market;

import com.binance.client.future.FutureRequestOptions;
import com.binance.client.future.FutureRequestClient;

import com.binance.client.examples.swap.constants.PrivateConfig;

public class GetSymbolOrderBookTicker {
    public static void main(String[] args) {
        FutureRequestOptions options = new FutureRequestOptions();
        FutureRequestClient futureRequestClient = FutureRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);
        System.out.println(futureRequestClient.getSymbolOrderBookTicker("BTCUSDT"));
        // System.out.println(syncRequestClient.getSymbolOrderBookTicker(null));
    }
}
