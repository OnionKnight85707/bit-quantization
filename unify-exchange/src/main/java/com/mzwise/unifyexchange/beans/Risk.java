package com.mzwise.unifyexchange.beans;

import com.mzwise.unifyexchange.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("ALL")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Risk extends Common {

	public Risk(String symbol, Constants.TRADING_POSITIONSIDE positionSide) {
		this.symbol = symbol;
		this.positionSide = positionSide;
	}

	/**
	 * 交易对
	 */
	private String symbol;
	/**
	 * 持仓方向
	 */
	private Constants.TRADING_POSITIONSIDE positionSide;
	/**
	 * 杠杆
	 */
	private BigDecimal leverage;
//	/**
//	 * 持仓方向
//	 */
//	private Boolean dualSidePosition;
	/**
	 * 全仓/逐仓
	 */
	private Constants.MARGIN_TYPE marginType;

	/**
	 * 使用联合保证金
	 */
	private Boolean multiAssetsMargin;

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response implements Serializable {

		private Constants.RESPONSE_STATUS status;

		private String errorCode;

		private String errorMsg;

		private String symbol;

		/**
		 * 逐仓模式或全仓模式
		 */
		private Constants.MARGIN_TYPE marginType;

		/**
		 * 开仓均价
		 */
		private BigDecimal entryPrice;

		/**
		 * 逐仓保证金
		 */
		private String isolatedMargin;

		/**
		 * 杠杆
		 */
		private BigDecimal leverage;

		/**
		 * 参考强平价格
		 */
		private BigDecimal liquidationPrice;

		/**
		 * 当前标记价格
		 */
		private BigDecimal price;

		/**
		 * 当前杠杆倍数允许的名义价值上限
		 */
		private BigDecimal maxNotionalValue;

		/**
		 * 头寸数量，符号代表多空方向, 正数为多，负数为空
		 */
		private BigDecimal positionAmt;

		/**
		 * 持仓未实现盈亏
		 */
		private BigDecimal unRealizedProfit;

		/**
		 * 持仓方向
		 */
		private Constants.TRADING_POSITIONSIDE positionSide;

//		entryPrice": "0.00000", // 开仓均价
//				"marginType": "isolated", // 逐仓模式或全仓模式
//				"isAutoAddMargin": "false",
//				"isolatedMargin": "0.00000000", // 逐仓保证金
//				"leverage": "10", // 当前杠杆倍数
//				"liquidationPrice": "0", // 参考强平价格
//				"markPrice": "6679.50671178",   // 当前标记价格
//				"maxNotionalValue": "20000000", // 当前杠杆倍数允许的名义价值上限
//				"positionAmt": "0.000", // 头寸数量，符号代表多空方向, 正数为多，负数为空
//				"symbol": "BTCUSDT", // 交易对
//				"unRealizedProfit": "0.00000000", // 持仓未实现盈亏
//				"positionSide": "BOTH", // 持仓方向
	}
}
