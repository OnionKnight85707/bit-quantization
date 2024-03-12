package com.binance.client.examples.swap.market;

import com.binance.client.future.FutureRequestOptions;
import com.binance.client.future.FutureRequestClient;
import com.binance.client.examples.swap.constants.PrivateConfig;
import com.binance.client.future.model.enums.PeriodType;

public class GetOpenInterestStat {
    public static void main(String[] args) {
        FutureRequestOptions options = new FutureRequestOptions();
        FutureRequestClient futureRequestClient = FutureRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);
        System.out.println(futureRequestClient.getOpenInterestStat("BTCUSDT", PeriodType._5m,null,null,10));


    }
}
