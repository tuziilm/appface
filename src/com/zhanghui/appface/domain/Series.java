package com.zhanghui.appface.domain;

import java.util.ArrayList;
import java.util.List;

public class Series {
	private String name;
	private List<Integer> data;
    public Series(String name, int capacity){
        this.name= name;
        this.data= new ArrayList<>(capacity);
    }
    public void addQuantity(int quantity){
        this.data.add(quantity);
    }
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Integer> getData() {
		return data;
	}
	public void setData(List<Integer> data) {
		this.data = data;
	}
	
}
