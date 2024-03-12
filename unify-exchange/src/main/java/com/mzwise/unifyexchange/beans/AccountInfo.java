package com.mzwise.unifyexchange.beans;

import com.mzwise.unifyexchange.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class AccountInfo extends Common {

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Response implements Serializable {
		/**
		 *
		 */
		private static final long serialVersionUID = 3374677305748881668L;
		private Constants.RESPONSE_STATUS status = Constants.RESPONSE_STATUS.ERROR;
		private String errorCode;
		private String errorMsg;

		/**
		 * 帐户状态是否正常
		 */
		private String accountId;
		private boolean isNormal = true;
		/**
		 * 各币种余额
		 */
		private Map<String, AssetItem> assetMap = new HashMap<>();

		private List<UnifyPosition> positions=new ArrayList<>();

		public void addPosition(UnifyPosition pos)
		{
			if (positions==null)
				positions=new ArrayList<>();
			positions.add(pos);
		}


		/**
		 * 添加钱包
		 * @param currency
		 * @param available
		 * @param frozen
		 */
		public void addAsset(String currency, BigDecimal available, BigDecimal frozen,BigDecimal walletBalance) {
			assetMap.put(currency, new AssetItem(currency, available, frozen,walletBalance));
		}
		/**
		 * 添加钱包
		 * @param currency
		 * @param available
		 * @param frozen
		 */
		public void addAsset(String currency, BigDecimal available, BigDecimal frozen) {
			assetMap.put(currency, new AssetItem(currency, available, frozen,BigDecimal.ZERO));
		}

		/**
		 * 添加可用余额
		 * @param currency
		 * @param available
		 */
		public void addAvailable(String currency, BigDecimal available) {
			if (!assetMap.containsKey(currency)) {
				assetMap.put(currency, new AssetItem(currency, BigDecimal.ZERO, BigDecimal.ZERO));
			}
			AssetItem assetItem = assetMap.get(currency);
			assetItem.setAvailable(available);
		}

		/**
		 * 添加冻结余额
		 * @param currency
		 * @param frozen
		 */
		public void addFrozen(String currency, BigDecimal frozen) {
			if (!assetMap.containsKey(currency)) {
				assetMap.put(currency, new AssetItem(currency, BigDecimal.ZERO, BigDecimal.ZERO));
			}
			AssetItem assetItem = assetMap.get(currency);
			assetItem.setFrozen(frozen);
		}

		@Data
		@NoArgsConstructor
		@AllArgsConstructor
		public class AssetItem implements Serializable {
			/**
			 *
			 */
			private static final long serialVersionUID = 2464994811180200234L;
			private String currency;
			private BigDecimal available = BigDecimal.ZERO;
			private BigDecimal frozen = BigDecimal.ZERO;
			private BigDecimal walletBalance=BigDecimal.ZERO;

			public AssetItem(String currency, BigDecimal available, BigDecimal frozen) {
				this.currency = currency;
				this.available = available;
				this.frozen = frozen;
			}
		}
	}
}
