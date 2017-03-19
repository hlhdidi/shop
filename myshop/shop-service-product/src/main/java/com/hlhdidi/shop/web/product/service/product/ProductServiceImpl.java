package com.hlhdidi.shop.web.product.service.product;

import java.util.Date;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hlhdidi.page.Pagination;
import com.hlhdidi.shop.commons.cons.Constant;
import com.hlhdidi.shop.interfaces.product.ProductService;
import com.hlhdidi.shop.mapper.product.ProductMapper;
import com.hlhdidi.shop.mapper.product.SkuMapper;
import com.hlhdidi.shop.pojo.product.Product;
import com.hlhdidi.shop.pojo.product.ProductQuery;
import com.hlhdidi.shop.pojo.product.ProductQuery.Criteria;
import com.hlhdidi.shop.pojo.product.Sku;

@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	@Autowired
	private SkuMapper skuMapper;
	@Autowired
    private AmqpTemplate template;

	@Override
	public Pagination listProductPage(Long brandId, Boolean isShow, String name, Integer pageNo) {
		ProductQuery productQuery=new ProductQuery();//逆向工程生成的查询类
		Criteria criteria = productQuery.createCriteria();//查询条件接口
		StringBuilder sb=new StringBuilder();
		if(brandId!=null) {
			criteria.andBrandIdEqualTo(brandId);
			sb.append("&brandId="+brandId);
		}
		if(isShow!=null) {
			criteria.andIsShowEqualTo(isShow);
			sb.append("&isShow="+isShow);
		}
		else {
			criteria.andIsShowEqualTo(false);
			sb.append("&isShow="+false);//默认查询的是下架的
		}
		if(name!=null) {
			criteria.andNameLike("%"+name+"%");
			sb.append("&name="+name);
		}
		productQuery.setPageNo(Pagination.cpn(pageNo));
		productQuery.setPageSize(3);
		productQuery.setOrderByClause("id desc");
		Pagination pagination=new Pagination(Pagination.cpn(pageNo), 3, productMapper.countByExample(productQuery),
				productMapper.selectByExample(productQuery));
		pagination.pageView("/console/product/productList.action", sb.toString());
		return pagination;
	}

	@Override
	public void saveProduct(Product product) {
		product.setCreateTime(new Date());
		product.setIsDel(true);	//不删除
		product.setIsShow(false);//下架
		//更新库存.
		Long product_id = redisTemplate.opsForValue().increment(Constant.REDIS_PRODUCTID_KEY, 1);
		product.setId(product_id);
		productMapper.insertSelective(product);//保存数据.
		String colorList = product.getColors();
		String sizeList=product.getSizes();
		String[] colors = colorList.split(",");
		String[] sizes = sizeList.split(",");
		for(String color:colors) {
			for(String size:sizes) {
				Sku sku=new Sku();
				sku.setColorId(Long.parseLong(color));
				sku.setProductId(product.getId());
				sku.setCreateTime(new Date());
				sku.setSize(size);
				sku.setStock(222);
				sku.setUpperLimit(200);
				sku.setPrice(10f);
				sku.setMarketPrice(8f);
				skuMapper.insertSelective(sku);
			}
		}
	}

	@Override
	public void show(Long[] ids) throws Exception {
		for(Long id:ids) {
			Product product = productMapper.selectByPrimaryKey(id);
			product.setId(id);
			product.setIsShow(true);
			productMapper.updateByPrimaryKeySelective(product);//上架
//			template.convertAndSend("show",id);
			template.convertAndSend("product", "",id);
		}
	}
	
	@Override
	public Product findProductById(Long id) {
		return productMapper.selectByPrimaryKey(id);
	}
}
