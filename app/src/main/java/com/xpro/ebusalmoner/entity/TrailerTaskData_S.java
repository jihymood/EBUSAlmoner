package com.xpro.ebusalmoner.entity;

public class TrailerTaskData_S {
    private String id;

    private boolean isNewRecord;

    private String remarks;

    private String createDate;

    private String updateDate;

    private String personId;

    private String hitchLatitude;

    private String hitchLongitude;

    private String hitchTime;

    private String lineName;

    private String plateNumber;

    private String code;

    private String driverName;

    private String driverTel;

    private String state;

    private String trailerCode;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setIsNewRecord(boolean isNewRecord) {
        this.isNewRecord = isNewRecord;
    }

    public boolean getIsNewRecord() {
        return this.isNewRecord;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateDate() {
        return this.createDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateDate() {
        return this.updateDate;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonId() {
        return this.personId;
    }

    public void setHitchLatitude(String hitchLatitude) {
        this.hitchLatitude = hitchLatitude;
    }

    public String getHitchLatitude() {
        return this.hitchLatitude;
    }

    public void setHitchLongitude(String hitchLongitude) {
        this.hitchLongitude = hitchLongitude;
    }

    public String getHitchLongitude() {
        return this.hitchLongitude;
    }

    public void setHitchTime(String hitchTime) {
        this.hitchTime = hitchTime;
    }

    public String getHitchTime() {
        return this.hitchTime;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getLineName() {
        return this.lineName;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getPlateNumber() {
        return this.plateNumber;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
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

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return this.state;
    }

    public void setTrailerCode(String trailerCode) {
        this.trailerCode = trailerCode;
    }

    public String getTrailerCode() {
        return this.trailerCode;
    }

    @Override
    public String toString() {
        return "TrailerTaskData_S{" +
                "code='" + code + '\'' +
                ", id='" + id + '\'' +
                ", isNewRecord=" + isNewRecord +
                ", remarks='" + remarks + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", personId='" + personId + '\'' +
                ", hitchLatitude='" + hitchLatitude + '\'' +
                ", hitchLongitude='" + hitchLongitude + '\'' +
                ", hitchTime='" + hitchTime + '\'' +
                ", lineName='" + lineName + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                ", driverName='" + driverName + '\'' +
                ", driverTel='" + driverTel + '\'' +
                ", state='" + state + '\'' +
                ", trailerCode='" + trailerCode + '\'' +
                '}';
    }
}
