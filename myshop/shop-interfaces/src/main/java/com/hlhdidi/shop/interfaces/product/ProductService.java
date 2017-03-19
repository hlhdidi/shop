package com.hlhdidi.shop.interfaces.product;

import com.hlhdidi.page.Pagination;
import com.hlhdidi.shop.pojo.product.Product;

public interface ProductService {
	Pagination listProductPage(Long brandId, Boolean isShow, String name, Integer pageNo);

	void saveProduct(Product product);

	void show(Long[] ids)throws Exception;
	
	Product findProductById(Long id);
}
