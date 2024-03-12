package com.mzwise.unifyexchange.beans;

import com.mzwise.unifyexchange.common.BaseForm;
import com.mzwise.unifyexchange.common.Constants;

public class OrderInfo extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1822555787222340534L;
	private String id;
	private String symbol;
	private String accountId;
	/**
	 * 委托量
	 */
	private String originalVolume;
	/**
	 * 成交量
	 */
	private String volume;
	private String price;
	/**
	 * 创建时间
	 */
	private String ts;
	private Constants.TRADING_DIRECTION direction;
	private Constants.ORDER_TYPE type;
	private Constants.ORDER_STATUS status;
	private Constants.EXCHANGE_NAME exchange;
	/**
	 * 手续费
	 */
	private String fee;
	/**
	 * 手续费币种
	 */
	private String feeSymbol;
	private String requestId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getOriginalVolume() {
		return originalVolume;
	}
	public void setOriginalVolume(String originalVolume) {
		this.originalVolume = originalVolume;
	}
	public String getVolume() {
		return volume;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getTs() {
		return ts;
	}
	public void setTs(String ts) {
		this.ts = ts;
	}
	public Constants.TRADING_DIRECTION getDirection() {
		return direction;
	}
	public void setDirection(Constants.TRADING_DIRECTION direction) {
		this.direction = direction;
	}
	public Constants.ORDER_TYPE getType() {
		return type;
	}
	public void setType(Constants.ORDER_TYPE type) {
		this.type = type;
	}
	public Constants.ORDER_STATUS getStatus() {
		return status;
	}
	public void setStatus(Constants.ORDER_STATUS status) {
		this.status = status;
	}
	public Constants.EXCHANGE_NAME getExchange() {
		return exchange;
	}
	public void setExchange(Constants.EXCHANGE_NAME exchange) {
		this.exchange = exchange;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getFeeSymbol() {
		return feeSymbol;
	}
	public void setFeeSymbol(String feeSymbol) {
		this.feeSymbol = feeSymbol;
	}

	@Override
	public String toString() {
		return "{\"OrderInfo\":{"
				+ "\"id\":\""
				+ id + '\"'
				+ ",\"symbol\":\""
				+ symbol + '\"'
				+ ",\"accountId\":\""
				+ accountId + '\"'
				+ ",\"originalVolume\":\""
				+ originalVolume + '\"'
				+ ",\"volume\":\""
				+ volume + '\"'
				+ ",\"price\":\""
				+ price + '\"'
				+ ",\"ts\":\""
				+ ts + '\"'
				+ ",\"direction\":"
				+ direction
				+ ",\"type\":"
				+ type
				+ ",\"status\":"
				+ status
				+ ",\"exchange\":"
				+ exchange
				+ ",\"fee\":\""
				+ fee + '\"'
				+ ",\"feeSymbol\":\""
				+ feeSymbol + '\"'
				+ ",\"requestId\":\""
				+ requestId + '\"'
				+ "},\"super-OrderInfo\":" + super.toString() + "}";

	}
}
