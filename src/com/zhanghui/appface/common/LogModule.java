package com.zhanghui.appface.common;


/**
 * 日志统计模块
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 */
public enum LogModule {
	LinkNode("链接");
	private String title;

	private LogModule(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
