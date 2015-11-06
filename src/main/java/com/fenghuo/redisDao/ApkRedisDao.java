package com.fenghuo.redisDao;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.fenghuo.util.FileToBytes;

@Component
public class ApkRedisDao {
	@Autowired
	private StringRedisTemplate redisTemplate;
	private FileToBytes fileutil;

	// 保存文件方法
	public void setFile(final String key, final String path) {
		if (!isEmpty(key)) {
			redisTemplate.delete(key);
		}
		redisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				File fr = new File(path);
				/*redisTemplate.opsForValue().set(key,
						new String(fileutil.object2Bytes(fr)));*/
				connection.set(key.getBytes(), fileutil.object2Bytes(fr));
				return null;
			}
			
			
		});
		

	}
	// 删除
		public void delete(String key) {
			
				redisTemplate.delete(key);
		

		}
	// 读取文件对象方法
	public File getFile(final String key) {
		String thefileString = null;
		
		if (!isEmpty(key)) {
			
			byte[] s=redisTemplate.execute(new RedisCallback<byte[]>() {

				@Override
				public byte[] doInRedis(RedisConnection connection)
						throws DataAccessException {
					/*redisTemplate.opsForValue().set(key,
							new String(fileutil.object2Bytes(fr)));*/
					
					return connection.get(key.getBytes());
				}
				
				
			});
			File file = (File) fileutil.byte2Object(s);
			
			/*thefileString = redisTemplate.opsForValue().get(key);
			byte[] s=thefileString.getBytes();
			int i=0;
			while(i<s.length){
			System.out.println(s[i]);
			i++;}
			File file = (File) fileutil.byte2Object(thefileString.getBytes());*/
			return file;
		} else
			return null;
	}



	public boolean isEmpty(final String key) {
		return redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				return !connection.exists(redisTemplate.getStringSerializer()
						.serialize(key));
			}

		});
	}


}
