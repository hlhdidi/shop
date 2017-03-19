package com.hlhdidi.shop.interfaces.solr;

import com.hlhdidi.page.Pagination;
import com.hlhdidi.shop.pojo.product.Product;

public interface SolrService {
	public void addToIndex(Product product) throws Exception;
	
	public Pagination search(String keyword,Integer pageNo, Long brandId, String price) throws Exception;
}
