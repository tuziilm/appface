package com.zhanghui.appface.common;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 日志统计类，用于做页面访问以日志形式进行记录统计
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 */
public final class LogStatistics {
	private final static Logger log = LoggerFactory.getLogger(LogStatistics.class);
	public final static char SEP='\u0001';
	
	public static void log(LogModule module, String identity, String msg){
		Preconditions.checkNotNull(module);
		Preconditions.checkNotNull(identity);
		StringBuilder builder=new StringBuilder();
		builder.append(module).append(SEP)
		.append(identity).append(SEP)
		.append(System.currentTimeMillis()).append(SEP);
		append(builder,msg);
		log.info(msg.toString());
	}

    public static void log(LogModule module, String identity, HttpServletRequest request,Object ...segments){
        log(log, module, identity, false, request, segments);
    }

    public static void log(LogModule module, String identity, boolean needQueryString, HttpServletRequest request,Object ...segments){
        log(log, module, identity, needQueryString, request, segments);
    }
    public static void log(Logger logger, LogModule module, String identity, boolean needQueryString, HttpServletRequest request,Object ...segments){
		Preconditions.checkNotNull(module);
		Preconditions.checkNotNull(identity);
		StringBuilder msg=new StringBuilder();
		msg.append(module).append(SEP)
		.append(identity).append(SEP)
		.append(System.currentTimeMillis()).append(SEP);
        String ip=RequestUtils.getRemoteIp(request);
        IpSeeker.IpData ipData = IpSeeker.ipData(ip);
        append(msg, ip).append(SEP).append(ipData==null?"cn":ipData.shortcut).append(SEP);
		append(msg,request.getRequestURI());
		if(needQueryString && request.getQueryString()!=null){
			msg.append("?").append(request.getQueryString());
		}
		if(segments!=null){
			for (Object segment : segments){
				msg.append(SEP).append(segment==null?"":segment);
			}
		}
		logger.info(msg.toString());
	}

	
	private static StringBuilder append(StringBuilder builder, String msg){
		if(msg!=null){
			builder.append(msg);
		}
		return builder;
	}
}
