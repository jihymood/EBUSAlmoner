package com.xpro.ebusalmoner.entity;

public class TrailerTaskRoot_S {
	private String success;

	private TrailerTaskBody_S body;

	private String errorCode;

	private String msg;

	public void setSuccess(String success){
	this.success = success;
	}
	public String getSuccess(){
	return this.success;
	}
	public void setBody(TrailerTaskBody_S body){
	this.body = body;
	}
	public TrailerTaskBody_S getBody(){
	return this.body;
	}
	public void setErrorCode(String errorCode){
	this.errorCode = errorCode;
	}
	public String getErrorCode(){
	return this.errorCode;
	}
	public void setMsg(String msg){
	this.msg = msg;
	}
	public String getMsg(){
	return this.msg;
	}
}
