package com.hlhdidi.shop.web.console.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hlhdidi.shop.interfaces.product.PositionService;
import com.hlhdidi.shop.pojo.product.Position;

@Controller
@RequestMapping("/console/position")
public class PositionController {
	@Autowired
	private PositionService positionService;
	
	@RequestMapping("/tree")
	public String tree(String root,Model model) {
		List<Position> positions=null;
		if(root.equals("source")) {
			//返回的是parent_id为0的结果集
			positions=positionService.findPositionsByParent(0L);
		}
		else {
			positions=positionService.findPositionsByParent(Long.parseLong(root));
		}
		model.addAttribute("list", positions);
		return "position/tree";
	}
	@RequestMapping("/list")
	public String list(String root,Model model) {
		List<Position> positions=null;
		if(root==null) {
			positions=positionService.findPositionsByParent(0);
			model.addAttribute("list",positions);
			return "position/list";
		}
		else {
			positions=positionService.findPositionsByParent(Long.parseLong(root));
			model.addAttribute("list",positions);
			return "position/list";
		}
	}
}
