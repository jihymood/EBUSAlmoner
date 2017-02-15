package com.xpro.ebusalmoner.entity;

/**
 * Created by houyang on 2016/12/20.
 */
public class MainEntity {
    private Boolean success;
    private String errorCode;
    private String msg;
    private BodyEntity body;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public BodyEntity getBody() {
        return body;
    }

    public void setBody(BodyEntity body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "MainEntity{" +
                "success=" + success +
                ", errorCode='" + errorCode + '\'' +
                ", msg='" + msg + '\'' +
                ", body=" + body.toString() +
                '}';
    }
}
