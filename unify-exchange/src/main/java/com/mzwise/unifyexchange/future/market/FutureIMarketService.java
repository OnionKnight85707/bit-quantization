package com.mzwise.unifyexchange.future.market;

import com.mzwise.unifyexchange.beans.Thumb;

public interface FutureIMarketService {

	/**
	 * 获取最新价格
	 * @param thumb
	 * @return
	 */
	Thumb.Response getPrice(Thumb thumb);

}
