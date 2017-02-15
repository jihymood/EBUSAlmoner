package com.xpro.ebusalmoner.data;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 故障车表
 *
 * @author huangjh
 *
 */
@Table(name = "HitchVehicle")
public class HitchVehicle {

	@Column(name = "id", isId = true)
	private int id;

	@Column(name = "hitchLatitude")
	private String hitchLatitude;// 故障车位置纬度

	@Column(name = "hitchLongitude")
	private String hitchLongitude;// 故障车位置经度

	@Column(name = "hitchTime")
	private String hitchTime;// 故障车故障时间

	@Column(name = "line")
	private String line;// 故障车所属路线

	@Column(name = "plateNumber")
	private String plateNumber;// 故障车车牌号

	@Column(name = "driverName")
	private String driverName;// 故障车司机姓名

	@Column(name = "driverTel")
	private String driverTel;// 故障车司机手机号

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHitchLatitude() {
		return hitchLatitude;
	}

	public void setHitchLatitude(String hitchLatitude) {
		this.hitchLatitude = hitchLatitude;
	}

	public String getHitchLongitude() {
		return hitchLongitude;
	}

	public void setHitchLongitude(String hitchLongitude) {
		this.hitchLongitude = hitchLongitude;
	}

	public String getHitchTime() {
		return hitchTime;
	}

	public void setHitchTime(String hitchTime) {
		this.hitchTime = hitchTime;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverTel() {
		return driverTel;
	}

	public void setDriverTel(String driverTel) {
		this.driverTel = driverTel;
	}

	@Override
	public String toString() {
		return "HitchVehicle [id=" + id + ", hitchLatitude=" + hitchLatitude + ", hitchLongitude="
				+ hitchLongitude + ", hitchTime=" + hitchTime + ", line="
				+ line + ", plateNumber=" + plateNumber + ", driverName="
				+ driverName + ", driverTel=" + driverTel + "]";
	}

}
