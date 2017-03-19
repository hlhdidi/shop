package com.hlhdidi.shop.interfaces.product;

import java.util.List;

import com.hlhdidi.page.Pagination;
import com.hlhdidi.shop.pojo.product.Brand;

public interface BrandService {

	public List<Brand> listBrands(String name, Integer isDisplay);

	public Pagination listPage(String name, Integer isDisplay, Integer pageNo);

	public Brand findBrandById(Integer id);

	public void update(Brand brand);

	public void deleteIds(Long[] ids);

	public void saveBrand(Brand brand);

	public void deleteById(Integer id);

	public List<Brand> listBrandsByRedis();

}
