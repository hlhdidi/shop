package com.hlhdidi.shop.web.login.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hlhdidi.shop.commons.utils.CodeUtils;
import com.hlhdidi.shop.interfaces.buyer.BuyerService;
import com.hlhdidi.shop.pojo.user.Buyer;
@Controller
public class RegistController {
	@Autowired
	private BuyerService buyerService;
	
	@RequestMapping("/toRegist.aspx")
	public String toRegist() {
		return "regist";
	}
	@RequestMapping("/sendPhoneCode.aspx")
	@ResponseBody
	public Map<String,String> sendPhoneCode(String phone,HttpServletRequest request) throws HttpException, IOException {
		String phoneCode=CodeUtils.genCode();
		//放入session中.
		request.getSession().setAttribute("phoneCode", phoneCode);//可能会覆盖到之前的手机验证码
		String message="欢迎注册!您的验证码为:"+phoneCode+",请核实后填写!";
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://utf8.sms.webchinese.cn/"); 
		post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");//在头文件中设置转码
		NameValuePair[] data ={ new NameValuePair("Uid", "hlhdidi"),new NameValuePair("Key", "##password##"),
				new NameValuePair("smsMob",phone),new NameValuePair("smsText",message)};
		post.setRequestBody(data);
		int method = client.executeMethod(post);
		int statusCode = post.getStatusCode();
		System.out.println("statusCode:"+statusCode+",method:"+method);
		Map<String,String> map=new HashMap<>();
		map.put("phoneCheckCode",phoneCode);
		return map;
	}
	@RequestMapping("/checkUsernameExists")
	@ResponseBody
	public Map<String, String> checkUsernameExists(String username) {
		Map<String, String> map=new HashMap<>();
		boolean isExists=buyerService.isUsernameExists(username);
		if(isExists) {
			//存在
			map.put("result","yes");
		} else {
			//不存在
			map.put("result","no");
		}
		return map;
	}
	@RequestMapping("/regist")
	public String regist(String authCode,String mobileCode,Buyer buyer,HttpServletRequest request,
			Model model) throws Exception {
		//用户注册
		HttpSession session = request.getSession();
		Object verifyCode = session.getAttribute("verifyCode");
		Object phoneCode = session.getAttribute("phoneCode");
		boolean isError=false;
		//检验手机验证码
		if(!phoneCode.equals(mobileCode)) {
			model.addAttribute("error", "手机验证码错误!");
			isError=true;
		}
		//检验验证码
		if(!authCode.equals(verifyCode)) {
			model.addAttribute("error", "验证码错误!");
			isError=true;
		}
		if(isError) {
			return "regist";
		}
		//完成注册功能.
		buyerService.regist(buyer);
		return "registSuccess";
	}
	@RequestMapping("/active.aspx")
	public String active(String username) {
		buyerService.active(username);
		return "redirect:http://127.0.0.1:8680/portal/index";
	}
}
