package com.zhanghui.appface.domain;

import com.zhanghui.appface.common.JsonSupport;

public class DistributeArea extends RemarkStatusId{
	private Integer areaNum;
	private Integer[] adTypeIds;
	private Integer[] adIds;
	
	public String getAdTypeIds() {
		String result =null;
		try {
			if(adTypeIds==null){
				result = "{}";
			}else{
				result= JsonSupport.mapper.writeValueAsString(adTypeIds);
			}
		} catch (Exception e) {
		}
		return result;
	}
	public void setAdTypeIds(String adTypeIds) {
		try {
			this.adTypeIds = JsonSupport.mapper.readValue(adTypeIds, Integer[].class);
		} catch (Exception e) {
		}
	}
	public void setAdTypeIdsObject(Integer[] adTypeIds) {
			this.adTypeIds = adTypeIds;
	}
	public Integer[] getAdTypeIdsObject() {
		return adTypeIds;
	}
	public String getAdIds() {
		String result =null;
		try {
			if(adIds==null){
				result = "{}";
			}else{
				result= JsonSupport.mapper.writeValueAsString(adIds);
			}
		} catch (Exception e) {
		}
		return result;
	}
	public Integer[] getAdIdsObject() {
		return adIds;
	}
	public void setAdIds(String adIds) {
		try {
			this.adIds = JsonSupport.mapper.readValue(adIds, Integer[].class);
		} catch (Exception e) {
		}
	}
	public void setAdIds(Integer[] adIds) {
		this.adIds = adIds;
}
	public Integer getAreaNum() {
		return areaNum;
	}
	public void setAreaNum(Integer areaNum) {
		this.areaNum = areaNum;
	}
	
}
