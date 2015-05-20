package com.zhanghui.appface.service;

import com.zhanghui.appface.common.Tuple;
import com.zhanghui.appface.domain.Id;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 注意：**大数据表不能继承此接口**在增加更新操作时，应该加上resetCache(),否则缓存可能无效
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 * @param <T>
 */
public abstract class MapBasedCacheSupportService<K, T extends Id> extends RedisSupportService<T> {
	protected final AtomicReference<Map<K,T>> cache= new AtomicReference<Map<K,T>>();
	
	public MapBasedCacheSupportService() {
		super("keyValue");
	}


	public Map<K, T> getCache(){
		Map<K, T> expect= cache.get();
		Tuple<Boolean, Long> nldResult = needLoadData();
		if(expect==null || nldResult.first){
			Collection<T> list = listForCache();
			HashMap<K, T> update=new HashMap<K, T>(list.size());
			for(T t: list){
				updateCacheMap(update, t);
			}
			cache.compareAndSet(expect, update);
			version=nldResult.second;
			return update;
		}
		return expect;
	}
	
	public abstract void updateCacheMap(HashMap<K, T> update, T t);
	
	protected Collection<T> listForCache(){
		return  listAll();
	}
}
