package com.mzwise.unifyexchange.spot.stream;

import com.huobi.client.TradeClient;
import com.huobi.client.req.trade.SubOrderUpdateV2Request;
import com.huobi.constant.HuobiOptions;
import com.huobi.model.trade.OrderUpdateV2;
import com.mzwise.unifyexchange.beans.Order;
import com.mzwise.unifyexchange.common.Constants;
import com.mzwise.unifyexchange.common.StreamListener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HuobiStreamServiceSpot implements SpotIStreamService {

	public static Map<String, TradeClient> clients = new ConcurrentHashMap<>();

	@Override
	public void init() {

	}

	/**
	 * todo 目前用户信息只保留了委托成交的处理，非常浪费
	 * @param id (自定义id， 如数据库的member)
	 * @param accessKey
	 * @param secretkey
	 * @param streamListener
	 */
	@Override
	public void subOrderUpdate(String id, String accessKey, String secretkey, String passphrase, StreamListener<Order.Response> streamListener) {
		TradeClient tradeService;
		synchronized(clients) {
			// 如果用户已订阅，无需再次订阅
			if (clients.containsKey(id)) {
				return;
			}
			tradeService = TradeClient.create(HuobiOptions.builder()
					.apiKey(accessKey)
					.secretKey(secretkey)
					.build());
			clients.put(id, tradeService);
		}

		tradeService.subOrderUpdateV2(SubOrderUpdateV2Request.builder().symbols("*").build(), orderUpdateV2Event -> {
			OrderUpdateV2 orderUpdate = orderUpdateV2Event.getOrderUpdate();
			if ("trade".equals(orderUpdate.getEventType())) {
				String[] types = orderUpdate.getType().split("-");
				String direction = types[0].toUpperCase();
//				// 平均价无推送
//                BigDecimal avgPrice = orderUpdate.getTradePrice();
//				Order.Response response = new Order.Response().builder()
//						.newClientOrderId(orderUpdate.getClientOrderId())
//						.exchangeName(Constants.EXCHANGE_NAME.HUOBI)
//						.customId(id)
//						.status(Constants.RESPONSE_STATUS.OK)
//						.orderStatus(Constants.ORDER_RESPONSE_STATUS.fromHuobi(orderUpdate.getOrderStatus()))
//						.direction(Constants.TRADING_DIRECTION.valueOf(direction))
//						.tid(orderUpdate.getOrderId().toString())
//						.origQty(orderUpdate.getOrderSize())
//						.executedQty(orderUpdate.getType().equals("buy-market")?null:orderUpdate.getExecAmt())
//						.cummulativeQuoteQty(orderUpdate.getType().equals("buy-market")?orderUpdate.getExecAmt():null)
//						.avgPrice(avgPrice)
//						.commission(BigDecimal.ZERO).build(); // 手续费无推送
				// 由于推送数据无法获取手续费，重新获取
				// 获取订单
				com.huobi.model.trade.Order hbOrder = tradeService.getOrder(orderUpdate.getOrderId());
				// 火币不推送部分成交事件
				if (!hbOrder.getState().equals("filled")) {
					return;
				}
				// todo 价格存储8位-USDT
				BigDecimal avgPrice = null;
				if (hbOrder.getFilledAmount().compareTo(BigDecimal.ZERO)>0) {
					avgPrice = hbOrder.getFilledCashAmount().divide(hbOrder.getFilledAmount(), 8, RoundingMode.HALF_UP);
				}
				Order.Response response = new Order.Response().builder()
						.newClientOrderId(orderUpdate.getClientOrderId())
						.exchangeName(Constants.EXCHANGE_NAME.HUOBI)
						.customId(id)
						.status(Constants.RESPONSE_STATUS.OK)
						.orderStatus(Constants.ORDER_RESPONSE_STATUS.fromHuobi(hbOrder.getState()))
						.direction(Constants.TRADING_DIRECTION.valueOf(direction))
						.tid(hbOrder.getId().toString())
						.origQty(hbOrder.getAmount())
						.executedQty(hbOrder.getFilledAmount())
						.executedAmount(hbOrder.getFilledCashAmount())
						.commission(hbOrder.getFilledFees())
						.avgPrice(avgPrice)
						.build();
				streamListener.onReceive(response);
			}

		});
	}
}
