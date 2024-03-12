package com.mzwise.unifyexchange.future.stream;

import com.mzwise.unifyexchange.beans.Order;
import com.mzwise.unifyexchange.common.StreamListener;

public class CoinexStreamServiceFuture implements FutureIStreamService {

	@Override
	public void init() {

	}

	@Override
	public void subOrderUpdate(Integer apiAccessId,String id, String accessKey, String secretkey, String passphrase, StreamListener<Order.Response> streamListener) {

	}
}
