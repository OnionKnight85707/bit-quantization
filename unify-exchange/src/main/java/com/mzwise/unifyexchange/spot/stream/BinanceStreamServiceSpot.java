package com.mzwise.unifyexchange.spot.stream;

import com.binance.client.spot.SpotRequestClient;
import com.binance.client.spot.SpotRequestOptions;
import com.binance.client.spot.SubscriptionClient;
import com.binance.client.spot.SubscriptionOptions;
import com.binance.client.spot.model.user.OrderUpdate;
import com.mzwise.unifyexchange.beans.Order;
import com.mzwise.unifyexchange.common.Constants;
import com.mzwise.unifyexchange.common.StreamListener;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public class BinanceStreamServiceSpot implements SpotIStreamService {

	public static SubscriptionClient client;

	public static Map<String, String> users = new HashMap<>();

	static {
		long t = 30;
		log.info("【Binance.spot】spot user-data websocket watch dog started, ping every {} minutes", t);
		ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
		exec.scheduleAtFixedRate(() -> {
			log.info("【Binance.spot】spot keep-user-data: {}", users);
			users.forEach((key, listenKey) -> {
				try {
					String[] keys = key.split("---");
					SpotRequestOptions options = new SpotRequestOptions();
					SpotRequestClient spotRequestClient = SpotRequestClient.create(keys[0], keys[1],
							options);
					log.info("【Binance.spot】send ping: {}", listenKey);
					String s = spotRequestClient.keepUserDataStream(listenKey);
					log.info("【Binance.spot】 ping result: {}", s);
				} catch (Exception e) {
					log.error("【Binance.spot】 ping wrong: {}", e.getMessage());
					e.printStackTrace();
				}
			});
		}, t, t, TimeUnit.MINUTES);
		Runtime.getRuntime().addShutdownHook(new Thread(exec::shutdown));
	}

	@Override
	public void init() {
		SubscriptionOptions options = new SubscriptionOptions();
		client = SubscriptionClient.create("", "", options);
	}

	public static void main(String[] args) {
		SubscriptionOptions options = new SubscriptionOptions();
		client = SubscriptionClient.create("", "", options);
		BinanceStreamServiceSpot service = new BinanceStreamServiceSpot();
		service.subOrderUpdate(
				"74",
				"oUYX9hEV6d8fenjalCdIDqBgylzuRjBp5qQ6Idm57zCaTkTf2gsSgBUf65nJnCG7",
				"tE0epFaTm1WLN8JMzu5tzMJQuYugx6SZOOkA66KFCiTxFmIInO2IQuhjVwnuUCeN",
				"",
				(response) -> {
					System.out.println(response);
				}
		);
	}

	/**
	 * 目前用户信息只保留了委托成交的处理
	 * @param accessKey
	 * @param secretkey
	 */
	@Override
	public void subOrderUpdate(String id, String accessKey, String secretkey, String passphrase, StreamListener<Order.Response> streamListener) {
		String listenKey;
		synchronized(users) {
			String key = accessKey + "---" + secretkey;
			// 如果用户已订阅，无需再次订阅
			if (users.containsKey(key)) {
				return;
			}
			SpotRequestOptions options = new SpotRequestOptions();
			SpotRequestClient spotRequestClient = SpotRequestClient.create(accessKey, "",
					options);
			listenKey = spotRequestClient.startUserDataStream();
			users.put(key, listenKey);
		}
		client.subscribeUserDataEvent(listenKey, ((event) -> {
			if (event==null) {
				return;
			}
			// 订单事件
			if ("executionReport".equals(event.getEventType())) {
				OrderUpdate orderUpdate = event.getOrderUpdate();
				// 委托成交事件
				if (Constants.ORDER_RESPONSE_STATUS.PARTIALLY_FILLED.getCode().equals(orderUpdate.getOrderStatus()) || Constants.ORDER_RESPONSE_STATUS.FILLED.getCode().equals(orderUpdate.getOrderStatus())) {
					// todo 价格存储8位-USDT
					BigDecimal avgPrice = orderUpdate.getCumulativeFilledQuoteQty().divide(orderUpdate.getCumulativeFilledQty(), 8, RoundingMode.HALF_UP);
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
							.executedAmount(orderUpdate.getCumulativeFilledQuoteQty())
							.avgPrice(avgPrice)
							.commission(orderUpdate.getCommissionAmount()).build();
					streamListener.onReceive(response);
				}
			}
		}), (e)->{
		});
	}
}
