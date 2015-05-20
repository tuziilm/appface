package com.zhanghui.appface.domain;

import com.google.common.base.Objects;

/**
 * Ò³ÃæÍ³¼Æ
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 */
public class PageView extends Id{
	private String day; 
	private String module; 
	private String data;
	
	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("day", day).add("module", module)
		.add("data", data).toString();
	}
}
