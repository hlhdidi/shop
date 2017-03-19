package com.hlhdidi.shop.web.console.product;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hlhdidi.shop.interfaces.product.SkuService;
import com.hlhdidi.shop.pojo.product.Sku;

@Controller
@RequestMapping("/console/sku")
public class SkuController {
	@Autowired
	private SkuService skuService;
	
	@RequestMapping("/editSku/{id}")
	public String editSku(@PathVariable Long id,Model model) {
		List<Sku> skus=skuService.findSkusByProductId(id);
		model.addAttribute("skus", skus);
		return "sku/list";
	}
	@RequestMapping("/update")
	public void update(Sku sku,HttpServletResponse response) throws IOException {
		skuService.update(sku);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write("{\"mes\":\"success\"}");
	}
}
