package com.mzwise.unifyexchange.spot.market;

import com.huobi.client.MarketClient;
import com.huobi.client.req.market.MarketDetailMergedRequest;
import com.huobi.constant.HuobiOptions;
import com.huobi.model.market.MarketDetailMerged;
import com.mzwise.unifyexchange.beans.Thumb;
import com.mzwise.unifyexchange.common.Constants;
import com.mzwise.unifyexchange.util.SymbolUtil;

public class HuobiMarketServiceSpot implements SpotIMarketService {


	@Override
	public Thumb.Response get24Thumb(Thumb thumb) {
		MarketClient marketClient = MarketClient.create(new HuobiOptions());

		MarketDetailMerged marketDetailMerged = marketClient.getMarketDetailMerged(MarketDetailMergedRequest.builder().symbol(SymbolUtil.asHuobi(thumb.getSymbol())).build());
		return new Thumb.Response().builder()
				.status(Constants.RESPONSE_STATUS.OK)
				.weightPrice(marketDetailMerged.getClose())
				.open(marketDetailMerged.getOpen())
				.close(marketDetailMerged.getClose())
				.high(marketDetailMerged.getHigh())
				.low(marketDetailMerged.getLow()).build();
	}
}
