package com.xpro.ebusalmoner.entity;

/**
 * Created by huangjh on 2017/2/15 0015 11:01
 */

public class HistoryInfoData {
    private String id;

    private String faultId;

    private String reason;

    private String state;

    private String photos;

    private Long createDate;

    private String createBy;

    private String driverId;

    private String delFlag;

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return this.id;
    }
    public void setFaultId(String faultId){
        this.faultId = faultId;
    }
    public String getFaultId(){
        return this.faultId;
    }
    public void setReason(String reason){
        this.reason = reason;
    }
    public String getReason(){
        return this.reason;
    }
    public void setState(String state){
        this.state = state;
    }
    public String getState(){
        return this.state;
    }
    public void setPhotos(String photos){
        this.photos = photos;
    }
    public String getPhotos(){
        return this.photos;
    }
    public void setCreateDate(Long createDate){
        this.createDate = createDate;
    }
    public Long getCreateDate(){
        return this.createDate;
    }
    public void setCreateBy(String createBy){
        this.createBy = createBy;
    }
    public String getCreateBy(){
        return this.createBy;
    }
    public void setDriverId(String driverId){
        this.driverId = driverId;
    }
    public String getDriverId(){
        return this.driverId;
    }
    public void setDelFlag(String delFlag){
        this.delFlag = delFlag;
    }
    public String getDelFlag(){
        return this.delFlag;
    }
}
