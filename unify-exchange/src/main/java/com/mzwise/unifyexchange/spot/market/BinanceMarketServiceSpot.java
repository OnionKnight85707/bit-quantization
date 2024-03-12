package com.mzwise.unifyexchange.spot.market;

import com.binance.client.spot.SpotRequestClient;
import com.binance.client.spot.SpotRequestOptions;
import com.binance.client.spot.model.market.PriceChangeTicker;
import com.mzwise.unifyexchange.beans.Thumb;
import com.mzwise.unifyexchange.common.Constants;
import com.mzwise.unifyexchange.util.SymbolUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class BinanceMarketServiceSpot implements SpotIMarketService {

	@Override
	public Thumb.Response get24Thumb(Thumb thumb) {
		SpotRequestOptions options = new SpotRequestOptions();
		SpotRequestClient spotRequestClient = SpotRequestClient.create(thumb.getAccessKey(), thumb.getSecretkey(),
				options);
		List<PriceChangeTicker> tickers = spotRequestClient.get24hrTickerPriceChange(SymbolUtil.asBinance(thumb.getSymbol()));
		if (tickers==null || tickers.size()!=1) {
			log.error("获取24小时行情错误");
			return new Thumb.Response().builder().status(Constants.RESPONSE_STATUS.ERROR).build();
		}
		PriceChangeTicker priceChangeTicker = tickers.get(0);
		return new Thumb.Response().builder()
				.status(Constants.RESPONSE_STATUS.OK)
				.weightPrice(priceChangeTicker.getWeightedAvgPrice())
				.open(priceChangeTicker.getOpenPrice())
				.close(priceChangeTicker.getLastPrice())
				.high(priceChangeTicker.getHighPrice())
				.low(priceChangeTicker.getLowPrice()).build();
	}
}
