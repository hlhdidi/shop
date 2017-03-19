package com.hlhdidi.shop.web.portal.product;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hlhdidi.page.Pagination;
import com.hlhdidi.shop.interfaces.product.BrandService;
import com.hlhdidi.shop.interfaces.product.ProductService;
import com.hlhdidi.shop.interfaces.product.SkuService;
import com.hlhdidi.shop.interfaces.solr.SolrService;
import com.hlhdidi.shop.pojo.product.Brand;
import com.hlhdidi.shop.pojo.product.Color;
import com.hlhdidi.shop.pojo.product.Product;
import com.hlhdidi.shop.pojo.product.Sku;

@Controller
@RequestMapping("/portal/product")
public class ProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private BrandService brandService;
	@Autowired
	private SolrService solrService;
	@Autowired
	private SkuService skuService;

	@RequestMapping("/list")
	public String list(String keyword, Integer pageNo, String price, Long brandId, Model model) throws Exception {
		List<Brand> brands = brandService.listBrandsByRedis();
		Pagination page = solrService.search(keyword, pageNo, brandId, price);
		model.addAttribute("keyword", keyword);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pagination", page);
		model.addAttribute("brands", brands);
		model.addAttribute("brandId", brandId);
		model.addAttribute("price", price);
		// 添加Map
		Map<String, String> map = new HashMap<>();
		// price和brandId支持回显
		if (price != null) {
			if (price.contains("*")) {
				map.put("价格", price.substring(0, price.indexOf("-")) + "以上");
			} else {
				map.put("价格", price);
			}
		}
		if (brandId != null) {
			// 查询出商品的种类.
			for (Brand brand : brands) {
				if (brand.getId().equals(brandId)) {
					map.put("品牌", brand.getName());
					break;
				}
			}
		}
		model.addAttribute("map", map);
		return "search";
	}

	@RequestMapping("/detail/{id}")
	public String detail(@PathVariable Long id, Model model) {
		// 1.查找所有商品
		Product product = productService.findProductById(id);
		// 2.查找商品的库存.
		List<Sku> skus = skuService.findShowSkusByProductId(product.getId());
		// 3.根据库存查询颜色.保证不是重复.
		Set<Color> colors = new HashSet<>();
		for (Sku sku : skus) {
			colors.add(sku.getColor());
		}
		model.addAttribute("product", product);
		model.addAttribute("skus", skus);
		model.addAttribute("colors", colors);
		return "product";
	}
}
