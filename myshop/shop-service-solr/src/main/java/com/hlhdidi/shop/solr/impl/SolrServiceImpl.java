package com.hlhdidi.shop.solr.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hlhdidi.page.Pagination;
import com.hlhdidi.shop.interfaces.solr.SolrService;
import com.hlhdidi.shop.mapper.product.SkuMapper;
import com.hlhdidi.shop.pojo.product.Product;
import com.hlhdidi.shop.pojo.product.Sku;
import com.hlhdidi.shop.pojo.product.SkuQuery;

@Service("solrService")
public class SolrServiceImpl implements SolrService{
	@Autowired
	private SolrClient solrServer;
	@Autowired
	private SkuMapper skuMapper;
	
	public void addToIndex(Product product) throws Exception {
		//和solr整合.将商品信息添加到索引库中.
		SolrInputDocument doc=new SolrInputDocument();
		doc.addField("id",product.getId());//string
		doc.addField("name_ik",product.getName());//string
		doc.addField("url",product.getFirstImg());//string
		//展示价格
		SkuQuery skuQuery=new SkuQuery();
		skuQuery.createCriteria().andProductIdEqualTo(product.getId());
		skuQuery.setOrderByClause("price asc");
		skuQuery.setPageNo(1);
		skuQuery.setPageSize(1);
		List<Sku> skus = skuMapper.selectByExample(skuQuery);
		doc.addField("price",skus.get(0).getPrice());//float类型
		doc.addField("brandId",product.getBrandId());	//int类型
		solrServer.add(doc);
		solrServer.commit();
	}
	
	public Pagination search(String keyword,Integer pageNo, Long brandId, String price) throws Exception {
		StringBuilder sb=new StringBuilder();
		SolrQuery solrQuery=new SolrQuery();
		solrQuery.set("fl","id,name_ik,url,price,brandId");
		solrQuery.setSort("price", ORDER.asc);
		solrQuery.setQuery("name_ik:"+keyword);
		sb.append("&keyword="+keyword);
		List<String> filters=new ArrayList<>();
		if(price!=null) {
			String[] prices = price.split("-");
			filters.add("price:["+prices[0]+" TO "+prices[1]+"]");
			sb.append("&price="+price);
		}
		if(brandId!=null) {
			filters.add("brandId:"+brandId);
			sb.append("&brandId="+brandId);
		}
		if(filters.size()>0) {
			solrQuery.setFilterQueries(filters.toArray(new String[filters.size()]));
		}
		solrQuery.setStart((Pagination.cpn(pageNo)-1)*8);
		solrQuery.setRows(8);
		//设置高亮
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("name_ik");
		solrQuery.setHighlightSimplePre("<font color='red'>");
		solrQuery.setHighlightSimplePost("</font>");
		QueryResponse query = solrServer.query(solrQuery);
		Map<String, Map<String, List<String>>> map = query.getHighlighting();
		SolrDocumentList results = query.getResults();
		long total = results.getNumFound();
		List<Product> list=new ArrayList<>();
		for(SolrDocument solrDocument:results) {
			Product product=new Product();
			String productId=(String) solrDocument.get("id");
			product.setId(Long.parseLong(productId));
			product.setBrandId(Long.parseLong(solrDocument.get("brandId")+""));
			product.setFirstImg((String)(solrDocument.get("url")));
			product.setPrice((Float)solrDocument.get("price"));
			//获取高亮的数据
			List<String> nameList = map.get(solrDocument.get("id")).get("name_ik");
			if(nameList!=null && nameList.size()>0) {
				product.setName(nameList.get(0));
			}
			else {
				product.setName((String)solrDocument.get("name_ik"));
			}
			list.add(product);
		}
		Pagination pagination=new Pagination(Pagination.cpn(pageNo),8, (int)total, list);
		pagination.pageView("/portal/product/list",sb.toString());
		return pagination;
	}
}
