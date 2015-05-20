package com.zhanghui.appface.domain;

/**
 * 统计扩展
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 * 
 */
public class StatisticSupport extends Id{
	public enum Type{
		/**下载*/
		DOWNLOAD,
		/**安装*/
		INSTALL
	}
	// 统计扩展
	/** 是否要统计下载量 */
	private boolean needCountDownload;
	/** 是否要统计安装量 */
	private boolean needCountInstall;

	public void setNeedCountInstall(boolean needCountInstall) {
		this.needCountInstall = needCountInstall;
	}

	public boolean isNeedCountInstall() {
		return needCountInstall;
	}

	public void setNeedCountDownload(boolean needCountDownload) {
		this.needCountDownload = needCountDownload;
	}

	public boolean isNeedCountDownload() {
		return needCountDownload;
	}
	
	public void setNeedCount(Type type){
		if(type==Type.DOWNLOAD)
			setNeedCountDownload(true);
		else
			setNeedCountInstall(true);
	}
}
