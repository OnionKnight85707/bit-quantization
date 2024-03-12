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
public class FeeInfo extends Common {

	private String symbol;

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response implements Serializable {

		private Constants.RESPONSE_STATUS status;

		private String errorCode;

		private String errorMsg;

		private BigDecimal makerCommission = BigDecimal.ZERO;

		private BigDecimal takerCommission = BigDecimal.ZERO;


	}
}
