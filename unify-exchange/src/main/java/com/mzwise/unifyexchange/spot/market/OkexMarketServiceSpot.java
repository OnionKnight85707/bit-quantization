package com.mzwise.unifyexchange.spot.market;

import com.alibaba.fastjson.JSONObject;
import com.mzwise.unifyexchange.beans.Common;
import com.mzwise.unifyexchange.beans.Thumb;
import com.mzwise.unifyexchange.common.Constants;
import com.mzwise.unifyexchange.util.OkexUtil;
import com.mzwise.unifyexchange.util.SymbolUtil;
import com.okex.open.api.config.APIConfiguration;
import com.okex.open.api.service.marketData.impl.MarketDataAPIServiceImpl;

import java.math.BigDecimal;

public class OkexMarketServiceSpot implements SpotIMarketService {

	@Override
	public Thumb.Response get24Thumb(Thumb thumb) {
		MarketDataAPIServiceImpl marketService = getMarketService(thumb);
		JSONObject body = marketService.getTicker(SymbolUtil.asOkex(thumb.getSymbol()));
		JSONObject result = OkexUtil.getSimpleResponse(body);
		return new Thumb.Response().builder()
				.status(Constants.RESPONSE_STATUS.OK)
				.weightPrice(new BigDecimal(result.getString("last")))
				.open(new BigDecimal(result.getString("open24h")))
				.close(new BigDecimal(result.getString("last")))
				.high(new BigDecimal(result.getString("high24h")))
				.low(new BigDecimal(result.getString("low24h"))).build();
	}

	private MarketDataAPIServiceImpl getMarketService(Common setting) {
		APIConfiguration config = new APIConfiguration();
		config.setApiKey("");
		config.setSecretKey("");
		config.setPassphrase("");
		return new MarketDataAPIServiceImpl(config);
	}
}
