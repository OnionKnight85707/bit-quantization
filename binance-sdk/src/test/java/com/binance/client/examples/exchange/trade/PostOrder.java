package com.binance.client.examples.exchange.trade;

import com.binance.client.examples.exchange.constants.PrivateConfig;
import com.binance.client.spot.SpotRequestClient;
import com.binance.client.spot.SpotRequestOptions;
import com.binance.client.spot.model.enums.NewOrderRespType;
import com.binance.client.spot.model.enums.OrderSide;
import com.binance.client.spot.model.enums.OrderType;
import com.binance.client.spot.model.enums.TimeInForce;

public class PostOrder {
    public static void main(String[] args) {
        SpotRequestOptions options = new SpotRequestOptions();
        SpotRequestClient spotRequestClient = SpotRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);
//        System.out.println(syncRequestClient.postOrder("BTCUSDT", OrderSide.SELL, PositionSide.BOTH, OrderType.LIMIT, TimeInForce.GTC,
//                "1", "1", null, null, null, null));

        // place dual position side order.
        // Switch between dual or both position side, call: com.binance.client.examples.swap.trade.ChangePositionSide
        System.out.println(spotRequestClient.postOrder("BTCUSDT", OrderSide.SELL, OrderType.LIMIT, TimeInForce.GTC,
                "1", "9000", null, null, null, null, NewOrderRespType.RESULT));
    }
}