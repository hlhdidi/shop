package com.hlhdidi.shop.web.product.service.product;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hlhdidi.shop.interfaces.product.AdService;
import com.hlhdidi.shop.mapper.product.AdMapper;
import com.hlhdidi.shop.pojo.product.Ad;
import com.hlhdidi.shop.pojo.product.AdQuery;

import redis.clients.jedis.Jedis;
@Service("adService")
public class AdServiceImpl implements AdService {
	
	@Autowired
	private AdMapper adMapper;
	@Autowired
	private RedisTemplate<String, String> template;
	
	@Override
	public List<Ad> findAdByPositionId(long positionId) {
		AdQuery adQuery=new AdQuery();
		adQuery.createCriteria().andPositionIdEqualTo(positionId);
		return adMapper.selectByExample(adQuery);
	}

	@Override
	public String findAdByPositionIdStr(long i) {
		String adStr = template.opsForValue().get("ads");
		if(adStr==null) {
			AdQuery adQuery=new AdQuery();
			adQuery.createCriteria().andPositionIdEqualTo(i);
			List<Ad> list = adMapper.selectByExample(adQuery);
			adStr=JSON.toJSONString(list);
			template.opsForValue().set("ads", adStr);
			template.expire("ads",40,TimeUnit.MINUTES);	//设置缓存时间.
		}
		return adStr;
	}

}
