package com.mzwise.unifyexchange.spot.market;

import com.mzwise.unifyexchange.beans.Thumb;

public interface SpotIMarketService {

	/**
	 * 获取币种24小时行情
	 * @param thumb
	 * @return
	 */
	Thumb.Response get24Thumb(Thumb thumb);

}
