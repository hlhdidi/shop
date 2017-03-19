package com.hlhdidi.shop.web.console.product;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.hlhdidi.page.Pagination;
import com.hlhdidi.shop.commons.cons.Constant;
import com.hlhdidi.shop.interfaces.UploadService;
import com.hlhdidi.shop.interfaces.product.BrandService;
import com.hlhdidi.shop.pojo.product.Brand;

@Controller
@RequestMapping("/console/brand")
public class BrandController {
	
	@Autowired
	private BrandService brandService;
	@Autowired
	private UploadService uploadService;
	
	@RequestMapping("/list") 
	public String list(String name, Integer isDisplay,
			Model model, Integer pageNo) {
		Pagination page=brandService.listPage(name,isDisplay,pageNo);
		model.addAttribute("name",name);
		model.addAttribute("isDisplay",isDisplay);
		model.addAttribute("page",page);
		return "brand/list";
	}
	@RequestMapping("/editUI/{id}")
	public String editUI(@PathVariable("id") Integer id,Model model) {
		Brand brand=brandService.findBrandById(id);
		model.addAttribute("brand", brand);
		return "brand/edit";
	}
	@RequestMapping("/edit")
	public String edit(Brand brand) {
		brandService.update(brand);
		return "redirect:list";
	}
	@RequestMapping("/deleteIds")
	public String deleteIds(Long[] ids) {
		brandService.deleteIds(ids);
		return "forward:list";
	}
	@RequestMapping("/addUI")
	public String addUI() {
		return "brand/add";
	}
	@RequestMapping("/add")
	public String add(Brand brand) {
		brandService.saveBrand(brand);
		return "redirect:list";
	}
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id) {
		brandService.deleteById(id);
		return "redirect:/console/brand/list";
	}
	@RequestMapping("/upload")
	public void upload(MultipartFile pic,HttpServletResponse response) throws Exception{
		String path = uploadService.upload(pic.getBytes(),pic.getOriginalFilename(),pic.getSize());
		String aPath=Constant.FAST_DFS_IP+path;	//获取path路径
		response.getWriter().write("{\"path\":\""+aPath+"\"}");
	}
}
