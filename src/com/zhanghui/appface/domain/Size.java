package com.zhanghui.appface.domain;

import java.text.NumberFormat;

/**
 * 容量，支持GB、MB、KB的解释
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 */
public final class Size implements Comparable<Size>{
	/** 容量，单位byte*/
	private long size;
	/** 容量，字面意思*/
	private String plainSize="0KB";
	@Override
	public int compareTo(Size s) {
		if(s==null)
			return 1;
		return size-s.size>0?1:(size-s.size==0?0:-1);
	}
	
	private Size(){
	}
	
	@Override
	public String toString() {
		return plainSize;
	}
	
	public static Size valueOf(long _size){
		if(_size<0)
			return null;
		Size size=new Size();
		size.size=_size;
		NumberFormat nf=NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(2);
		double plainSize=_size/1024.0;
		if(plainSize<1024.0){
			size.plainSize=nf.format(plainSize)+"KB";
			return size;
		}
		plainSize=plainSize/1024.0;
		if(plainSize<1024.0){
			size.plainSize=nf.format(plainSize)+"MB";
			return size;
		}
		plainSize=plainSize/1024.0;
		size.plainSize=nf.format(plainSize)+"GB";
		return size;
	}
	
	public static Size valueOf(String sizeString){
		if(sizeString==null)
			return null;
		Size size=new Size();
		sizeString=sizeString.trim().toUpperCase();
		if(sizeString.length()>2){
			String unit=sizeString.substring(sizeString.length()-2, sizeString.length());
			String num=sizeString.substring(0,sizeString.length()-2).trim();
			try{
				if("GB".equals(unit)){
					size.size=(long)(Double.valueOf(num)*1024L)*1024L*1024L;
					size.plainSize=sizeString;
				}else if("MB".equals(unit)){
					size.size=(long)(Double.valueOf(num)*1024L)*1024L;
					size.plainSize=sizeString;
				}else if("KB".equals(unit)){
					size.size=(long)(Double.valueOf(num)*1024L);
					size.plainSize=sizeString;
				}
				return size;
			}catch(Exception e){
				//do nothing
			}
		}
		return null;
	}

	public long getSize() {
		return size;
	}

	public String getPlainSize() {
		return plainSize;
	}
	
	public static void main(String[] args) {
		Size size=Size.valueOf(100222200222000L);
		System.out.println(size);
	}
}
