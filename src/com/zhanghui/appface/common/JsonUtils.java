package com.zhanghui.appface.common;

import java.util.Collection;

/**
 * Json操作工具类
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 */
public class JsonUtils {
	private final static Object[] EMPTY_OBJS=new Object[0];
	
	public static interface ToObjectConverter<T> {
		Object convert(T t);
	}
	/**
	 * 对象转换
	 * @param colls
	 * @param converter
	 * @return
	 */
	public static <T> Object[] toObject(Collection<T> colls, ToObjectConverter<T> converter){
		if(colls==null || colls.isEmpty()){
			return EMPTY_OBJS;
		}
		Object[] objs=new Object[colls.size()];
		int i=0;
		for(T t : colls){
			objs[i++]=converter.convert(t);
		}
		return objs;
	}
}
