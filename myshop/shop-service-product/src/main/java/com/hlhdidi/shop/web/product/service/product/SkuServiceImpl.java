package com.hlhdidi.shop.web.product.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hlhdidi.shop.interfaces.product.SkuService;
import com.hlhdidi.shop.mapper.product.ColorMapper;
import com.hlhdidi.shop.mapper.product.ProductMapper;
import com.hlhdidi.shop.mapper.product.SkuMapper;
import com.hlhdidi.shop.pojo.product.Color;
import com.hlhdidi.shop.pojo.product.Product;
import com.hlhdidi.shop.pojo.product.Sku;
import com.hlhdidi.shop.pojo.product.SkuQuery;

@Service("skuService")
@Transactional
public class SkuServiceImpl implements SkuService{
	@Autowired
	private SkuMapper skuMapper;
	@Autowired
	private ColorMapper colorMapper;
	@Autowired
	private ProductMapper productMapper;
	@Override
	public void save(Sku sku) {
		skuMapper.insertSelective(sku);
	}

	@Override
	public List<Sku> findSkusByProductId(Long id) {
		SkuQuery skuQuery=new SkuQuery();
		skuQuery.createCriteria().andProductIdEqualTo(id);
		List<Sku> skus = skuMapper.selectByExample(skuQuery);
		for(Sku sku:skus) {
			Color color = colorMapper.selectByPrimaryKey(sku.getColorId());
			sku.setColor(color);
		}
		return skus;
	}

	@Override
	public void update(Sku sku) {
		skuMapper.updateByPrimaryKeySelective(sku);
	}

	@Override
	public List<Sku> findShowSkusByProductId(Long id) {
		SkuQuery skuQuery=new SkuQuery();
		skuQuery.createCriteria().andProductIdEqualTo(id).andStockGreaterThan(0);
		List<Sku> skus = skuMapper.selectByExample(skuQuery);
		for(Sku sku:skus) {
			Color color = colorMapper.selectByPrimaryKey(sku.getColorId());
			sku.setColor(color);
		}
		return skus;
	}

	@Override
	public Sku findSkuById(Long skuId) {
		Sku sku = skuMapper.selectByPrimaryKey(skuId);
		Product product = productMapper.selectByPrimaryKey(sku.getProductId());
		Product simpleProduct=new Product();
		simpleProduct.setImages(product.getImages());
		simpleProduct.setName(product.getName());
		sku.setProduct(simpleProduct);
		Color color=colorMapper.selectByPrimaryKey(sku.getColorId());
		Color simpleColor=new Color();
		simpleColor.setName(color.getName());
		sku.setColor(simpleColor);
		return sku;
	}
}
