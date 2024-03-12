package com.binance.client.examples.exchange.trade;

import com.binance.client.examples.exchange.constants.PrivateConfig;
import com.binance.client.spot.SpotRequestClient;
import com.binance.client.spot.SpotRequestOptions;

/**
 * @author : wangwanlu
 * @since : 2020/3/26, Thu
 **/
public class BatchPlaceOrders {
    public static void main(String[] args) {
        SpotRequestOptions options = new SpotRequestOptions();
        SpotRequestClient spotRequestClient = SpotRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);

        // place dual position side orders.
        // Switch between dual or both position side, call: com.binance.client.examples.swap.trade.ChangePositionSide
        System.out.println(spotRequestClient.postBatchOrders(
                "[{\"symbol\": \"BTCUSDT\",\"side\":\"BUY\",\"positionSide\":\"LONG\",\"type\":\"LIMIT\",\"newClientOrderId\":\"wanlu_dev_0324\",\"quantity\":\"1\",\"price\": \"8000\",\"timeInForce\":\"GTC\"},\n" +
                "{\"symbol\": \"BTCUSDT\",\"side\":\"BUY\",\"positionSide\":\"SHORT\",\"type\":\"LIMIT\",\"newClientOrderId\":\"wanlu_dev_0325\",\"quantity\":\"1\",\"price\": \"8000\",\"timeInForce\":\"GTC\"},\n" +
                "{\"symbol\": \"BTCUSDT\",\"side\":\"BUY\",\"type\":\"LIMIT\",\"newClientOrderId\":\"wanlu_dev_0320\",\"quantity\":\"1\",\"price\": \"8000\",\"timeInForce\":\"GTC\"}]"

        ));
    }
}
