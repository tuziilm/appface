package com.zhanghui.appface.domain;

/**
 * ��������
 */
public class AdManage extends RemarkStatusId{
	private String name; //�������
	private String showname;//��ʾ�Ĺ������
	private Integer type;//�������
	private String picture;//���ڴ洢���ͼƬ�ĵ�ַ
	private String banner;//���ڴ洢���bannner�ĵ�ַ
	private String link;//�������
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
