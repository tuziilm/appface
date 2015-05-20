package com.zhanghui.appface.mvc.callback;

import com.zhanghui.appface.common.JsonSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractCallbackController implements JsonSupport{
	protected final Logger log=LoggerFactory.getLogger(getClass());
	@ExceptionHandler(value=Throwable.class)
	protected void fail(Throwable throwable, HttpServletResponse response){
		response.reset();
		try {
			log.error("exception handler catch exception!", throwable);
			response.getWriter().write(FAIL_JSON);
		} catch (IOException e1) {
		}
	}
	protected void simpleSuccess(HttpServletResponse response){
		try {
			response.getWriter().write(SIMPLE_SUCCESS_JSON);
		} catch (IOException e1) {
		}
	}
    protected void simpleFailed(HttpServletResponse response){
        try {
            response.getWriter().write(FAIL_JSON);
        } catch (IOException e1) {
        }
    }
}

