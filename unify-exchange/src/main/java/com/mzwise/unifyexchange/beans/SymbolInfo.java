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
public class SymbolInfo extends Common {

	private String symbol;

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response implements Serializable {

		private Constants.RESPONSE_STATUS status;

		private String errorCode;

		private String errorMsg;

		/**
		 * 标的资产精度
		 */
		private Integer assetScale;

		/**
		 * 报价资产精度
		 */
		private Integer quoteScale;

		/**
		 * 最小交易金额(计价单位)
		 */
		private BigDecimal minValue;
	}
}
