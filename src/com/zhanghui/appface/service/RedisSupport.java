package com.zhanghui.appface.service;

import com.zhanghui.appface.common.TryUtils;
import com.zhanghui.appface.exception.NeedRetryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import javax.annotation.Resource;


/**
 * Á¬½ÓRedis
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 */
@Component
public class RedisSupport {
	private final Logger log=LoggerFactory.getLogger(getClass());
	@Resource
	private JedisPool jedisPool;
	
	public static interface JedisHandler<T>{
		public T handle(Jedis jedis);
	}

    public <T> T try2DoWithRedisWithNoException(final JedisHandler<T> handler){
        return try2DoWithRedis(handler, false);
    }

    public <T> T try2DoWithRedis(final JedisHandler<T> handler){
       return try2DoWithRedis(handler, true);
    }

	public <T> T try2DoWithRedis(final JedisHandler<T> handler, final boolean throwEx){
        return TryUtils.doTry(new TryUtils.Func<T>() {
            @Override
            public T run() {
                Jedis jedis = null;
                try{
                    jedis=jedisPool.getResource();
                    return handler.handle(jedis);
                }catch(JedisException e){
                    throw new NeedRetryException(e);
                }finally{
                    if(jedis!=null)
                        jedisPool.returnResource(jedis);
                }
            }

            @Override
            public void callbackWhenRetry(int times, NeedRetryException e) {
                log.warn("failure to handler jedis, try times {}. ex:{}", (times+1), e.getMessage());
            }

            @Override
            public void callbackWhenFail(int times, NeedRetryException e) {
                if(throwEx){
                    throw e;
                }else{
                    log.error(String.format("failure to handler jedis, try times %d.", (times+1)), e);
                }
            }
        });
	}
	public <T> T doWithRedis(JedisHandler<T> handler){
		Jedis jedis=null;
		try{
			jedis=jedisPool.getResource();
			return handler.handle(jedis);
		}finally{
			if(jedis!=null)
				jedisPool.returnResource(jedis);
		}
	}
}
