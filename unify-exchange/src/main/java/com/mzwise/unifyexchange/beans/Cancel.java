package com.mzwise.unifyexchange.beans;

import java.io.Serializable;

import com.mzwise.unifyexchange.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cancel extends Common {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2401774342779032830L;
	private String symbol;
	private String tid;

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 2870163071445336074L;
		private Constants.RESPONSE_STATUS status;
		private String errorCode;
		private String errorMsg;
		
		private String tid;
		
		
	}
}
