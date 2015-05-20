package com.zhanghui.appface.service;

import com.zhanghui.appface.common.RedisKeys;
import com.zhanghui.appface.common.Tuple;
import com.zhanghui.appface.domain.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

/**
 * 注意：**大数据表不能继承此接口**在增加更新操作时，应该加上resetCache(),否则缓存可能无效
 * @author <a href="pangkunyi@gmail.com">Calvin Pang</a>
 *
 * @param <T>
 */
public class RedisSupportService<T extends Id> extends BaseService<T> {
	private final Logger log=LoggerFactory.getLogger(getClass());
	@Resource
	protected RedisSupport redisSupport;
	protected volatile long version = 0;
	public final String versionKey;
	public RedisSupportService(String module) {
		this.versionKey= RedisKeys.moduleServiceVersionKey(this.getClass(), module);
	}

	protected Tuple<Boolean, Long> needLoadData(){
		try{
			return redisSupport.doWithRedis(new RedisSupport.JedisHandler<Tuple<Boolean, Long>>(){
				@Override
				public Tuple<Boolean, Long> handle(Jedis jedis) {
					String versionInRedis=jedis.get(versionKey);
					if(versionInRedis==null){
						return Tuple.valueOf(true, jedis.incr(versionKey));
					}
					long v = Long.valueOf(versionInRedis);
					return Tuple.valueOf(v!=version, v);
				}});
		}catch(Exception e){
			log.error(String.format("falure to access redis. use local cache for key[%s], it may cause inconsistence.", versionKey), e);
			return Tuple.valueOf(false,-1L);
		}
	}
	
	protected void resetCache(){
		try{
			redisSupport.try2DoWithRedis(new RedisSupport.JedisHandler<Long>() {
				@Override
				public Long handle(Jedis jedis) {
					return jedis.incr(versionKey);
				}
			});
		}catch(Exception e){
			log.error(String.format("falure to access redis. can not reset Cache for key[%s], it may cause inconsistence.",versionKey), e);
		}
	}
	
	@Override
	public int save(T t) {
		int ret=super.save(t);
		resetCache();
		return ret;
	}

	@Override
	public int update(T t) {
		int ret=super.update(t);
		resetCache();
		return ret;
	}

	@Override
	public int delete(Integer id) {
		int ret= super.delete(id);
		resetCache();
		return ret;
	}

	@Override
	public int delete(int[] ids) {
		int ret= super.delete(ids);
		resetCache();
		return ret;
	}

}
