package com.mzwise.unifyexchange.beans;

public class GetOrder extends Common {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1638945906226887753L;
	private String symbol;
	private String tid;
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
}
