package com.zhanghui.appface.persistence;

import com.zhanghui.appface.domain.PageView;

import java.util.List;

/**
 * ҳ��ͳ��Mapper
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 */
public interface PageViewMapper extends BaseMapper<PageView> {
	public int insertBatch(List<PageView> list);
	public String selectData(String day, String module);
}
