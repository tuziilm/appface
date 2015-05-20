package com.zhanghui.appface.common;

import com.zhanghui.appface.domain.RemarkStatusId;

/**
 * 带有remark, status的表单基类
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 */
public abstract class RemarkStatusForm<T extends RemarkStatusId> extends RemarkForm<T>{
    protected Byte status;

    @Override
    public T toObj() {
        T t= super.toObj();
        t.setStatus(status);
        return t;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
