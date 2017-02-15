package com.xpro.ebusalmoner.entity;

/**
 * Created by huangjh on 2017/2/14 0014 10:53
 */

public class HistoryManagerEntity {
    private String state;
    private String detailType;
    private String hitchTime;
    private String trailerCode;

    public String getHitchTime() {
        return hitchTime;
    }

    public void setHitchTime(String hitchTime) {
        this.hitchTime = hitchTime;
    }

    public String getDetailType() {
        return detailType;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTrailerCode() {
        return trailerCode;
    }

    public void setTrailerCode(String trailerCode) {
        this.trailerCode = trailerCode;
    }
}
