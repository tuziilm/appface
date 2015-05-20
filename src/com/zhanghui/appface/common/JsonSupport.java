package com.zhanghui.appface.common;

import org.codehaus.jackson.map.ObjectMapper;

public interface JsonSupport {
	/** ObjectMapper是线程安全的*/
	final static ObjectMapper mapper=new ObjectMapper();
	final static String FAIL_JSON="{\"success\":false}";
	final static String SIMPLE_SUCCESS_JSON="{\"success\":true}";
	final static String FAIL_JSONP="({\"success\":false})";
	final static String SIMPLE_SUCCESS_JSONP="({\"success\":true})";
}
