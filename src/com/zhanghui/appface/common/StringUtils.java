package com.zhanghui.appface.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;



public class StringUtils {
	public static String urlEncode(String input){
		try {
			return URLEncoder.encode(input, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			//never happen
			e.printStackTrace();
		}
		return null;
	}
	
	public static int[] splitInts(String ids){
		if(ids==null)
			return null;
		ids=ids.trim();
		String[] _ids = ids.split("-", -1);
		if(_ids.length>0){
			int[] ret=new int[_ids.length];
			for(int i=0;i<_ids.length;i++){
				ret[i]=Integer.valueOf(_ids[i]);
			}
			return ret;
		}
		return null;
	}
	
	/**
	 * 如果目标值为null，或是空字串，则返回默认值
	 * @param obj
	 * @param defVal
	 * @return
	 */
	public static Object defVal(Object obj, Object defVal){
		return obj==null?defVal:((obj instanceof String && ((String)obj).isEmpty())?defVal:obj);
	}
	
	public static String toGoogleChartData(String[][] data){
		if(data!=null){
			StringBuilder dataString=new StringBuilder();
			dataString.append("[");
			boolean firstRow=true;
			for(String[] innerData : data){
				dataString.append("[");
				boolean firstCol=true;
				for(String iinnerData : innerData){
					if(firstRow || firstCol)
						dataString.append("'");
					dataString.append(iinnerData);
					if(firstRow || firstCol)
						dataString.append("'");
					dataString.append(",");
					if(firstCol)
						firstCol=false;
				}
				if(firstRow)
					firstRow=false;
				dataString.append("]").append(",");
			}
			dataString.append("]");
			return dataString.toString();
		}
		return "";
	}
	
	public static void main(String[] args) {
		String[][] data=new String[][]{{"a","b"},{"c","d"},{"c","d"}};
		System.out.println(toGoogleChartData(data));
	}
}
