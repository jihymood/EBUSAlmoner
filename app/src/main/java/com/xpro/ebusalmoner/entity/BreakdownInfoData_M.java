package com.xpro.ebusalmoner.entity;

public class BreakdownInfoData_M {
    private String id;
    private boolean isNewRecord;
    private String remarks;
    private String createDate;
    private String updateDate;
    /**
     * 拖车id
     */
    private String trailerId;
    /**
     * 拖车车牌号
     */
    private String trailerCode;
    /**
     * 拖车位置纬度
     */
    private String trailerLatitude;
    /**
     * 拖车位置经度
     */
    private String trailerLongitude;
    /**
     * 拖车司机姓名
     */
    private String driverName;
    /**
     * 拖车司机电话
     */
    private String driverTel;
    /**
     * 拖车状态(0空闲，1任务中，2完成任务返回中)
     */
    private String trailerState;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isNewRecord() {
        return isNewRecord;
    }

    public void setNewRecord(boolean newRecord) {
        isNewRecord = newRecord;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public void setTrailerId(String trailerId) {
        this.trailerId = trailerId;
    }

    public String getTrailerId() {
        return this.trailerId;
    }

    public String getTrailerCode() {
        return trailerCode;
    }

    public void setTrailerCode(String trailerCode) {
        this.trailerCode = trailerCode;
    }

    public void setTrailerLatitude(String trailerLatitude) {
        this.trailerLatitude = trailerLatitude;
    }

    public String getTrailerLatitude() {
        return this.trailerLatitude;
    }

    public void setTrailerLongitude(String trailerLongitude) {
        this.trailerLongitude = trailerLongitude;
    }

    public String getTrailerLongitude() {
        return this.trailerLongitude;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverName() {
        return this.driverName;
    }

    public void setDriverTel(String driverTel) {
        this.driverTel = driverTel;
    }

    public String getDriverTel() {
        return this.driverTel;
    }

    public void setTrailerState(String trailerState) {
        this.trailerState = trailerState;
    }

    public String getTrailerState() {
        return this.trailerState;
    }
}
