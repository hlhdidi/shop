package com.hlhdidi.shop.mapper;

import org.apache.ibatis.annotations.Insert;

import com.hlhdidi.shop.pojo.TestBean;

public interface TestMapper {
	@Insert("insert into testbean(name,age) VALUES(#{name},#{age})")
	public void saveTestBean(TestBean testBean);
}
