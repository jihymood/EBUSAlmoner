package com.xpro.ebusalmoner.entity;


public class BreakdownRoot_M {
	private String success;

	private BreakdownBody_M body;

	private String errorCode;

	private String msg;

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getSuccess() {
		return this.success;
	}

	public void setBody(BreakdownBody_M body) {
		this.body = body;
	}

	public BreakdownBody_M getBody() {
		return this.body;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return this.msg;
	}
}
