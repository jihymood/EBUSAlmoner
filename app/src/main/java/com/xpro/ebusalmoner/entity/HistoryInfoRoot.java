package com.xpro.ebusalmoner.entity;

/**
 * Created by huangjh on 2017/2/15 0015 11:00
 */

public class HistoryInfoRoot {

    private Boolean success;

    private String errorCode;

    private String msg;

    private HistoryInfoBody body;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
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

    public void setBody(HistoryInfoBody body) {
        this.body = body;
    }

    public HistoryInfoBody getBody() {
        return this.body;
    }
}
