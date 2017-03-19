package com.hlhdidi.shop.web.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hlhdidi.shop.interfaces.TestService;
import com.hlhdidi.shop.mapper.TestMapper;
import com.hlhdidi.shop.pojo.TestBean;

@Service("testService")
public class TestServiceImpl implements TestService{
	@Autowired
	private TestMapper testMapper;
	
	public void saveTestBean(TestBean bean) {
		testMapper.saveTestBean(bean);
	}
}
