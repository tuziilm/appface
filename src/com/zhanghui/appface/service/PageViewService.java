package com.zhanghui.appface.service;

import com.zhanghui.appface.domain.PageView;
import com.zhanghui.appface.persistence.PageViewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 页面统计服务
 * <pre>
 * use LogStatistics instead
 * </pre>
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 * 
 */
@Service
public class PageViewService extends BaseService<PageView> {
	private PageViewMapper pageViewMapper;
	@Autowired
	public void setPageViewMapper(PageViewMapper pageViewMapper) {
		this.mapper = pageViewMapper;
		this.pageViewMapper =pageViewMapper;
	}

	public int save(List<PageView> list){
		return pageViewMapper.insertBatch(list);
	}
	
	public String getData(String day, String module){
		return pageViewMapper.selectData(day, module);
	}
}
