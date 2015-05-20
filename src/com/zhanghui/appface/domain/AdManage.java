package com.zhanghui.appface.domain;

/**
 * 广告管理类
 */
public class AdManage extends RemarkStatusId{
	private String name; //广告名称
	private String showname;//显示的广告名称
	private Integer type;//广告类型
	private String picture;//用于存储广告图片的地址
	private String banner;//用于存储广告bannner的地址
	private String link;//广告链接
	private String code;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShowname() {
		return showname;
	}
	public void setShowname(String showname) {
		this.showname = showname;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getBanner() {
		return banner;
	}
	public void setBanner(String banner) {
		this.banner = banner;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
}
