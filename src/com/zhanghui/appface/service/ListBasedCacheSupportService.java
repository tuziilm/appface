package com.zhanghui.appface.service;

import com.zhanghui.appface.common.Tuple;
import com.zhanghui.appface.domain.Id;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 注意：**大数据表不能继承此接口**在增加更新操作时，应该加上resetCache(),否则缓存可能无效
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 * @param <T>
 */
public abstract class ListBasedCacheSupportService<T extends Id> extends RedisSupportService<T> {
	protected final AtomicReference<Collection<T>> cache= new AtomicReference<Collection<T>>();

	public ListBasedCacheSupportService() {
		super("list");
	}

	public Collection<T> getCache(){
		Collection<T> expect= cache.get();
		Tuple<Boolean, Long> nldResult = needLoadData();
		if(expect==null || nldResult.first){
			Collection<T> update = listForCache();
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
