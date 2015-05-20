package com.zhanghui.appface.common;

/**
 * ถþิชื้
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 * @param <T>
 * @param <K>
 */
public class Tuple<T, K> {
	public T first;
	public K second;
	
	public Tuple(T first, K second) {
		this.first=first;
		this.second=second;
	}
	
	public static <T, K> Tuple<T,K> valueOf(T first, K second) {
		return new Tuple<T,K>(first, second);
	}
	
	public T getFirst() {
		return first;
	}
	
	public void setFirst(T first) {
		this.first = first;
	}
	
	public K getSecond() {
		return second;
	}
	
	public void setSecond(K second) {
		this.second = second;
	}
}
