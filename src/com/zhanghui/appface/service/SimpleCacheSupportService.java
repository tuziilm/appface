package com.zhanghui.appface.service;

import com.zhanghui.appface.common.Tuple;
import com.zhanghui.appface.domain.Id;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 注意：**大数据表不能继承此接口**在增加更新操作时，应该加上resetCache(),否则缓存可能无效
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 * @param <T>
 */
public class SimpleCacheSupportService<T extends Id> extends RedisSupportService<T> {
	protected final AtomicReference<LinkedHashMap<Integer,T>> cache= new AtomicReference<LinkedHashMap<Integer,T>>();
	
	public SimpleCacheSupportService() {
		super("id2ObjectMap");
	}


	public LinkedHashMap<Integer, T> getCache(){
		LinkedHashMap<Integer, T> expect= cache.get();
		Tuple<Boolean, Long> nldResult = needLoadData();
		if(expect==null || nldResult.first){
			Collection<T> list = listForCache();
			LinkedHashMap<Integer, T> update=new LinkedHashMap<Integer, T>(list.size());
			for(T t: list){
				update.put(t.getId(), t);
			}
			cache.compareAndSet(expect, update);
			version=nldResult.second;
			return update;
		}
		return expect;
	}
	
	protected Collection<T> listForCache(){
		return  listAll();
	}
}
