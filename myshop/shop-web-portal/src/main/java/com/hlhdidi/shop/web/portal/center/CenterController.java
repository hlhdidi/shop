package com.hlhdidi.shop.web.portal.center;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hlhdidi.shop.interfaces.product.AdService;

@Controller
@RequestMapping("/portal")
public class CenterController {
	
	@Autowired
	private AdService adService;
	
	@RequestMapping("/index")
	public String index(Model model) {
		String adStr = adService.findAdByPositionIdStr(89l);	//显示大广告
		model.addAttribute("ads", adStr);
		return "index";
	}
	
}
