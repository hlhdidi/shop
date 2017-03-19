package com.hlhdidi.shop.web.product.service.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hlhdidi.page.Pagination;
import com.hlhdidi.shop.interfaces.product.BrandService;
import com.hlhdidi.shop.mapper.product.BrandMapper;
import com.hlhdidi.shop.pojo.product.Brand;
import com.hlhdidi.shop.pojo.product.BrandQuery;
@Service("brandService")
@Transactional
public class BrandServiceImpl implements BrandService{
	@Autowired
	private BrandMapper brandMapper;
	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	
	@Override
	public List<Brand> listBrands(String name, Integer isDisplay) {
		BrandQuery brandQuery=new BrandQuery();
		if(name!=null) {
			brandQuery.setName(name);
		}
		if(isDisplay==null) {
			brandQuery.setIsDisplay(1);
		}
		else {
			brandQuery.setIsDisplay(isDisplay);
		}
		List<Brand> brandList=brandMapper.selectBrandList(brandQuery);
		return brandList;
	}

	@Override
	public Pagination listPage(String name, Integer isDisplay, Integer pageNo) {
		BrandQuery brandquery=new BrandQuery();
		StringBuilder sb=new StringBuilder();
		if(name!=null) {
			brandquery.setName(name);
			sb.append("name=").append(name);
		}
		if(isDisplay==null) {
			brandquery.setIsDisplay(1);	//默认展示使用的品牌.
			sb.append("&isDisplay=").append(1);
		}
		else {
			brandquery.setIsDisplay(isDisplay);
			sb.append("&isDisplay=").append(isDisplay);
		}
		brandquery.setPageSize(3);//设置页大小.
		if(pageNo!=null) {
			brandquery.setPageNo(pageNo);//设置当前页数.
		}
		Integer totalCount=brandMapper.selectTotalCount(brandquery);
		List<Brand> brandList=brandMapper.selectBrandList(brandquery);
		Pagination page = new Pagination();
		page.setPageNo(brandquery.getPageNo());
		page.setPageSize(brandquery.getPageSize());
		page.setTotalCount(totalCount);
		page.pageView("/console/brand/list",sb.toString());
		page.setList(brandList);
		return page;
	}

	@Override
	public Brand findBrandById(Integer id) {
		return brandMapper.findById(id);
	}

	@Override
	public void update(Brand brand) {
		brandMapper.update(brand);
		redisTemplate.opsForHash().put("brand_boot",brand.getId(),brand.getName());
	}

	@Override
	public void deleteIds(Long[] ids) {
		brandMapper.deleteIds(ids);
		for(Long id:ids) {
			redisTemplate.opsForHash().delete("brand_boot",id);
		}
	}

	@Override
	public void saveBrand(Brand brand) {
		brandMapper.save(brand);
		redisTemplate.opsForHash().put("brand_boot", brand.getId(),brand.getName());
	}

	@Override
	public void deleteById(Integer id) {
		brandMapper.deleteById(id);
		redisTemplate.opsForHash().delete("brand_boot",id);
	}

	@Override
	public List<Brand> listBrandsByRedis() {
		Map<Object, Object> map = redisTemplate.opsForHash().entries("brand_boot");
		if(map!=null && map.size()>0) {
			List<Brand> brands=new ArrayList<>();
			for(Map.Entry<Object,Object> en:map.entrySet()) {
				Brand brand=new Brand();
				brand.setId(Long.parseLong(en.getKey()+""));
				brand.setName(en.getValue()+"");
				brands.add(brand);
			}
			return brands;
		}
		else {
			List<Brand> brands=brandMapper.selectBrandList(null);
			map=new HashMap<>();
			for(Brand brand:brands) {
				map.put(brand.getId()+"",brand.getName());
			}
			redisTemplate.opsForHash().putAll("brand_boot",map);
			return brands;
		}
	}
	

}
