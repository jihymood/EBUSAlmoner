package com.xpro.ebusalmoner.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class BreakdownData_M implements Parcelable {
    private String personId;//人员id
    /**
     * 故障车驾驶员id
     */
    private String id;
    /**
     * 故障车车牌号
     */
    private String plateNumber;
    /**
     * 故障车位置纬度
     */
    private String hitchLatitude;
    /**
     * 故障车位置经度
     */
    private String hitchLongitude;
    /**
     * 故障车司机姓名
     */
    private String driverName;
    /**
     * 故障车司机手机号
     */
    private String driverTel;
    /**
     * 所属路线
     */
    private String lineName;
    /**
     * 故障时间
     */
    private String hitchTime;
    /**
     * 车辆自编号
     */
    private String code;
    /**
     * 状态
     */
    private String state;
    private String remarks;
    private String createDate;
    private String updateDate;
    private boolean isNewRecord;

    /**
     * 拖车code
     */
    private String trailerCode;

    public void setIsNewRecord(boolean isNewRecord){
        this.isNewRecord = isNewRecord;
    }
    public boolean getIsNewRecord(){
        return this.isNewRecord;
    }
    public void setTrailerCode(String trailerCode){
        this.trailerCode = trailerCode;
    }
    public String getTrailerCode(){
        return this.trailerCode;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getPlateNumber() {
        return this.plateNumber;
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

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public void setHitchTime(String hitchTime) {
        this.hitchTime = hitchTime;
    }

    public String getHitchTime() {
        return this.hitchTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BreakdownData_M() {
        super();
        // TODO Auto-generated constructor stub
    }

    public BreakdownData_M(Parcel rouce) {
        super();
        // TODO Auto-generated constructor stub
        personId = rouce.readString();
        plateNumber = rouce.readString();
        hitchLatitude = rouce.readString();
        hitchLongitude = rouce.readString();
        driverName = rouce.readString();
        driverTel = rouce.readString();
        lineName = rouce.readString();
        hitchTime = rouce.readString();
        code = rouce.readString();
        state = rouce.readString();
        trailerCode = rouce.readString();
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub
        dest.writeString(personId);
        dest.writeString(plateNumber);
        dest.writeString(hitchLatitude);
        dest.writeString(hitchLongitude);
        dest.writeString(driverName);
        dest.writeString(driverTel);
        dest.writeString(lineName);
        dest.writeString(hitchTime);
        dest.writeString(code);
        dest.writeString(state);
        dest.writeString(trailerCode);
    }

    public static final Creator<BreakdownData_M> CREATOR = new Creator<BreakdownData_M>() {

        /**
         * ���ⲿ�෴���л���������ʹ��
         */
        @Override
        public BreakdownData_M[] newArray(int size) {
            return new BreakdownData_M[size];
        }

        /**
         * ��Parcel�ж�ȡ����
         */
        @Override
        public BreakdownData_M createFromParcel(Parcel source) {
            return new BreakdownData_M(source);
        }
    };

}
