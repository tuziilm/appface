package com.zhanghui.appface.domain;

import com.zhanghui.appface.common.SystemUserType;


/**
 * 系统用户表
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 */
public class SysUser  extends RemarkId {
    /** 账号*/
    private String username;
    /** 密码*/
    private String passwd;
    /** 系统用户类型,0:系统管理员,1:业务员, 2:代理商, 3：客户*/
    private Byte sysUserType;
    /** 状态,1:正常,0:异常*/
    private Byte status;
    /** 权限值列表,形如：1|2|3*/
    private String privilege;

    public SystemUserType getSystemUserType(){
    	return SystemUserType.valueOf(sysUserType);
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd == null ? null : passwd.trim();
    }

    public Byte getSysUserType() {
        return sysUserType;
    }

    public void setSysUserType(Byte sysUserType) {
        this.sysUserType = sysUserType;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege == null ? null : privilege.trim();
    }
}