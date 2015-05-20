package com.zhanghui.appface.domain;

import java.util.HashSet;
import java.util.Set;

public class AreaManage extends RemarkStatusId{
	private String area;//λ������
	private Integer adType;//�������
	private Integer areaNum;//λ������
	private Integer  areaType;//λ������
	private Set<String> supportCountries;//���Ͷ�Ź���
	
	public String getArea() {
		return area;
	}
	public Integer getAdType() {
		return adType;
	}
	public void setAdType(Integer adType) {
		this.adType = adType;
	}
	public Integer getAreaNum() {
		return areaNum;
	}
	public void setAreaNum(Integer areaNum) {
		this.areaNum = areaNum;
	}
	public Integer getAreaType() {
		return areaType;
	}
	public void setAreaType(Integer areaType) {
		this.areaType = areaType;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getSupportCountries() {
		if (supportCountries == null || supportCountries.isEmpty()) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (String country : supportCountries) {
			sb.append(country == null ? "null" : country.toString()).append(",");
		}
		return sb.toString().substring(0, sb.length()-1);
	}
	public Set<String> getSupportcountriesObject() {
		return supportCountries;
	}
	public void setSupportCountries(String supportCountries) {
		Set<String> countriesSet = new HashSet<String>();
		String[] countriesStr= supportCountries.split(",");
		for(String str :countriesStr){
			countriesSet.add(str);
		}
		this.supportCountries = countriesSet;
	}
		
}
