package com.hlhdidi.shop.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hlhdidi.shop.web.convert.TrimConverter;

@Configuration
public class BeanConfig {
	@Bean
	public TrimConverter converter() {
		return new TrimConverter();
	}
}
