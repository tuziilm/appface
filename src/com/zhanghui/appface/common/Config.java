package com.zhanghui.appface.common;

import com.google.common.io.Resources;

import java.io.IOException;
import java.util.Properties;

/**
 * 集中定义一些常量
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 */
public final class Config {
	public final static Properties props=new Properties();
	static{
		try {
			props.load(Resources.getResource("config.properties").openStream());
		} catch (IOException e) {
			System.exit(1);
		}
	}
	/** 文件上传根路径*/
	public final static String UPLOAD_ROOT_DIR=props.getProperty("upload.root.dir");
    public final static String APK_DECODE_DIR=UPLOAD_ROOT_DIR+"/"+".apk";
    public final static String LOGS_ROOT_DIR=props.getProperty("logs.root.dir");
	public final static String HOST="http://"+props.getProperty("host.ip")+"/";
	public final static String APP_NAME=props.getProperty("app.name");
    public final static String DOWNLOAD_URL=props.getProperty("download.url");
    public final static String OTA_WORKSPACE=props.getProperty("ota.workspace");
    public final static String OTA_CMD=props.getProperty("ota.cmd");
    public final static String OTA_LOG_PATH=props.getProperty("ota.log.path");
    public final static String OTA_DIFF_PATH=props.getProperty("ota.diff.path");
    public final static boolean IS_MASTER= "true".equals(props.getProperty("master")) || "test".equals(props.getProperty("master")) ;
}
