package com.zhanghui.appface.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import com.zhanghui.appface.common.Tuple;
import com.zhanghui.appface.domain.Id;

/**
 * 注意：**大数据表不能继承此接口**在增加更新操作时，应该加上resetCache(),否则缓存可能无效
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 * @param <T>
 */
public abstract class MapBasedGroupCacheSupportService<T extends Id> extends RedisSupportService<T> {
	protected final AtomicReference<Map<String,Map<Object,Object>>> cache= new AtomicReference<Map<String,Map<Object,Object>>>();
	
	public MapBasedGroupCacheSupportService() {
		super("groupKeyValue");
	}


	public Map<Object, Object> getCache(String groupKey){
		Map<String,Map<Object, Object>> expect= cache.get();
		Tuple<Boolean, Long> nldResult = needLoadData();
		if(expect==null || nldResult.first){
			Collection<T> list = listForCache();
			Map<String,Map<Object, Object>> update=new HashMap<String, Map<Object, Object>>(cacheGroupKeys().length);
			for(String cgk: cacheGroupKeys()){
				update.put(cgk, new HashMap<Object, Object>());
			}
			for(T t: list){
				updateCacheMap(update, t);
			}
			cache.compareAndSet(expect, update);
			version=nldResult.second;
			return update.get(groupKey);
		}
		return expect.get(groupKey);
	}
	
	public abstract String[] cacheGroupKeys();
	
	public abstract void updateCacheMap(Map<String, Map<Object, Object>> update, T t);
	
	protected Collection<T> listForCache(){
		return  listAll();
	}
}
