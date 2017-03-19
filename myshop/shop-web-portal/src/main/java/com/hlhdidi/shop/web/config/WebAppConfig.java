package com.hlhdidi.shop.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.hlhdidi.shop.web.inteceptor.LoginInteceptor;
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInteceptor()).addPathPatterns("/portal/order/**");//添加对于订单的过滤
	}
	
}
