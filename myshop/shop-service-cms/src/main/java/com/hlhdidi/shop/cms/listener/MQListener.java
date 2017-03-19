package com.hlhdidi.shop.cms.listener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hlhdidi.shop.interfaces.product.StaticPageService;
import com.hlhdidi.shop.mapper.product.ColorMapper;
import com.hlhdidi.shop.mapper.product.ProductMapper;
import com.hlhdidi.shop.mapper.product.SkuMapper;
import com.hlhdidi.shop.pojo.product.Color;
import com.hlhdidi.shop.pojo.product.Product;
import com.hlhdidi.shop.pojo.product.Sku;
import com.hlhdidi.shop.pojo.product.SkuQuery;

@Component
public class MQListener {
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private StaticPageService staticPageService;
	@Autowired
	private SkuMapper skuMapper;
	@Autowired
	private ColorMapper colorMapper;
	@RabbitListener(queues="cmsQueue")    //监听器监听指定的Queue
    public void process(Long id) {
		//做静态化页面.
		//静态化页面
		//产生静态页面的方法.
		//1.查找所有商品
		Product p=productMapper.selectByPrimaryKey(id);
//		//2.查找商品的库存.
		SkuQuery skuQuery=new SkuQuery();
		skuQuery.createCriteria().andProductIdEqualTo(id).andStockGreaterThan(0);
		List<Sku> skus = skuMapper.selectByExample(skuQuery);
		for(Sku sku:skus) {
			Color color = colorMapper.selectByPrimaryKey(sku.getColorId());
			sku.setColor(color);
		}
//		//3.根据库存查询颜色.保证不是重复.
		Map<Object,Object> map=new HashMap<>();
		Set<Color> colors=new HashSet<>();
		for(Sku sku:skus) {
			colors.add(sku.getColor());
		}
		map.put("product", p);
		map.put("skus", skus);	
		map.put("colors", colors);
		staticPageService.toStatic(map,id);//id是为了获取名字.
	}
}
