package com.mzwise.unifyexchange.beans;

import com.mzwise.unifyexchange.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@SuppressWarnings("ALL")
@Data
@Builder
public class Access extends Common {

	public Access() {
	}

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response implements Serializable {

		private static final long serialVersionUID = 7657654382832287880L;
		private Constants.RESPONSE_STATUS status;
		private String errorCode;
		private String errorMsg;
		private boolean isNormal = false;
		private String accountId;
	}
}