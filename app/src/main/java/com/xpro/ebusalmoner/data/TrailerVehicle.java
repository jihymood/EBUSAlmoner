package com.xpro.ebusalmoner.data;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
/**
 * 拖车表
 * @author huangjh
 *
 */
@Table(name="trailer")
public class TrailerVehicle {

	@Column(name = "id", isId = true, autoGen = true)
	private int id;

	@Column(name = "trailerId")
	private int trailerId;// 拖车id

	@Column(name="trailerState")
	private int trailerState;// 拖车状态(0空闲，1任务中，2完成任务返回中)

	@Column(name = "trailerLatitude")
	private String trailerLatitude;// 拖车位置纬度

	@Column(name = "trailerLongitude")
	private String trailerLongitude;// 拖车位置经度

	@Column(name = "trailerNumber")
	private String trailerNumber;// 拖车车牌号

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTrailerId() {
		return trailerId;
	}

	public void setTrailerId(int trailerId) {
		this.trailerId = trailerId;
	}

	public int getTrailerState() {
		return trailerState;
	}

	public void setTrailerState(int trailerState) {
		this.trailerState = trailerState;
	}

	public String getTrailerLatitude() {
		return trailerLatitude;
	}

	public void setTrailerLatitude(String trailerLatitude) {
		this.trailerLatitude = trailerLatitude;
	}

	public String getTrailerLongitude() {
		return trailerLongitude;
	}

	public void setTrailerLongitude(String trailerLongitude) {
		this.trailerLongitude = trailerLongitude;
	}

	public String getTrailerNumber() {
		return trailerNumber;
	}

	public void setTrailerNumber(String trailerNumber) {
		this.trailerNumber = trailerNumber;
	}

	@Override
	public String toString() {
		return "TrailerVehicle [id=" + id + ", trailerId=" + trailerId
				+ ", trailerState=" + trailerState + ", trailerLatitude="
				+ trailerLatitude + ", trailerLongitude=" + trailerLongitude
				+ ", trailerNumber=" + trailerNumber + "]";
	}


}
