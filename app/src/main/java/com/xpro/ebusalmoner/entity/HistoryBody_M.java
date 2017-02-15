package com.xpro.ebusalmoner.entity;

import java.util.List;

public class HistoryBody_M {
	
	private List<HistoryData_M> data ;

	public void setData(List<HistoryData_M> data){
	this.data = data;
	}
	public List<HistoryData_M> getData(){
	return this.data;
	}
	
	
}
