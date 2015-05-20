package com.zhanghui.appface.domain;

import com.zhanghui.appface.common.SqlFunc;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

public abstract class Id {
	/** ID*/
    protected Integer id;
    /** 创建时间*/
    protected Date gmtCreate;
    /** 修改时间*/
    protected Date gmtModified;

    public SqlFunc getFn(){
        return SqlFunc.get();
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Date getGmtModified() {
		return gmtModified;
	}

	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
