package com.xpro.ebusalmoner.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class HistoryData_M implements Parcelable {
    /**
     * 故障车驾驶员id
     */
    private String id;
    /**
     * 故障车自编号
     */
    private String code;

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
     * 故障车所属路线
     */
    private String lineName;

    private int state;

    private List<HistoryImages_driver_M> images_driver;

    private String cause;

    private List<HistoryImages_trailer_M> images_trailer;
    /**
     * 故障车故障时间
     */
    private String hitchTime;

    private String number;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
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

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }

    public void setImages_driver(List<HistoryImages_driver_M> images_driver) {
        this.images_driver = images_driver;
    }

    public List<HistoryImages_driver_M> getImages_driver() {
        return this.images_driver;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getCause() {
        return this.cause;
    }

    public void setImages_trailer(List<HistoryImages_trailer_M> images_trailer) {
        this.images_trailer = images_trailer;
    }

    public List<HistoryImages_trailer_M> getImages_trailer() {
        return this.images_trailer;
    }

    public void setHitchTime(String hitchTime) {
        this.hitchTime = hitchTime;
    }

    public String getHitchTime() {
        return this.hitchTime;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return this.number;
    }

    public HistoryData_M(String id, String plateNumber, String hitchLatitude, String hitchLongitude, String
            driverName, String driverTel, String lineName, int state, String hitchTime, String number,String code) {
        super();
        this.id = id;
        this.plateNumber = plateNumber;
        this.hitchLatitude = hitchLatitude;
        this.hitchLongitude = hitchLongitude;
        this.driverName = driverName;
        this.driverTel = driverTel;
        this.lineName = lineName;
        this.state = state;
        this.hitchTime = hitchTime;
        this.number = number;
        this.code=code;
    }

    public HistoryData_M() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return "HistoryData_M [id=" + id + ", plateNumber=" + plateNumber + ", hitchLatitude=" + hitchLatitude + ", " +
                "hitchLongitude=" + hitchLongitude + ", driverName=" + driverName + ", driverTel=" + driverTel + ", " +
                "line=" + lineName + ", state=" + state + ", images_driver=" + images_driver + ", cause=" + cause + ", " +
                "images_trailer=" + images_trailer + ", hitchTime=" + hitchTime + ", number=" + number + "]";
    }

    @SuppressWarnings("unchecked")
    public HistoryData_M(Parcel source) {
        id = source.readString();
        plateNumber = source.readString();
        hitchLatitude = source.readString();
        hitchLongitude = source.readString();
        driverName = source.readString();
        driverTel = source.readString();
        lineName = source.readString();
        state = source.readInt();
        images_driver = source.readArrayList(HistoryImages_driver_M.class.getClassLoader());
        cause = source.readString();
        images_trailer = source.readArrayList(HistoryImages_trailer_M.class.getClassLoader());
        hitchTime = source.readString();
        number = source.readString();
        code=source.readString();
    }


    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub
        dest.writeString(id);
        dest.writeString(plateNumber);
        dest.writeString(hitchLatitude);
        dest.writeString(hitchLongitude);
        dest.writeString(driverName);
        dest.writeString(driverTel);
        dest.writeString(lineName);
        dest.writeInt(state);
        dest.writeList(images_driver);
        dest.writeString(cause);
        dest.writeList(images_trailer);
        dest.writeString(hitchTime);
        dest.writeString(number);
        dest.writeString(code);
    }

    public static final Creator<HistoryData_M> CREATOR = new Creator<HistoryData_M>() {

        /**
         * 供外部类反序列化本类数组使用
         */
        @Override
        public HistoryData_M[] newArray(int size) {
            return new HistoryData_M[size];
        }

        /**
         * 从Parcel中读取数据
         */
        @Override
        public HistoryData_M createFromParcel(Parcel source) {
            return new HistoryData_M(source);
        }
    };
}
