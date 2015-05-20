package com.zhanghui.appface.common;

import com.zhanghui.appface.domain.RemarkId;

/**
 * 带有remark的表单基类
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 */
public abstract class RemarkForm<T extends RemarkId> extends IdForm<T>{
    protected String remark;

    @Override
    public T toObj() {
        T t= super.toObj();
        t.setRemark(remark);
        return t;
    }

    public String getRemark() {
		return remark;
	}
    
    public void setRemark(String remark) {
		this.remark = remark;
	}
}
