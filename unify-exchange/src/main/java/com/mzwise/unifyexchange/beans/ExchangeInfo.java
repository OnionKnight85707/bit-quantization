package com.mzwise.unifyexchange.beans;

import com.mzwise.unifyexchange.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ExchangeInfo extends Common {

	private String symbol;

	@AllArgsConstructor
	@Data
	@NoArgsConstructor
	public static class Response implements Serializable {

		private Constants.RESPONSE_STATUS status;
		private String errorCode;
		private String errorMsg;

		private String lotSize;

		private String minQty;

		private String maxQty;
	}
}
