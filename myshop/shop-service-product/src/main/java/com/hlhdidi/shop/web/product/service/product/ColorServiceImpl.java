package com.hlhdidi.shop.web.product.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hlhdidi.shop.interfaces.product.ColorService;
import com.hlhdidi.shop.mapper.product.ColorMapper;
import com.hlhdidi.shop.pojo.product.Color;
import com.hlhdidi.shop.pojo.product.ColorQuery;
@Service("colorService")
public class ColorServiceImpl implements ColorService{
	@Autowired
	private ColorMapper colorMapper;
	
	@Override
	public List<Color> findAllColors() {
		//查询出parent_id!=0的
		ColorQuery colorQuery=new ColorQuery();
		colorQuery.createCriteria().andParentIdNotEqualTo(0l);
		return colorMapper.selectByExample(colorQuery);
	}
}
