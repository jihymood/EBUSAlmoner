package com.xpro.ebusalmoner.entity;

/**
 * Created by huangjh on 2017/2/13 0013 15:23
 */

public class SaveReliefDetailEntity {
    //
    private String faultId;//故障id
    private String reason;//故障原因
    private String state;//维修状态 1：现场可维修 2：进场维修
    private String photos;//故障图片
    private String createBy;//personId
    private String driverId;//故障车司机id

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getFaultId() {
        return faultId;
    }

    public void setFaultId(String faultId) {
        this.faultId = faultId;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
