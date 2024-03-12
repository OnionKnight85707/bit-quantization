package com.mzwise.unifyexchange.future.stream;

import com.huobi.client.TradeClient;
import com.huobi.client.req.trade.SubOrderUpdateV2Request;
import com.huobi.constant.HuobiOptions;
import com.huobi.model.trade.OrderUpdateV2;
import com.mzwise.unifyexchange.beans.Order;
import com.mzwise.unifyexchange.common.StreamListener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HuobiStreamServiceFuture implements FutureIStreamService {

	public static Map<String, TradeClient> clients = new ConcurrentHashMap<>();

	@Override
	public void init() {

	}

	@Override
	public void subOrderUpdate(Integer apiAccessId,String id, String accessKey, String secretkey, String passphrase, StreamListener<Order.Response> streamListener) {

	}
}
