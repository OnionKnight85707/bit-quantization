package com.mzwise.unifyexchange.future;

import com.mzwise.unifyexchange.beans.Thumb;
import com.mzwise.unifyexchange.common.Constants;
import com.mzwise.unifyexchange.future.market.*;

public class FutureMarketServiceFactory {

	public static MarketServiceSPIFuture getInstance(Constants.EXCHANGE_NAME exchange) {

		return new MarketServiceSPIFuture(exchange);
	}

	public final static class MarketServiceSPIFuture implements FutureIMarketService {

		private FutureIMarketService futureIMarketService;

		public MarketServiceSPIFuture(Constants.EXCHANGE_NAME exchange) {

			if (Constants.EXCHANGE_NAME.HUOBI.equals(exchange)) {
				futureIMarketService = new HuobiMarketServiceFuture();
			} else if (Constants.EXCHANGE_NAME.COINEX.equals(exchange)) {
				futureIMarketService = new CoinexMarketServiceFuture();
			} else if (Constants.EXCHANGE_NAME.OKEX.equals(exchange)) {
				futureIMarketService = new OkexMarketServiceFuture();
			} else if (Constants.EXCHANGE_NAME.BINANCE.equals(exchange)) {
				futureIMarketService = new BinanceMarketServiceFuture();
			}
		}

		@Override
		public Thumb.Response getPrice(Thumb thumb) {
			return futureIMarketService.getPrice(thumb);
		}
	}
	
}
