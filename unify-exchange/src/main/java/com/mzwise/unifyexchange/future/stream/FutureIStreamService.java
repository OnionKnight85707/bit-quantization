package com.mzwise.unifyexchange.future.stream;

import com.mzwise.unifyexchange.beans.Order;
import com.mzwise.unifyexchange.common.StreamListener;

public interface FutureIStreamService {

	/**
	 * 初始化
	 * @return
	 */
	void init();

	/**
	 * 订阅订单事件
	 * @param id (自定义id， 如数据库的member)
	 * @param accessKey
	 * @param secretkey
	 * @param passphrase
	 * @param streamListener
	 */
	void subOrderUpdate(Integer apiAccessId,String id, String accessKey, String secretkey, String passphrase, StreamListener<Order.Response> streamListener);
}
