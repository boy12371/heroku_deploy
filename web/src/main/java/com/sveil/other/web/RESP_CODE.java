package com.sveil.other.web;

public enum RESP_CODE {
	
	SUCCESS("000000", "调用成功"),
	PARAM_ERROR("001001", "参数错误"),
	DATA_NOT_EXIST_ERROR("001002", "数据不存在"),
	LOGIN_USER_NOT_EXIST_ERROR("001003", "登录用户名不存在"),
	LOGIN_PWD_ERROR("001004", "登录密码不正确"),
	TOKEN_INVALID_ERROR("001005", "TOKEN失效"),
	LOGIN_USER_DISABLED_ERROR("001006", "登录用户账户已停用"),
	SYSTEM_ERROR("001999", "系统错误");
	
	private String code;
	private String desc;
	
	private RESP_CODE(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
