package com.zhanghui.appface.mvc.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class CaseInsensitiveParamFilter implements Filter {
	private Set<String> prefixSet=new HashSet<String>();

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		String uri = ((HttpServletRequest) request).getRequestURI();
		if(prefixSet.contains(uri)){
			filterChain.doFilter(requestWraper(request), response);
		}else{
			filterChain.doFilter(request, response);
		}
	}
	
	@SuppressWarnings("rawtypes")
	private ServletRequest requestWraper(ServletRequest request){
		Map parameterMap = request.getParameterMap();
		final Map<String,String[]> wraperParamMap =new HashMap<String,String[]>(parameterMap.size());
		Iterator it = parameterMap.keySet().iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			String[] value = (String[])parameterMap.get(key);
			wraperParamMap.put(key.toString().toLowerCase(), value);
		}
		HttpServletRequest req = new HttpServletRequestWrapper((HttpServletRequest) request){
			
			@Override
			public String getParameter(String name) {
				String[] value=wraperParamMap.get(name);
				return value!=null && value.length>0? value[0]:null;
			}
			
			@Override
			public Map getParameterMap() {
				return wraperParamMap;
			}
			
			@Override
			public String[] getParameterValues(String name) {
				return wraperParamMap.get(name);
			}
			
			@Override
			public Enumeration getParameterNames() {
				return new Enumeration(){
					private Iterator<String> iterator = wraperParamMap.keySet().iterator();

					@Override
					public boolean hasMoreElements() {
						
						return iterator.hasNext();
					}

					@Override
					public Object nextElement() {
						return iterator.next();
					}};
			}
		};
		return req;
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		String prefixMappingString = config.getInitParameter("prefixMapping");
		for(String mapping : prefixMappingString.split("\\s+")){
			prefixSet.add(mapping);
		}
	}
}
