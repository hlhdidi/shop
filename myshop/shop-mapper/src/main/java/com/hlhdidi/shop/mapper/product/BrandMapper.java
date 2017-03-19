package com.hlhdidi.shop.mapper.product;

import java.util.List;

import com.hlhdidi.shop.pojo.product.Brand;
import com.hlhdidi.shop.pojo.product.BrandQuery;

public interface BrandMapper {
	
	public List<Brand> selectBrandList(BrandQuery brandQuery);

	public Integer selectTotalCount(BrandQuery brandquery);
	
	public Brand findById(Integer id);

	public void update(Brand brand);

	public void deleteIds(Long[] ids);

	public void save(Brand brand);

	public void deleteById(Integer id);
}
