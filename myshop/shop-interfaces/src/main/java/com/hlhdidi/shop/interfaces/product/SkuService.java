package com.hlhdidi.shop.interfaces.product;

import java.util.List;

import com.hlhdidi.shop.pojo.product.Sku;

public interface SkuService {
	public void save(Sku sku);
	public List<Sku> findSkusByProductId(Long id);
	public void update(Sku sku);
	public List<Sku> findShowSkusByProductId(Long id);
	public Sku findSkuById(Long skuId);
}
