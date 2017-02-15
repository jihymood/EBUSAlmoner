package com.xpro.ebusalmoner.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 拖车司机上传的故障图片（地址）
 * @author huangjh
 *
 */
public class HistoryImages_trailer_M implements Parcelable {
	private String url;

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return this.url;
	}

	public HistoryImages_trailer_M() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HistoryImages_trailer_M(String url) {
		super();
		this.url = url;
	}

	@Override
	public String toString() {
		return "HistoryImages_trailer_M [url=" + url + "]";
	}

	public HistoryImages_trailer_M(Parcel source) {
		// TODO Auto-generated constructor stub
		url = source.readString();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(url);
	}

	public static final Creator<HistoryImages_trailer_M> CREATOR = new Creator<HistoryImages_trailer_M>() {

		/**
		 * 供外部类反序列化本类数组使用
		 */
		@Override
		public HistoryImages_trailer_M[] newArray(int size) {
			return new HistoryImages_trailer_M[size];
		}

		/**
		 * 从Parcel中读取数据
		 */
		@Override
		public HistoryImages_trailer_M createFromParcel(Parcel source) {
			return new HistoryImages_trailer_M(source);
		}
	};

}
