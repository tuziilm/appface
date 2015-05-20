package com.zhanghui.appface.service;

import com.zhanghui.appface.common.Paginator;
import com.zhanghui.appface.domain.Id;
import com.zhanghui.appface.persistence.BaseMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据操作服务基础类
 * 
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 * 
 */
@Service
public abstract class BaseService<T extends Id> {
	protected BaseMapper<T> mapper;
	
	public T get(Integer id) {
		return mapper.selectById(id);
	}

    public int saveOrUpdate(T t){
        if(t.getId()==null || t.getId()<1){
            return save(t);
        }else{
            return update(t);
        }
    }

	public int save(T t) {
		return mapper.insert(t);
	}

	public int update(T t) {
		return mapper.updateByIdSelective(t);
	}

	public List<T> listAll() {
		return mapper.selectAll();
	}
	
	public List<T> list(Paginator page) {
		List<T> list = mapper.select(page);
        list = initPaginator(page, list);
        if(page.isNeedTotal()){
            if(page.getPage()>1 || page.isHasNextPage()){
                int total = mapper.count(page);
                page.setTotal(total);
            }else if(list!=null){
                page.setTotal(list.size());
            }
        }
        return list;
	}
	
	protected <K> List<K> initPaginator(Paginator page, List<K> list){
		if(list==null|| list.isEmpty()){
			page.setHasNextPage(false);
			page.setHasData(false);
			return list;
		}else{
			page.setHasData(true);
			if(!page.isPageless() && list.size()>page.getSize()){
				page.setHasNextPage(true);
				return list.subList(0, list.size()-1);
			}else{
				page.setHasNextPage(false);
				return list;
			}
		}
	}
	
	public int delete(Integer id) {
		return mapper.deleteById(id);
	}
	
	public int delete(int[] ids) {
		return mapper.deleteByIds(ids);
	}
}
