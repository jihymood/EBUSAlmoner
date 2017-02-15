package com.xpro.ebusalmoner.entity;

/**
 * Created by huangjh on 2017/2/10 0010 14:24
 *
 */

/**
 * 人和救济车绑定
 */

public class BoundTrailerRoot_M {
    private boolean success;

    private String errorCode;

    private String msg;

    private BoundTrailerBody_M body;

    public void setSuccess(boolean success){
        this.success = success;
    }
    public boolean getSuccess(){
        return this.success;
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
    public void setBody(BoundTrailerBody_M body){
        this.body = body;
    }
    public BoundTrailerBody_M getBody(){
        return this.body;
    }
}
