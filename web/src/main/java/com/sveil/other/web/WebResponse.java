package com.sveil.other.web;


public class WebResponse implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String respCode;
	private String memo;
	

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
