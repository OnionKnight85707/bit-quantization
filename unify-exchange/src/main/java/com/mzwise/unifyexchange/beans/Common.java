package com.mzwise.unifyexchange.beans;

import com.mzwise.unifyexchange.common.BaseForm;
import lombok.Data;

@Data
public class Common extends BaseForm {

	private String accessKey;
	private String secretkey;
	private String accountId;
	private String passphrase;
	/**
	 * 客户自定义ID
	 */
	private String clientId;

	/**
	 * 复制
	 * @param source
	 */
	public Common from(Common source) {
		setAccessKey(source.getAccessKey());
		setSecretkey(source.getSecretkey());
		setPassphrase(source.getPassphrase());
		setAccountId(source.getAccountId());
		setClientId(source.getClientId());
		return this;
	}
	
}
