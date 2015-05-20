package com.zhanghui.appface.common;

import com.zhanghui.appface.domain.Size;

import java.util.HashMap;
import java.util.Map;


/**
 * 上传类型
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 */
public enum UploadType {
    /** 彩信*/
    PKG("pkg","zip","10000MB"),
    /** 彩信*/
    RICH_SMS("rich_sms","*","200MB"),
	/** apk应用*/
	APK("apk","apk","200MB"),
	/** 品牌广告*/
	BRAND_ADV("brand_adv","swf|png|jpg|jpeg|gif","20MB"),
	/** 图片*/
	PIC("pic","png|jpg|jpeg|gif","5MB"),
	/** 图片*/
	ICON("icon","png|jpg|jpeg|gif|ico","5MB");
	private final static Map<String, String> contentTypes=new HashMap<String, String>();
	static{
		contentTypes.put("apk", "application/vnd.android.package-archive");
		contentTypes.put("swf", "application/x-shockwave-flash");
		contentTypes.put("png", "image/png");
		contentTypes.put("jpg", "image/jpeg");
		contentTypes.put("jpeg", "image/jpeg");
		contentTypes.put("gif", "image/gif");
		contentTypes.put("ico", "image/ico");
	}
	private String fileTypesString;
	private String[] fileTypes;
	private String module;
	private Size maxSize;
	private UploadType(String module, String fileTypes,String maxSize){
		this.module=module;
		this.fileTypesString=fileTypes;
		this.fileTypes=fileTypes.split("\\|");
		this.maxSize=Size.valueOf(maxSize);
	}
	
	public Size getMaxSize() {
		return maxSize;
	}
	
	public String getFileTypesString() {
		return fileTypesString;
	}
	
	public String[] getFileTypes() {
		return fileTypes;
	}
	
	public String getModule() {
		return module;
	}
	
	public static UploadType fromModule(String module){
		for(UploadType ut : UploadType.values()){
			if(ut.getModule().equals(module)){
				return ut;
			}
		}
		return null;
	}
	
	public static String contentType(String extension){
		if(extension==null)
			return "text/html";
		String contentType=contentTypes.get(extension.toLowerCase());
		return contentType==null?"text/html":contentType;
	}
	
	public static boolean checkModule(String module){
		for(UploadType ut : UploadType.values()){
			if(ut.getModule().equals(module)){
				return true;
			}
		}
		return false;
	}
	public boolean supportFileType(String fileType){
		if(fileType==null)
			return false;
        if(fileTypesString.equals("*"))
            return true;
		fileType=fileType.toLowerCase();
		for(String ft : fileTypes){
			if(ft.equals(fileType))
				return true;
		}
		return false;
	}
	public boolean supportFileSize(long size){
		return maxSize.getSize()>size;
	}
}
