package com.mzwise.unifyexchange.future;

import com.binance.client.future.model.trade.MyTrade;
import com.binance.client.future.model.trade.PositionRisk;
import com.mzwise.unifyexchange.beans.*;
import com.mzwise.unifyexchange.common.Constants;
import com.mzwise.unifyexchange.future.trading.*;

import java.util.List;

public class FutureTradingServiceFactory {

	public static TradingServiceSPI getInstance(Constants.EXCHANGE_NAME exchange) {
		
		return new FutureTradingServiceFactory.TradingServiceSPI(exchange);
	}
	
	public final static class TradingServiceSPI implements FutureITradingService {
		
		private FutureITradingService tradingService;
	
		public TradingServiceSPI(Constants.EXCHANGE_NAME exchange) {
			if (Constants.EXCHANGE_NAME.HUOBI.equals(exchange)) {
				tradingService = new HuobiTradingServiceFuture();
			} else if (Constants.EXCHANGE_NAME.COINEX.equals(exchange)) {
				tradingService = new CoinexTradingServiceFuture();
			} else if (Constants.EXCHANGE_NAME.OKEX.equals(exchange)) {
				tradingService = new OkexTradingServiceFuture();
			} else if (Constants.EXCHANGE_NAME.BINANCE.equals(exchange)) {
				tradingService = new BinanceTradingServiceFuture();
			}
		}


		@Override
		public AccountInfo.Response getAccountInfo(AccountInfo accountInfo) {
			return this.tradingService.getAccountInfo(accountInfo);
		}

		@Override
		public SymbolInfo.Response symbolInfo(SymbolInfo symbolInfo) {
			return this.tradingService.symbolInfo(symbolInfo);
		}

		@Override
		public Risk.Response fixedRisk(Risk tradeType) {
			return this.tradingService.fixedRisk(tradeType);
		}

		@Override
		public Risk.Response getRisk(Risk risk) {
			return this.tradingService.getRisk(risk);
		}

		@Override
		public List<MyTrade> getHistoryTrade(HistoryTradeReq req) {
			return this.tradingService.getHistoryTrade(req);
		}

		@Override
		public List<PositionRisk> getNotNullRisks(Risk risk) {
			return this.tradingService.getNotNullRisks(risk);
		}


		@Override
		public List<Risk.Response> getAllRisk(Risk risk) {
			return this.tradingService.getAllRisk(risk);
		}



		@Override
		public Lever.Response changeLever(Lever lever) {
			return this.tradingService.changeLever(lever);
		}

		@Override
		public Margin.Response changeMarginType(Margin type) {
			return this.tradingService.changeMarginType(type);
		}

		/**
		 * usdt本位下单
		 * @return
		 */
		@Override
		public Order.Response postUsdtOrder(Order order) {
			return this.tradingService.postUsdtOrder(order);
		}

		@Override
		public Order.Response detail(Order order) {
			return this.tradingService.detail(order);
		}

		/**
		 * 撤销订单
		 * @param cancel
		 * @return
		 */
		@Override
		public Cancel.Response cancel(Cancel cancel) {
			return this.tradingService.cancel(cancel);
		}



		public Order.Response cancelAllSwapOpenOrder(String symbol, String accessKey, String secretKey, String passphrase) {
			return this.tradingService.cancelAllSwapOpenOrder(symbol,accessKey,secretKey,passphrase);
		}

		@Override
		public UnifyOrder getExchangeOrder(HistoryOrderReq req) {
			return this.tradingService.getExchangeOrder(req);
		}
	}
}
