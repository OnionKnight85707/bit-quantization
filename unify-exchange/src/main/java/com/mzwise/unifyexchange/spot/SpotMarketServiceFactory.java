package com.mzwise.unifyexchange.spot;

import com.mzwise.unifyexchange.beans.Thumb;
import com.mzwise.unifyexchange.common.Constants;
import com.mzwise.unifyexchange.spot.market.*;

public class SpotMarketServiceFactory {

	public static MarketServiceSPISpot getInstance(Constants.EXCHANGE_NAME exchange) {

		return new MarketServiceSPISpot(exchange);
	}

	public final static class MarketServiceSPISpot implements SpotIMarketService {

		private SpotIMarketService iSpotIMarketService;

		public MarketServiceSPISpot(Constants.EXCHANGE_NAME exchange) {

			if (Constants.EXCHANGE_NAME.HUOBI.equals(exchange)) {
				iSpotIMarketService = new HuobiMarketServiceSpot();
			} else if (Constants.EXCHANGE_NAME.COINEX.equals(exchange)) {
				iSpotIMarketService = new CoinexMarketServiceSpot();
			} else if (Constants.EXCHANGE_NAME.OKEX.equals(exchange)) {
				iSpotIMarketService = new OkexMarketServiceSpot();
			} else if (Constants.EXCHANGE_NAME.BINANCE.equals(exchange)) {
				iSpotIMarketService = new BinanceMarketServiceSpot();
			}
		}

		@Override
		public Thumb.Response get24Thumb(Thumb thumb) {
			return iSpotIMarketService.get24Thumb(thumb);
		}
	}
	
}
