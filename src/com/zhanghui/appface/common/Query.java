package com.zhanghui.appface.common;

import java.util.Collection;
import java.util.LinkedHashMap;

public abstract class Query{
	private LinkedHashMap<String, Item> items=new LinkedHashMap<String, Item>();
	public static class Item{
		private String name;
		private Object value;
		
		public Item(String name, Object value){
			this.name=name;
			this.value=value;
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Object getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}
	public static class NameQuery extends Query{
		protected String name;
		public String getName() {
			return name==null || name.trim().length()<1 ? null : "%"+name+"%";
		}
		public void setName(String name) {
			this.name = name;
			addItem("name", name);
		}
	}
	public Collection<Item> getItems(){
		return items.values();
	}
	
	public void addItem(String name, Object value){
		items.put(name, new Item(name,value));
	}
	public String getQueryString(){
		if(items==null|| items.isEmpty())
			return "";
		StringBuilder queryString=new StringBuilder();
		for(Item item: items.values()){
			queryString.append(item.name).append("=").append(item.value).append("&");
		}
		return queryString.deleteCharAt(queryString.length()-1).toString();
	}
}