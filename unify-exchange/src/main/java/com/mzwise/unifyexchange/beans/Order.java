package com.mzwise.unifyexchange.beans;

import com.mzwise.unifyexchange.common.Constants;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("ALL")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order extends Common {

	private static final long serialVersionUID = -4205844875425589379L;
	// 订单ID 查询使用
	private String id;
	// 币种
	private String symbol;
	// 数量
	private String qty;
	// 金额
	private String amount;
	// 价格
	private String price;
	// 杠杆(合约专用，为计算保证金使用 todo 解决币安不返回保证金和杠杆问题)
	private Integer leverage;
	// 买卖方向
	private Constants.TRADING_DIRECTION direction;
	// 持仓方向
	private Constants.TRADING_POSITIONSIDE positionside;
	// 方式
	private Constants.ORDER_TYPE type;
//	/**
//	 * 止盈止损选项
//	 */
//	private String timeInForce;

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	@ToString
	public static class Response implements Serializable {

		private static final long serialVersionUID = 7657654382832287880L;
		private Constants.RESPONSE_STATUS status;
		private String errorCode;
		private String errorMsg;

		private String symbol;

		/**
		 * 平台
		 */
		private Constants.EXCHANGE_NAME exchangeName;

		/**
		 * 状态
		 */
		private Constants.ORDER_RESPONSE_STATUS orderStatus;

		/**
		 * 方向
		 */
		private Constants.TRADING_DIRECTION direction;

		/**
		 * 成交量
		 */
		private BigDecimal executedQty;

		/**
		 * 原始交易量
		 */
		private BigDecimal origQty;

		/**
		 * 手续费
		 */
		private BigDecimal commission;

		/**
		 * 累计金额
		 */
		private BigDecimal executedAmount;

		/**
		 * 开仓均价
		 */
		private BigDecimal avgPrice;

		/**
		 * 收益(合约收益)
		 */
		private BigDecimal profit;

		// 自定义id(非第三方返回值)
		private String customId;

		// 客户自定义的唯一订单ID
		private String newClientOrderId;

		// open or close
		private String openClose;

		/**
		 * 订单号
		 */
		private String tid;


		/**
		 * 平台用户的api id
		 */
		private Integer apiAccessId;

		public Response(String code, String msg) {
			this.errorCode=code;
			this.errorMsg=msg;
		}

		public static  Response fail(String code, String msg)
		{
			Response response= new Response(code,msg);
			response.setStatus(Constants.RESPONSE_STATUS.ERROR);
			return  response;
		}

	}
}
