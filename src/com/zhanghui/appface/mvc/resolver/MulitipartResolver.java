package com.zhanghui.appface.mvc.resolver;

import com.zhanghui.appface.common.RedisKeys;
import com.zhanghui.appface.service.RedisSupport;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.ProgressListener;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author <a href="mailto:pangkunyi@gmail.com">Calvin Pang</a>
 */
public class MulitipartResolver extends CommonsMultipartResolver{
    private final static int PROGRESS_KEY_EXPIRE_TIME = 12 * 60 * 60;
    private String progressKey;
    @Resource
    private RedisSupport redisSupport;
    @Override
    protected FileUpload prepareFileUpload(String encoding) {
        FileUpload fileUpload = super.prepareFileUpload(encoding);
        if(progressKey != null){
            ProgressListener progressListener = new ProgressListener() {
                @Override
                public void update(final long pBytesRead, final long pContentLen, int pItems) {
                    redisSupport.try2DoWithRedisWithNoException(new RedisSupport.JedisHandler<Object>() {
                        @Override
                        public Object handle(Jedis jedis) {
                            return jedis.setex(progressKey, PROGRESS_KEY_EXPIRE_TIME, String.valueOf(pBytesRead * 100L/pContentLen));
                        }
                    });
                }
            };
            fileUpload.setProgressListener(progressListener);
        }
        return fileUpload;
    }

    @Override
    protected MultipartParsingResult parseRequest(HttpServletRequest request) throws MultipartException {
        HttpSession session = request.getSession();
        progressKey = session == null? null : RedisKeys.uploadKey(session.getId());
        return super.parseRequest(request);
    }

    @Override
    public void cleanupMultipart(MultipartHttpServletRequest request) {
        super.cleanupMultipart(request);
    }
}
