package com.zhanghui.appface.domain;

import java.util.List;

import com.zhanghui.appface.common.AbstractJsonObject;

public class HighChartData extends AbstractJsonObject{
	public List<String> date;
	public List<Series> series;//ÇúÏßÊý¾Ý
	
	public List<String> getDate() {
		return date;
	}
	public void setDate(List<String> date) {
		this.date = date;
	}
	public List<Series> getSeries() {
		return series;
	}
	public void setSeries(List<Series> series) {
		this.series = series;
	}
}
