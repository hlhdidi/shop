package com.hlhdidi.shop.web.console.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hlhdidi.shop.interfaces.product.AdService;
import com.hlhdidi.shop.interfaces.product.PositionService;
import com.hlhdidi.shop.pojo.product.Ad;
import com.hlhdidi.shop.pojo.product.Position;

@Controller
@RequestMapping("/console/ad")
public class AdController {
	@Autowired
	private AdService adService;
	@Autowired
	private PositionService positionService;
	
	@RequestMapping("/adFrame")
	public String adFrame() {
		return "frame/ad_main";
	}
	@RequestMapping("/ad_left")
	public String ad_left() {
		return "frame/ad_left";
	}
	@RequestMapping("/list")
	public String list(String root,Model model) {
		List<Ad> ads=adService.findAdByPositionId(Long.parseLong(root));
		Position position=positionService.findPositionsById(Long.parseLong(root));
		for(Ad ad:ads) {
			ad.setPosition(position);
		}
		model.addAttribute("ads",ads);
		return "ad/list";
	}
}
