package com.mzwise.unifyexchange.spot.stream;

import com.mzwise.unifyexchange.beans.Order;
import com.mzwise.unifyexchange.common.StreamListener;

public class CoinexStreamServiceSpot implements SpotIStreamService {

	@Override
	public void init() {

	}

	@Override
	public void subOrderUpdate(String id, String accessKey, String secretkey, String passphrase, StreamListener<Order.Response> streamListener) {

	}
}
