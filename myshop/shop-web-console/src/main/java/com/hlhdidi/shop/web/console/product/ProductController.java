package com.hlhdidi.shop.web.console.product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.hlhdidi.page.Pagination;
import com.hlhdidi.shop.commons.cons.Constant;
import com.hlhdidi.shop.interfaces.UploadService;
import com.hlhdidi.shop.interfaces.product.BrandService;
import com.hlhdidi.shop.interfaces.product.ColorService;
import com.hlhdidi.shop.interfaces.product.ProductService;
import com.hlhdidi.shop.pojo.product.Brand;
import com.hlhdidi.shop.pojo.product.Color;
import com.hlhdidi.shop.pojo.product.Product;

@Controller
@RequestMapping("/console/product")
public class ProductController {
	
	@Autowired
	private BrandService brandService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ColorService colorService;
	@Autowired
	private UploadService uploadService;
	@RequestMapping("/productFrame")
	public String productFrame() {
		return "frame/product_main";
	}
	@RequestMapping("/productLeft")
	public String productLeft() {
		return "frame/product_left";
	}
	@RequestMapping("/productList")
	public String productList(Long brandId,Boolean isShow,String name,Integer pageNo,Model model) {
		//TODO brandId isShow name pageNo
		//查询出所有的品牌
		List<Brand> brands = brandService.listBrands(null, 1);
		Pagination page=productService.listProductPage(brandId,isShow,name,pageNo);
		model.addAttribute("page", page);
		model.addAttribute("brands", brands);
		model.addAttribute("isShow",isShow);
		model.addAttribute("name",name);
		model.addAttribute("brandId", brandId);
		model.addAttribute("pageNo",pageNo);
		return "product/list";
	}
	@RequestMapping("/addUI")
	public String addUI(Model model) {
		//查询出所有的品牌
		List<Brand> brands = brandService.listBrands(null, 1);
		model.addAttribute("brands", brands);
		//查询出所有的颜色.
		List<Color> colors=colorService.findAllColors();
		model.addAttribute("colors", colors);
		return "product/add";
	}
	@RequestMapping("/uploadPics")
	@ResponseBody
	public List<String> uploadPics(HttpServletRequest request,HttpServletResponse response
			,@RequestParam(value="pics",required=false) MultipartFile[] pics) throws Exception {
		List<String> paths=new ArrayList<>();
		for(MultipartFile pic:pics) {
			paths.add(Constant.FAST_DFS_IP+uploadService.upload(pic.getBytes(), pic.getOriginalFilename(),pic.getSize()));
		}
		return paths;
	}
	@RequestMapping("/uploadFck")
	public void uploadFck(HttpServletRequest request,HttpServletResponse response) {
		MultipartRequest mr=(MultipartRequest) request;
		Map<String, MultipartFile> map = mr.getFileMap();
		response.setContentType("application/json;charset=UTF-8");
		for (Map.Entry<String,MultipartFile> en :map.entrySet()) {
			JSONObject object=new JSONObject();
			MultipartFile pic = en.getValue();
			try{
				String path = uploadService.upload(pic.getBytes(), pic.getOriginalFilename(), pic.getSize());
				String filePath=Constant.FAST_DFS_IP+path;
				object.put("url", filePath);
				object.put("error", 0);
				response.getWriter().write(object.toString());
			}catch(Exception e) {
				object.put("error", 1);
				try {
					response.getWriter().write(object.toString());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	@RequestMapping("/add")
	public String add(Product product) {
		productService.saveProduct(product);
		return "redirect:productList.action";
	}
	//上架
	@RequestMapping("/isShow")
	public String isShow(Long[] ids) throws Exception {
		productService.show(ids);
		return "forward:productList";
	}
}
