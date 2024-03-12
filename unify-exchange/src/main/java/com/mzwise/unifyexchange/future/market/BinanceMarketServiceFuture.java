package com.mzwise.unifyexchange.future.market;

import com.binance.client.future.FutureRequestClient;
import com.binance.client.future.FutureRequestOptions;
import com.binance.client.future.model.market.SymbolPrice;
import com.mzwise.unifyexchange.beans.Thumb;
import com.mzwise.unifyexchange.common.Constants;
import com.mzwise.unifyexchange.util.SymbolUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class BinanceMarketServiceFuture implements FutureIMarketService {
	@Override
	public Thumb.Response getPrice(Thumb thumb) {
		FutureRequestOptions options = new FutureRequestOptions();
		FutureRequestClient requestClient = FutureRequestClient.create(thumb.getAccessKey(), thumb.getSecretkey(),
				options);
		List<SymbolPrice> tickers = requestClient.getSymbolPriceTicker(SymbolUtil.asBinance(thumb.getSymbol()));
		if (tickers==null || tickers.size()!=1) {
			log.error("获取最新价格行情错误");
			return new Thumb.Response().builder().status(Constants.RESPONSE_STATUS.ERROR).build();
		}
		SymbolPrice symbolPrice = tickers.get(0);
		return new Thumb.Response().builder()
				.status(Constants.RESPONSE_STATUS.OK)
				.weightPrice(symbolPrice.getPrice())
				.close(symbolPrice.getPrice()).build();
	}
}
