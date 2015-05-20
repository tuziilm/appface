package com.zhanghui.appface.common;

import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.HttpConnectionParams;

/**
 * Http相关工具类
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 */
public class HttpUtils {
	private final static HttpClient client=initHttpClient();
	private final static int DEFAULT_TIMEOUT=10000;
	
	public static HttpClient initHttpClient(){
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
		schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
		PoolingClientConnectionManager cm = new PoolingClientConnectionManager(schemeRegistry);
		cm.setMaxTotal(200);
		cm.setDefaultMaxPerRoute(20);
	    HttpClient client=new DefaultHttpClient(cm);
	    HttpConnectionParams.setConnectionTimeout(client.getParams(), DEFAULT_TIMEOUT);
	    HttpConnectionParams.setSoTimeout(client.getParams(), DEFAULT_TIMEOUT);
	    return client;
	}
	
	public static HttpClient getClient(){
		return client;
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		HttpClientUtils.closeQuietly(client);
	}
}
