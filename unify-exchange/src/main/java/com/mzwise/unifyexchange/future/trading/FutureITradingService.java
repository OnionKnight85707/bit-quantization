package com.mzwise.unifyexchange.future.trading;

import com.binance.client.future.model.trade.MyTrade;
import com.binance.client.future.model.trade.PositionRisk;
import com.mzwise.unifyexchange.beans.*;

import java.util.List;

public interface FutureITradingService {

	/**
	 * 获取账户基本信息
	 * @param accountInfo
	 * @return
	 */
	AccountInfo.Response getAccountInfo(AccountInfo accountInfo);

	/**
	 * 获取币种交易规则(时间会慢，需缓存)
	 * @param symbolInfo
	 * @return
	 */
	SymbolInfo.Response symbolInfo(SymbolInfo symbolInfo);

	/**
	 * 交易前确保交易模式, 并放回交易模式
	 * @param riskReq
	 * @return
	 */
	Risk.Response fixedRisk(Risk riskReq);

	/**
	 * 用户持仓概况(包含杠杆，逐仓等信息)
	 * @param risk
	 * @return
	 */
	Risk.Response getRisk(Risk risk);

    List<MyTrade> getHistoryTrade(HistoryTradeReq req);

    List<PositionRisk> getNotNullRisks(Risk risk);


	/**
	 * 调整杠杆
	 * @param lever
	 * @return
	 */
	Lever.Response changeLever(Lever lever);

	/**
	 * 变换逐全仓模式
	 * @param type
	 * @return
	 */
	Margin.Response changeMarginType(Margin type);

	/**
	 * usdt本位下单
	 * 市价开单 传amount(金额)
	 * 其它传数量qty
	 * @param order
	 * @return
	 */
	Order.Response postUsdtOrder(Order order);

	/**
	 * 订单详情
	 * @param order
	 * @return
	 */
	Order.Response detail(Order order);

	/**
	 * 撤销单
	 * @param cancel
	 * @return
	 */
	Cancel.Response cancel(Cancel cancel);

	List<Risk.Response> getAllRisk(Risk risk);

    default Order.Response cancelAllSwapOpenOrder(String symbol, String accessKey, String secretKey, String passphrase)
	{
		return null;
	}

    UnifyOrder getExchangeOrder(HistoryOrderReq req);
}
