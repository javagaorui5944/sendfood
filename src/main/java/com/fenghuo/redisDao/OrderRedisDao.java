package com.fenghuo.redisDao;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Component
public class OrderRedisDao {

	@Autowired
    private StringRedisTemplate redisTemplate;
	
	public boolean redis_addOrder(JSONObject order,long school_id) {
		// TODO Auto-generated method stub
		String id = "order:"+school_id;
		if(!isEmpty(id)){
			redis_deleteOrder(school_id);
		}
		redisTemplate.opsForValue().set(id, order.toString());
		redisTemplate.expire(id, 1, TimeUnit.HOURS);
		return true;
	}

	public boolean redis_deleteOrder(long school_id) {
		redisTemplate.delete("order:"+school_id);
		return true;
	}

	public JSONObject redis_getOrder(long school_id) {
		String id = "order:"+school_id;
		if(!isEmpty(id)){
			return JSONObject.parseObject(redisTemplate.opsForValue().get(id));
		}
		return null;
	}
	
	public boolean redis_updateOrder(JSONObject order,long school_id) {
		redisTemplate.delete("order:"+school_id);
		return redis_addOrder(order,school_id);
	}
	
	public boolean isEmpty(final String key) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				return !connection.exists(redisTemplate.getStringSerializer().serialize(key));
			}
			
		});
	}
}
