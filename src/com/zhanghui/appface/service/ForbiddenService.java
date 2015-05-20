package com.zhanghui.appface.service;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.TransactionBlock;
import redis.clients.jedis.exceptions.JedisException;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用来判断操作是否是可执行的
 * @author <a href="mailto:pangkunyi@gmail.com">Calvin Pang</a>
 */
@Component
public class ForbiddenService {
    public final static int DOWNLOAD_FORBIDDEN_TIME= 60 * 10;
    @Resource
    private RedisSupport redisSupport;

    public static interface Func{
        boolean forbid();
    }
    public boolean forbidden(Func func){
        return func.forbid();
    }

    public boolean forbidden(final int timeInSecond, final String keyString){
        return forbidden(new TimeBasedForbiddenFunc() {
            @Override
            public int expireTime() {
                return timeInSecond;
            }

            @Override
            public String keyString() {
                return keyString;
            }
        });
    };

    /**
     * 基于时间的禁止执行
     */
    public abstract class TimeBasedForbiddenFunc implements Func{
        @Override
        public boolean forbid() {
            return redisSupport.try2DoWithRedis(new RedisSupport.JedisHandler<Boolean>() {
                @Override
                public Boolean handle(Jedis jedis) {
                    final String key = keyString();
                    List<Object> result= jedis.multi(new TransactionBlock() {
                        @Override
                        public void execute() throws JedisException {
                            Response<Long> resp = this.setnx(key, "1");
                            this.expire(key, expireTime());
                        }
                    });
                    if(result ==null || result.isEmpty()){
                        return true;
                    }
                    return (Long)result.get(0)!=1;
                }
            });
        }

        /**
         * in second
         * @return
         */
        public abstract int expireTime();

        public abstract String keyString();
    }
}
