package com.zhanghui.appface.common;


/**
 * ��־ͳ��ģ��
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 */
public enum LogModule {
	LinkNode("����");
	private String title;

	private LogModule(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
