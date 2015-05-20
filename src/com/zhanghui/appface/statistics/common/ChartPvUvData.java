package com.zhanghui.appface.statistics.common;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.HashMap;
import java.util.Map;

public class ChartPvUvData{
    private final static Map<String,String> linkNodeMapping=new HashMap<>();
    private static int linkNodeIdx=0;
    static {
        linkNodeMapping.put("all","all");
    }
	private String module;
	private String key;
	private int pv;
	private int uv;
	private String date;

    public ChartPvUvData(String module, String date, String key, int pv){
        this.module = module;
        this.key = key;
        this.date = date;
        this.pv = pv;
    }

    public static String mappedLinkNode(String _linknode){
        String linknode=linkNodeMapping.get(_linknode);
        if(linknode==null){
            linknode = String.valueOf(linkNodeIdx++);
            linkNodeMapping.put(_linknode, linknode);
            linkNodeMapping.put(linknode, _linknode);
        }
        return linknode;
    }

    public class KeyEntry{
        public String linkNode;
        public String country;
        public String from;

        public KeyEntry(String linkNode, String country, String from) {
            this.linkNode = linkNode;
            this.country = country;
            this.from = from;
        }
    }

    public KeyEntry getKeyEntry(){
        String[] fields = key.split(Config.SEP);
        return new KeyEntry(linkNodeMapping.get(fields[0]), fields[1], fields[2]);
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getPv() {
		return pv;
	}
	public void setPv(int pv) {
		this.pv = pv;
	}
	public int getUv() {
		return uv;
	}
	public void setUv(int uv) {
		this.uv = uv;
	}
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
