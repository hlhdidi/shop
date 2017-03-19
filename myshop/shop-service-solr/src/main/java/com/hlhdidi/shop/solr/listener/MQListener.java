package com.hlhdidi.shop.solr.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hlhdidi.shop.interfaces.solr.SolrService;
import com.hlhdidi.shop.mapper.product.ProductMapper;
import com.hlhdidi.shop.pojo.product.Product;
@Component
public class MQListener {
	
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private SolrService solrService;
	
	@RabbitListener(queues="solrQueue")    //监听器监听指定的Queue
    public void process(Long id) {
		Product product = productMapper.selectByPrimaryKey(id);
		try {
			solrService.addToIndex(product);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
