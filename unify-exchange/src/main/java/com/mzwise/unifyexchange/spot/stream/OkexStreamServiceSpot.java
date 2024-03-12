package com.mzwise.unifyexchange.spot.stream;

import com.alibaba.fastjson.JSONObject;
import com.mzwise.unifyexchange.beans.Order;
import com.mzwise.unifyexchange.common.Constants;
import com.mzwise.unifyexchange.common.StreamListener;
import com.mzwise.unifyexchange.util.OkexUtil;
import com.okex.open.api.websocket.privates.PrivateWsClient;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wmf
 */
@Slf4j
public class OkexStreamServiceSpot implements SpotIStreamService {

	public static Map<String, PrivateWsClient> clients = new HashMap<>();

	@Override
	public void init() {

	}

	@Override
	public void subOrderUpdate(String id, String accessKey, String secretkey, String passphrase, StreamListener<Order.Response> streamListener) {
		PrivateWsClient client;
		synchronized(clients) {
			// 如果用户已订阅，无需再次订阅
			if (clients.containsKey(id)) {
				return;
			}
			client = new PrivateWsClient(accessKey, secretkey, passphrase);
			clients.put(id, client);
		}
		//添加订阅频道
		ArrayList<Map> channelList = new ArrayList<>();
		Map orderMap =new HashMap();
		orderMap.put("channel","orders");
		//"ANY"
		orderMap.put("instType","SPOT");
		channelList.add(orderMap);
		//调用订阅方法
		client.subscribe(channelList, message -> {
			if (message.containsKey("arg") && message.containsKey("data")) {
				JSONObject arg = message.getJSONObject("arg");
				if (!"orders".equals(arg.getString("channel"))) {
					return;
				}
				JSONObject result = OkexUtil.getSimpleResponse(message);
				// 不推送部分成交事件
				if (!"filled".equals(result.getString("state"))) {
					return;
				}
				BigDecimal accFillSz = new BigDecimal(result.getString("accFillSz"));
				BigDecimal avgPx = "".equals(result.getString("avgPx"))?null:new BigDecimal(result.getString("avgPx"));
				BigDecimal executedQty = BigDecimal.ZERO;
				if (avgPx != null) {
					executedQty = accFillSz.multiply(avgPx);
				}
				Order.Response response = new Order.Response().builder()
						.newClientOrderId(result.getString("clOrdId"))
						.exchangeName(Constants.EXCHANGE_NAME.OKEX)
						.customId(id)
						.status(Constants.RESPONSE_STATUS.OK)
						.orderStatus(Constants.ORDER_RESPONSE_STATUS.fromOkex(result.getString("state")))
						.direction(Constants.TRADING_DIRECTION.valueOf(result.getString("side").toUpperCase()))
						.tid(result.getString("ordId"))
						.origQty(new BigDecimal(result.getString("sz")))
						.executedQty(accFillSz)
						.executedAmount(executedQty)
						.commission(new BigDecimal(result.getString("fee")).negate())
						.avgPrice(avgPx)
						.build();
				System.out.println("订单委托成交数据");
				System.out.println(response);
				streamListener.onReceive(response);
			}
		});
	}
}
