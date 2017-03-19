package com.hlhdidi.shop.service.buyer;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.hlhdidi.shop.interfaces.buyer.SessionProvider;
@Service("sessionProvider")
public class SessionProviderImpl implements SessionProvider{
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Override
	public void setToGlobalSession(String key, String value) {
		redisTemplate.opsForValue().set("DSESSIONID:"+key, value);
		redisTemplate.expire("DSESSIONID:"+key, 60*60, TimeUnit.SECONDS);	//设定过期时间
	}

	@Override
	public String getFromGlobalSession(String key) {
		String val = redisTemplate.opsForValue().get("DSESSIONID:"+key);
		//重新设置过期时间
		if(val!=null) {
			redisTemplate.expire("DSESSIONID:"+key, 60*60, TimeUnit.SECONDS);	//设定过期时间
		}
		return val;
	}

}
