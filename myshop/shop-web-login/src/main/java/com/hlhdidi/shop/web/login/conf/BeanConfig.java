package com.hlhdidi.shop.web.login.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hlhdidi.shop.web.login.converter.TrimConverter;

@Configuration
public class BeanConfig {
	@Bean
	public TrimConverter converter() {
		return new TrimConverter();
	}
}
