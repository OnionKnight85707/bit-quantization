package com.mzwise.unifyexchange.spot.trading;

import com.mzwise.unifyexchange.beans.*;

public interface SpotITradingService {

	/**
	 * 测试key是否联通
	 * @param access
	 * @return
	 */
	Access.Response access(Access access);

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
	 * 获取币种交易手续费
	 * @param feeInfo
	 * @return
	 */
	FeeInfo.Response feeInfo(FeeInfo feeInfo);

	/**
	 * 现货下单
	 * 市价开单 传amount(金额)
	 * 其它传qty(数量)
	 * @param order
	 * @return
	 */
	Order.Response postOrder(Order order);

	/**
	 * 订单详情
	 * @param order
	 * @return
	 */
	Order.Response detail(Order order);

	/**
	 * 现货撤销单
	 * @param cancel
	 * @return
	 */
	Cancel.Response cancel(Cancel cancel);

}
