package com.mzwise.unifyexchange.future.stream;

import com.binance.client.future.FutureRequestClient;
import com.binance.client.future.FutureRequestOptions;
import com.binance.client.future.SubscriptionClient;
import com.binance.client.future.model.user.OrderUpdate;
import com.mzwise.unifyexchange.beans.Order;
import com.mzwise.unifyexchange.common.Constants;
import com.mzwise.unifyexchange.common.StreamListener;
import com.mzwise.unifyexchange.util.SymbolUtil;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class BinanceStreamServiceFuture implements FutureIStreamService {

    public static SubscriptionClient client;

    public static Map<String, String> users = new HashMap<>();

    static {
        long t = 10;
        log.info("【Binance.future】future user-data websocket watch dog started, ping every {} minutes", t);
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        exec.scheduleAtFixedRate(() -> {
           // log.info("【Binance.future】future keep-user-data: {}", users);
            users.forEach((key, listenKey) -> {
                try {
                    String[] keys = key.split("---");
                    FutureRequestOptions options = new FutureRequestOptions();
                    FutureRequestClient requestClient = FutureRequestClient.create(keys[0], keys[1],
                            options);
                    log.info("【Binance.future】send ping: {}", keys[0]);
                    String s = requestClient.keepUserDataStream(key);
                    log.info("【Binance.future】 ping result: {}", s);
                } catch (Exception e) {
                    log.error("【Binance.future】 ping wrong: {}", e.getMessage());
                    e.printStackTrace();
                }
            });
        }, 5, t, TimeUnit.MINUTES);
        Runtime.getRuntime().addShutdownHook(new Thread(exec::shutdown));
    }

    public static void main(String[] args) {
        client = SubscriptionClient.create();
        client.subscribeUserDataEvent("VZEZS28CD8o3ad4oapePRAeNwUdsCibbU8VPDTGNHskBRMtYdDPzmYK7S50So6oN",
                ((event) -> {
            if (event==null) {
                return;
            }
            // 订单事件
            System.out.println(event);
        }), (e) ->
                log.error("BinanceStreamServiceFuture 币安-订阅消息出现严重错误 ，{}", e.getMessage()));
    }

    @Override
    public void init() {
        client = SubscriptionClient.create();
    }

    /**
     * 目前用户信息只保留了委托成交的处理
     * @param accessKey
     * @param secretkey
     */
    @Override
    public void subOrderUpdate(Integer apiAccessId,String id, String accessKey, String secretkey, String passphrase, StreamListener<Order.Response> futureStreamListener) {
        String listenKey;
        synchronized (users) {
            String key = accessKey + "---" + secretkey;
            // 如果用户已订阅，无需再次订阅
            if (users.containsKey(key)) {
                return;
            }
            FutureRequestOptions options = new FutureRequestOptions();
            FutureRequestClient futureRequestClient = FutureRequestClient.create(accessKey, secretkey,
                    options);
            listenKey = futureRequestClient.startUserDataStream();
            users.put(key, listenKey);
        }
        client.subscribeUserDataEvent(listenKey, ((event) -> {
            if (event==null) {
                return;
            }
            // 订单事件  返回的symbol 格式为 ： ETHUSDT
            //isReduceOnly=false  表示开仓
            log.info("BinanceStreamServiceFuture 接受到 web socket 推送： {}",event.toString());
            if ("ORDER_TRADE_UPDATE".equals(event.getEventType())) {
                OrderUpdate orderUpdate = event.getOrderUpdate();
                Order.Response response = new Order.Response().builder()
                        .newClientOrderId(orderUpdate.getClientOrderId())
                        .exchangeName(Constants.EXCHANGE_NAME.BINANCE)
                        .customId(id)
                        .status(Constants.RESPONSE_STATUS.OK)
                        .orderStatus(Constants.ORDER_RESPONSE_STATUS.valueOf(orderUpdate.getOrderStatus()))
                        .direction(Constants.TRADING_DIRECTION.valueOf(orderUpdate.getSide()))
                        .tid(orderUpdate.getOrderId().toString())
                        .origQty(orderUpdate.getOrigQty())
                        .executedQty(orderUpdate.getCumulativeFilledQty())
//							.executedAmount()
                        .avgPrice(orderUpdate.getAvgPrice())
                        .profit(orderUpdate.getProfit())
                        .symbol(SymbolUtil.toPlatform(orderUpdate.getSymbol()))
                        .openClose(orderUpdate.getIsReduceOnly()==Boolean.FALSE?"open":"close")
                        .apiAccessId(apiAccessId)
                        .commission(orderUpdate.getCommissionAmount()).build();
                if (response.getProfit()!=null && response.getProfit().compareTo(BigDecimal.ZERO)!=0)
                {
                    response.setOpenClose("close");
                }

                futureStreamListener.onReceive(response);
            }
        }), (e) ->
				log.error("币安-订阅消息出现严重错误，{}", e.getMessage()));
    }


}
