package com.zhanghui.appface.service;

import com.zhanghui.appface.common.Tuple;
import com.zhanghui.appface.domain.Id;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 注意：**大数据表不能继承此接口**在增加更新操作时，应该加上resetCache(),否则缓存可能无效
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 * @param <T>
 */
public abstract class ObjectBasedGroupCacheSupportService<T extends Id> extends RedisSupportService<T> {
	protected final AtomicReference<Map<String,Object>> cache= new AtomicReference<Map<String,Object>>();

	public ObjectBasedGroupCacheSupportService() {
		super("groupKeyValue");
	}


	public Object getCache(String groupKey){
		Map<String,Object> expect= cache.get();//查找缓存记录
		Tuple<Boolean, Long> nldResult = needLoadData();//是否需要加载缓存数据
		if(expect==null || nldResult.first){
			Collection<T> list = listForCache();
			Map<String,Object> update=new HashMap<String, Object>(cacheGroupKeys().length);
			for(String cgk: cacheGroupKeys()){
				update.put(cgk, newObject(cgk));
			}
			for(T t: list){
				updateCacheList(update, t);
			}
			cache.compareAndSet(expect, update);
			version=nldResult.second;
			return update.get(groupKey);
		}
		return expect.get(groupKey);
	}
	
	public abstract String[] cacheGroupKeys();

    public abstract Object newObject(String cacheGroupKey);

	public abstract void updateCacheList(Map<String, Object> update, T t);
	
	protected Collection<T> listForCache(){
		return  listAll();
	}
}
