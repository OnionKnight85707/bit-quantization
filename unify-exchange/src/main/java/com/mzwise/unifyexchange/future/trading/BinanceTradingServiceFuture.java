package com.mzwise.unifyexchange.future.trading;

import com.binance.client.future.FutureRequestClient;
import com.binance.client.future.FutureRequestOptions;
import com.binance.client.future.exception.BinanceApiException;
import com.binance.client.future.model.ResponseResult;
import com.binance.client.future.model.enums.*;
import com.binance.client.future.model.market.ExchangeInfoEntry;
import com.binance.client.future.model.market.ExchangeInformation;
import com.binance.client.future.model.trade.*;
import com.mzwise.unifyexchange.beans.*;
import com.mzwise.unifyexchange.beans.Order;
import com.mzwise.unifyexchange.common.Constants;
import com.mzwise.unifyexchange.util.CommonUtil;
import com.mzwise.unifyexchange.util.SymbolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public final class BinanceTradingServiceFuture implements FutureITradingService {

	public static void main(String[] args) {
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setAccessKey("oUYX9hEV6d8fenjalCdIDqBgylzuRjBp5qQ6Idm57zCaTkTf2gsSgBUf65nJnCG7");
		accountInfo.setSecretkey("tE0epFaTm1WLN8JMzu5tzMJQuYugx6SZOOkA66KFCiTxFmIInO2IQuhjVwnuUCeN");
		BinanceTradingServiceFuture binanceTradingServiceFuture = new BinanceTradingServiceFuture();
		AccountInfo.Response rep = binanceTradingServiceFuture.getAccountInfo(accountInfo);
		System.out.println(rep);
	}

	@Override
	public AccountInfo.Response getAccountInfo(AccountInfo accountInfo) {
		try {
			FutureRequestOptions options = new FutureRequestOptions();
			FutureRequestClient requestClient = FutureRequestClient.create(accountInfo.getAccessKey(), accountInfo.getSecretkey(),
					options);
			AccountInformation baResponse = requestClient.getAccountInformation();
			AccountInfo.Response response = new AccountInfo.Response().builder()
					.status(Constants.RESPONSE_STATUS.OK)
					.isNormal(baResponse.getCanTrade())
					.assetMap(new HashMap<>())
					.build();
			List<Asset> assets = baResponse.getAssets();
			for (Asset asset : assets) {
				response.addAsset(asset.getAsset(), asset.getAvailableBalance(), BigDecimal.ZERO,asset.getWalletBalance());
			}
			List<Position> positions = baResponse.getPositions();
			for(Position pos: positions)
			{
				if (pos.getInitialMargin()!=null && pos.getInitialMargin().compareTo(BigDecimal.ZERO)>0)
				{
					UnifyPosition up=new UnifyPosition();
					BeanUtils.copyProperties(up,pos);
					response.addPosition(up);
				}
			}
			return response;
		} catch (Exception e) {
			return new AccountInfo.Response().builder()
					.status(Constants.RESPONSE_STATUS.ERROR)
					.errorMsg(e.getMessage())
					.build();
		}
	}

	@Override
	public SymbolInfo.Response symbolInfo(SymbolInfo symbolInfo) {
		SymbolInfo.Response response = new SymbolInfo.Response();
		try {
			FutureRequestOptions options = new FutureRequestOptions();
			FutureRequestClient futureRequestClient = FutureRequestClient.create(symbolInfo.getAccessKey(), symbolInfo.getSecretkey(),
					options);
			ExchangeInformation exchangeInformation = futureRequestClient.getExchangeInformation();
			List<ExchangeInfoEntry> symbols = exchangeInformation.getSymbols();
			Optional<ExchangeInfoEntry> first = symbols.stream().filter(v -> v.getSymbol().equals(SymbolUtil.asBinance(symbolInfo.getSymbol()))).findFirst();
			ExchangeInfoEntry exchangeInfo = first.get();
			List<List<Map<String, String>>> filters = exchangeInfo.getFilters();
			Optional<List<Map<String, String>>> lotSize = filters.stream().filter(
					f -> f.stream().anyMatch(
							l -> l.entrySet().stream().anyMatch(
									v -> v.getKey().equals("filterType") && v.getValue().equals("LOT_SIZE")
							)
					)
			).findFirst();
			List<Map<String, String>> lotSizeItems = lotSize.get();
			BigDecimal stepSize = new BigDecimal(lotSizeItems.stream().filter(l -> l.containsKey("stepSize")).findFirst().get().get("stepSize"));
			Integer assetScale = CommonUtil.scaleBySize(stepSize);
			Optional<List<Map<String, String>>> priceFilter = filters.stream().filter(
					f -> f.stream().anyMatch(
							l -> l.entrySet().stream().anyMatch(
									v -> v.getKey().equals("filterType") && v.getValue().equals("PRICE_FILTER")
							)
					)
			).findFirst();
			List<Map<String, String>> priceFilterItems = priceFilter.get();
			BigDecimal tickSize = new BigDecimal(priceFilterItems.stream().filter(l -> l.containsKey("tickSize")).findFirst().get().get("tickSize"));
			Integer quoteScale = CommonUtil.scaleBySize(tickSize);
			Optional<List<Map<String, String>>> minNotionalOp = filters.stream().filter(
					f -> f.stream().anyMatch(
							l -> l.entrySet().stream().anyMatch(
									v -> v.getKey().equals("filterType") && v.getValue().equals("MIN_NOTIONAL")
							)
					)
			).findFirst();
			List<Map<String, String>> minNotionalItems = minNotionalOp.get();
			BigDecimal minNotional = new BigDecimal(minNotionalItems.stream().filter(l -> l.containsKey("notional")).findFirst().get().get("notional"));
			response.setStatus(Constants.RESPONSE_STATUS.OK);
			response.setAssetScale(assetScale);
			response.setQuoteScale(quoteScale);
			response.setMinValue(minNotional);
			return response;
		} catch (Exception e) {
			response.setStatus(Constants.RESPONSE_STATUS.ERROR);
			response.setErrorMsg(e.getMessage());
			return response;
		}
	}

	@SuppressWarnings("AlibabaAvoidCommentBehindStatement")
	@Override
	public Risk.Response fixedRisk(Risk riskReq) {
		try {
			/** 确保双向持仓模式 **/
			List<PositionRisk> risks = getRisks(riskReq);
			Boolean isChange = false;
			// 修改为双向持仓模式
			Risk.Response risk;
			// 单向
			if (risks.size()==1) {
				PositionMode.Response rep = changePositionMode((PositionMode) new PositionMode(true).from(riskReq));
				if (!rep.getStatus().equals(Constants.RESPONSE_STATUS.OK)) {
					throw new Exception(rep.getErrorMsg());
				}
				isChange = true;
				log.info("修改持仓模式为双向模式");
				risk = getRisk(risks, Constants.TRADING_POSITIONSIDE.BOTH);// 先用着
			} else if (risks.size() == 2 ){ // 双向
				risk = getRisk(risks, riskReq.getPositionSide());
			} else {
				throw new Exception("持仓模式不明确");
			}
			/** 非逐仓模式要修改为逐仓模式 **/
			if (!risk.getMarginType().equals(riskReq.getMarginType())) {
				Margin margin = (Margin) new Margin(riskReq.getSymbol(), riskReq.getMarginType()).from(riskReq);
				Margin.Response rep = changeMarginType(margin);
				if (!rep.getStatus().equals(Constants.RESPONSE_STATUS.OK)) {
					throw new Exception(rep.getErrorMsg());
				}
				isChange = true;
				log.info("修改交易模式为逐仓模式");
			}
			/** 按参数修改杠杆 **/
			if (!risk.getLeverage().equals(riskReq.getLeverage())) {
				Lever lever = (Lever) new Lever(riskReq.getSymbol(), riskReq.getLeverage())
						.from(riskReq);
				Lever.Response rep = changeLever(lever);
				if (!rep.getStatus().equals(Constants.RESPONSE_STATUS.OK)) {
					throw new Exception(rep.getErrorMsg());
				}
				isChange = true;
				log.info("修改杠杆为： {}", riskReq.getLeverage());
			}
			// 重新获取risk
//			if (isChange) {
//				risk = getRisk(riskReq);
//			}
			return risk;
		} catch (Exception e) {
			return new Risk.Response().builder()
					.status(Constants.RESPONSE_STATUS.ERROR)
					.errorMsg(e.getMessage())
					.build();
		}
	}

	@Override
	public Risk.Response getRisk(Risk risk) {
		try {
			List<PositionRisk> risks = getRisks(risk);
			return getRisk(risks, risk.getPositionSide());
		} catch (Exception e) {
			return new Risk.Response().builder()
					.status(Constants.RESPONSE_STATUS.ERROR)
					.errorMsg(e.getMessage())
					.build();
		}
	}


	@Override
	public List<MyTrade> getHistoryTrade(HistoryTradeReq req) {
		FutureRequestOptions options = new FutureRequestOptions();
		FutureRequestClient requestClient = FutureRequestClient.create(req.getAccessKey(), req.getSecretkey(),
				options);
		return requestClient.getAccountTrades(SymbolUtil.asBinance(req.getSymbol()),req.getStartTime(),req.getEndTime(),req.getFromId(),req.getLimit());


	}

	/**
	 * 从risks 中筛选risk
	 * @param
	 */
	private Risk.Response getRisk(List<PositionRisk> risks, Constants.TRADING_POSITIONSIDE positionSide) {
		PositionRisk positionRisk = risks.stream().filter(v -> v.getPositionSide().equals(positionSide.getCode())).findFirst().get();
		return new Risk.Response().builder()
				.status(Constants.RESPONSE_STATUS.OK)
				.entryPrice(positionRisk.getEntryPrice())
				.isolatedMargin(positionRisk.getIsolatedMargin())
				.liquidationPrice(positionRisk.getLiquidationPrice())
				.leverage(positionRisk.getLeverage())
				.marginType(Constants.MARGIN_TYPE.valueOf(positionRisk.getMarginType().toUpperCase()))
				.price(positionRisk.getMarkPrice())
				.maxNotionalValue(BigDecimal.valueOf(positionRisk.getMaxNotionalValue()))
				.positionSide(Constants.TRADING_POSITIONSIDE.valueOf(positionRisk.getPositionSide()))
				.positionAmt(positionRisk.getPositionAmt())
				.unRealizedProfit(positionRisk.getUnrealizedProfit())
				.build();
	}

	/**
	 * 获得所有方向的risk
	 * @param risk
	 */
	private List<PositionRisk> getRisks(Risk risk) {
		FutureRequestOptions options = new FutureRequestOptions();
		FutureRequestClient requestClient = FutureRequestClient.create(risk.getAccessKey(), risk.getSecretkey(),
				options);
		return requestClient.getPositionRisk(SymbolUtil.asBinance(risk.getSymbol()));

	}

	@Override
	public List<PositionRisk> getNotNullRisks(Risk risk) {
		FutureRequestOptions options = new FutureRequestOptions();
		FutureRequestClient requestClient = FutureRequestClient.create(risk.getAccessKey(), risk.getSecretkey(),
				options);
		List<PositionRisk>  list= requestClient.getPositionRisk(SymbolUtil.asBinance(risk.getSymbol()));

		return list.stream().filter(a->a.getPositionAmt().compareTo(BigDecimal.ZERO)!=0).collect(Collectors.toList());

	}

	public PositionMode.Response changePositionMode(PositionMode positionMode) {
		try {
			FutureRequestOptions options = new FutureRequestOptions();
			FutureRequestClient futureRequestClient = FutureRequestClient.create(positionMode.getAccessKey(), positionMode.getSecretkey(),
					options);
			ResponseResult req = futureRequestClient.changePositionSide(true);
			if (req.getCode()!=200) {
				throw new Exception(req.getMsg());
			}
			return new PositionMode.Response().builder()
					.status(Constants.RESPONSE_STATUS.OK)
					.build();
		} catch (Exception e) {
			return new PositionMode.Response().builder()
					.status(Constants.RESPONSE_STATUS.ERROR)
					.errorMsg(e.getMessage())
					.build();
		}
	}

	@Override
	public Lever.Response changeLever(Lever lever) {
		try {
			FutureRequestOptions options = new FutureRequestOptions();
			FutureRequestClient requestClient = FutureRequestClient.create(lever.getAccessKey(), lever.getSecretkey(),
					options);
			Leverage leverage = requestClient.changeInitialLeverage(SymbolUtil.asBinance(lever.getSymbol()), lever.getLeverage().intValue());
			return new Lever.Response().builder()
					.status(Constants.RESPONSE_STATUS.OK)
					.leverage(leverage.getLeverage())
					.build();
		} catch (BinanceApiException e) {
			return new Lever.Response().builder()
					.status(Constants.RESPONSE_STATUS.ERROR)
					.errorMsg(e.getMessage())
					.build();
		}
	}

	@Override
	public Margin.Response changeMarginType(Margin type) {
		try {
			FutureRequestOptions options = new FutureRequestOptions();
			FutureRequestClient requestClient = FutureRequestClient.create(type.getAccessKey(), type.getSecretkey(),
					options);
			ResponseResult responseResult = requestClient.changeMarginType(SymbolUtil.asBinance(type.getSymbol()), type.getMarginType().getCode());
			// 4046代表不需要切换，这里标记成功
			if (responseResult.getCode()!=200 && responseResult.getCode()!=4046) {
				throw new Exception(responseResult.getMsg());
			}
			return new Margin.Response().builder()
					.status(Constants.RESPONSE_STATUS.OK)
					.build();
		} catch (Exception e) {
			return new Margin.Response().builder()
					.status(Constants.RESPONSE_STATUS.ERROR)
					.errorMsg(e.getMessage())
					.build();
		}
	}

	@Override
	public Order.Response postUsdtOrder(Order order) {
		try {
			/** 开始交易 **/
			FutureRequestOptions options = new FutureRequestOptions();
			FutureRequestClient requestClient = FutureRequestClient.create(order.getAccessKey(), order.getSecretkey(),
					options);
			TimeInForce timeInForce = null;
			// 只有限价需要提交
			if (order.getType().equals(Constants.ORDER_TYPE.LIMIT)) {
				timeInForce = TimeInForce.GTC;
			}
			com.binance.client.future.model.trade.Order baResponse = requestClient.postOrder(
					SymbolUtil.asBinance(order.getSymbol()),
					OrderSide.valueOf(order.getDirection().toString()),
					PositionSide.valueOf(order.getPositionside().getCode()),
					OrderType.valueOf(order.getType().toString()),
					timeInForce,
					order.getQty(),
					order.getPrice(),
					null,
					order.getClientId(),
					null,
					null,
					NewOrderRespType.RESULT
			);
			return new Order.Response().builder()
					.exchangeName(Constants.EXCHANGE_NAME.BINANCE)
					.status(Constants.RESPONSE_STATUS.OK)
					.orderStatus(Constants.ORDER_RESPONSE_STATUS.valueOf(baResponse.getStatus()))
					.direction(Constants.TRADING_DIRECTION.valueOf(baResponse.getSide()))
					.tid(baResponse.getOrderId().toString())
					.origQty(baResponse.getOrigQty())
					.executedQty(baResponse.getExecutedQty())
					// 解决币安不返回保证金问题, 保证金保留两位即可
					.executedAmount(baResponse.getCumQuote().divide(BigDecimal.valueOf(order.getLeverage()), 2, RoundingMode.HALF_UP))
					.avgPrice(baResponse.getAvgPrice())
					 // todo 不计入手续费
					.commission(BigDecimal.ZERO)
					.build();
		} catch (Exception e) {
			Order.Response response = new Order.Response();
			response.setExchangeName(Constants.EXCHANGE_NAME.BINANCE);
			response.setStatus(Constants.RESPONSE_STATUS.ERROR);
			response.setErrorMsg(e.getMessage());
			return response;
		}
	}

	@Override
	public Order.Response detail(Order order) {
		try {
			/** 开始交易 **/
			FutureRequestOptions options = new FutureRequestOptions();
			FutureRequestClient requestClient = FutureRequestClient.create(order.getAccessKey(), order.getSecretkey(),
					options);
			com.binance.client.future.model.trade.Order baResponse = requestClient.getOrder(order.getSymbol(), null, order.getClientId());
			return new Order.Response().builder()
					.exchangeName(Constants.EXCHANGE_NAME.BINANCE)
					.status(Constants.RESPONSE_STATUS.OK)
					.orderStatus(Constants.ORDER_RESPONSE_STATUS.valueOf(baResponse.getStatus()))
					.direction(Constants.TRADING_DIRECTION.valueOf(baResponse.getSide()))
					.tid(baResponse.getOrderId().toString())
					.origQty(baResponse.getOrigQty())
					.executedQty(baResponse.getExecutedQty())
					// 解决币安不返回保证金问题, 保证金保留两位即可
					.executedAmount(baResponse.getCumQuote().divide(BigDecimal.valueOf(order.getLeverage()), 2, RoundingMode.HALF_UP))
					.avgPrice(baResponse.getAvgPrice())
					// todo 不计入手续费
					.commission(BigDecimal.ZERO)
					.build();
		} catch (Exception e) {
			Order.Response response = new Order.Response();
			response.setExchangeName(Constants.EXCHANGE_NAME.BINANCE);
			response.setStatus(Constants.RESPONSE_STATUS.ERROR);
			response.setErrorMsg(e.getMessage());
			return response;
		}
	}

	/**
	 * 切换至逐仓模式
	 * @param order
	 * @throws Exception
	 */
	private void switchToIsolated(Order order) throws Exception {
		Margin margin = new Margin(SymbolUtil.asBinance(order.getSymbol()), Constants.MARGIN_TYPE.ISOLATED);
		margin.setAccessKey(order.getAccessKey());
		margin.setSecretkey(order.getSecretkey());
		Margin.Response mtr = changeMarginType(margin);
		if (mtr.getStatus().equals(Constants.RESPONSE_STATUS.ERROR)) {
			throw new Exception("make sure to change margin type to \"isolated\"");
		}
	}

	@Override
	public Cancel.Response cancel(Cancel cancel) {
		try {
			FutureRequestOptions options = new FutureRequestOptions();
			FutureRequestClient requestClient = FutureRequestClient.create(cancel.getAccessKey(), cancel.getSecretkey(),
					options);
			com.binance.client.future.model.trade.Order baResponse = requestClient.cancelOrder(SymbolUtil.asBinance(cancel.getSymbol()), null, cancel.getClientId());
			Cancel.Response response = new Cancel.Response().builder()
					.status(Constants.RESPONSE_STATUS.OK)
					.tid(baResponse.getOrderId().toString()).build();
			return response;
		} catch (com.binance.client.future.exception.BinanceApiException e) {
			Cancel.Response response = new Cancel.Response();
			response.setStatus(Constants.RESPONSE_STATUS.ERROR);
			response.setErrorMsg(e.getMessage());
			return response;
		}
	}

	@Override
	public List<Risk.Response> getAllRisk(Risk risk) {

		List<Risk.Response> ret=new ArrayList<>();
		List<PositionRisk> positionList=getRisks(risk);
		for(PositionRisk positionRisk: positionList)
		{

				Risk.Response rep = new Risk.Response().builder()
						.status(Constants.RESPONSE_STATUS.OK)
						.entryPrice(positionRisk.getEntryPrice())
						.isolatedMargin(positionRisk.getIsolatedMargin())
						.liquidationPrice(positionRisk.getLiquidationPrice())
						.leverage(positionRisk.getLeverage())
						.marginType(Constants.MARGIN_TYPE.valueOf(positionRisk.getMarginType().toUpperCase()))
						.price(positionRisk.getMarkPrice())
						.maxNotionalValue(BigDecimal.valueOf(positionRisk.getMaxNotionalValue()))
						.positionSide(Constants.TRADING_POSITIONSIDE.valueOf(positionRisk.getPositionSide()))
						.positionAmt(positionRisk.getPositionAmt())
						.unRealizedProfit(positionRisk.getUnrealizedProfit())
						.build();
				ret.add(rep);


		}
		return ret;
	}

	@Override
	public Order.Response cancelAllSwapOpenOrder(String symbol, String accessKey, String secretKey, String passphrase) {
		FutureRequestOptions options = new FutureRequestOptions();
		FutureRequestClient requestClient = FutureRequestClient.create(accessKey, secretKey, options);
		ResponseResult result=requestClient.cancelAllOpenOrder(SymbolUtil.asBinance(symbol));

		return  new Order.Response(""+result.getCode(),result.getMsg());

	}

	@Override
	public UnifyOrder getExchangeOrder(HistoryOrderReq req ) {
		FutureRequestOptions options = new FutureRequestOptions();
		FutureRequestClient requestClient = FutureRequestClient.create(req.getAccessKey(), req.getSecretkey(), options);
		com.binance.client.future.model.trade.Order result=requestClient.getOrder(SymbolUtil.asBinance(req.getSymbol()),req.getOrderId(),req.getClientOrderId());

		UnifyOrder unifyOrder=new UnifyOrder();
		try {
			BeanUtils.copyProperties(unifyOrder,result);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return  unifyOrder;
	}
}
