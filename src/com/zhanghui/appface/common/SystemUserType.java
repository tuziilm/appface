package com.zhanghui.appface.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 系统用户类型
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 */
public enum SystemUserType {
	/** 系统管理员*/
	ADMIN(0,"系统管理员"),
	/** 普通运营人员*/
	OPERATOR(1,"业务员","/clerk","/sysuser/info_modify","/sysuser/index","/sysuser/info_save"),
	/** 代理商*/
	PROXY(2,"代理商","/proxy","/sysuser/info_modify","/sysuser/index","/sysuser/info_save"),
	/** 客户 */
	CLIENT(3,"客户","/client","/sysuser/info_modify","/sysuser/index","/sysuser/info_save"),
	/** 未识别*/
	UNKNOWN(-1,"未知");
	private int id;
	private String name;
	private List<String> resources;
	SystemUserType(int id, String name, String ...resources){
		this.id=id;
		this.name=name;
		if(resources==null){
			this.resources=Collections.emptyList();
		}else{
			this.resources=new ArrayList<String>(resources.length);
			for(String resource : resources){
				this.resources.add(resource);
			}
		}
	}
	
	public int getId() {
		return id;
	}
	
	public List<String> getResources() {
		return resources;
	}
	public String getName() {
		return name;
	}
	public static SystemUserType valueOf(Byte type){
		if(type==null)
			return UNKNOWN;
		if(type==0)
			return ADMIN;
		if(type==1)
			return OPERATOR;
		if(type==2)
			return PROXY;
		if(type==3)
			return CLIENT;
		return UNKNOWN;
	}
}
