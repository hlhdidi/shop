package com.hlhdidi.shop.web.convert;

import org.springframework.core.convert.converter.Converter;

public class TrimConverter implements Converter<String, String>{

	//对于空串我们转换为null值去替代.
	@Override
	public String convert(String source) {
		if(source!=null) {
			if(!"".equals(source.trim())) {
				return source.trim();
			}
		}
		return null;
	}

}
