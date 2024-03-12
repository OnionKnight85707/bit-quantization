package com.mzwise.unifyexchange.spot;

import com.mzwise.unifyexchange.beans.*;
import com.mzwise.unifyexchange.common.Constants;
import com.mzwise.unifyexchange.spot.trading.*;

public class SpotTradingServiceFactory {

	public static TradingServiceSPI getInstance(Constants.EXCHANGE_NAME exchange) {
		
		return new TradingServiceSPI(exchange);
	}

	public final static class TradingServiceSPI implements SpotITradingService {

		private SpotITradingService tradingService;

		public TradingServiceSPI(Constants.EXCHANGE_NAME exchange) {
			if (Constants.EXCHANGE_NAME.HUOBI.equals(exchange)) {
				tradingService = new HuobiTradingServiceSpot();
			} else if (Constants.EXCHANGE_NAME.COINEX.equals(exchange)) {
				tradingService = new CoinexTradingServiceSpot();
			} else if (Constants.EXCHANGE_NAME.OKEX.equals(exchange)) {
				tradingService = new OkexTradingServiceSpot();
			} else if (Constants.EXCHANGE_NAME.BINANCE.equals(exchange)) {
				tradingService = new BinanceTradingServiceSpot();
			}
		}


		
		/**
		 * 测试api是否联通
		 * @param access
		 * @return
		 */
		@Override
		public Access.Response access(Access access) {
			Access.Response response = tradingService.access(access);
			return response;
		}

		@Override
		public AccountInfo.Response getAccountInfo(AccountInfo accountInfo) {
			AccountInfo.Response response = tradingService.getAccountInfo(accountInfo);
			return response;
		}

		@Override
		public SymbolInfo.Response symbolInfo(SymbolInfo symbolInfo) {
			SymbolInfo.Response response = tradingService.symbolInfo(symbolInfo);
			return response;
		}

		@Override
		public FeeInfo.Response feeInfo(FeeInfo feeInfo) {
			return tradingService.feeInfo(feeInfo);
		}

		/**
		 * 下单
		 * @return
		 */
		@Override
		public Order.Response postOrder(Order order) {
			return tradingService.postOrder(order);
		}

		@Override
		public Order.Response detail(Order order) {
			return tradingService.detail(order);
		}

		/**
		 * 撤销下单
		 * @param cancel
		 * @return
		 */
		@Override
		public Cancel.Response cancel(Cancel cancel) {
			return tradingService.cancel(cancel);
		}
	}
}
