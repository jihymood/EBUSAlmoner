package com.xpro.ebusalmoner.entity;

/**
 * Created by houyang on 2016/12/30.
 */
public class UpLoadImageMainEntity {
    private String path;
    private String msg;
    private String result;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "UpLoadImageMainEntity{" +
                "path='" + path + '\'' +
                ", msg='" + msg + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
