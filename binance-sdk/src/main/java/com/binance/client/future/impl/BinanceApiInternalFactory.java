package com.binance.client.future.impl;

import com.binance.client.future.FutureRequestOptions;
import com.binance.client.future.SubscriptionClient;
import com.binance.client.future.SubscriptionOptions;
import com.binance.client.future.FutureRequestClient;
import java.net.URI;

public final class BinanceApiInternalFactory {

    private static final BinanceApiInternalFactory instance = new BinanceApiInternalFactory();

    public static BinanceApiInternalFactory getInstance() {
        return instance;
    }

    private BinanceApiInternalFactory() {
    }

    public FutureRequestClient createSyncRequestClient(String apiKey, String secretKey, FutureRequestOptions options) {
        FutureRequestOptions futureRequestOptions = new FutureRequestOptions(options);
        RestApiRequestImpl requestImpl = new RestApiRequestImpl(apiKey, secretKey, futureRequestOptions);
        return new FutureRequestImpl(requestImpl);
    }

    public SubscriptionClient createSubscriptionClient(String apiKey, String secretKey, SubscriptionOptions options) {
        SubscriptionOptions subscriptionOptions = new SubscriptionOptions(options);
        FutureRequestOptions futureRequestOptions = new FutureRequestOptions();
        try {
            String host = new URI(options.getUri()).getHost();
            futureRequestOptions.setUrl("https://" + host);
        } catch (Exception e) {

        }
        SubscriptionClient webSocketStreamClient = new WebSocketStreamClientImpl(apiKey, secretKey,
                subscriptionOptions);
        return webSocketStreamClient;
    }

}
