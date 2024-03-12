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
public class Lever extends Common {

	private String symbol;

	private BigDecimal leverage;

	/**
	 * long：双向持仓多头
	 * short：双向持仓空头
	 *
	 */
	private String posSide;

	public Lever(String symbol, BigDecimal leverage) {
		this.symbol=symbol;
		this.leverage=leverage;
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response implements Serializable {

		private Constants.RESPONSE_STATUS status;

		private String errorCode;

		private String errorMsg;

		private BigDecimal leverage;
	}
}
