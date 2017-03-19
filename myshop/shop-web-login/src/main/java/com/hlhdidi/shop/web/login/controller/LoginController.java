package com.hlhdidi.shop.web.login.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hlhdidi.shop.commons.utils.CookieUtils;
import com.hlhdidi.shop.commons.utils.EncodingUtils;
import com.hlhdidi.shop.interfaces.buyer.BuyerService;
import com.hlhdidi.shop.interfaces.buyer.SessionProvider;
import com.hlhdidi.shop.pojo.user.Buyer;

@Controller
public class LoginController {
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private BuyerService buyerService;
	@Autowired
	private SessionProvider provider;
	@RequestMapping(value="/login.aspx",method=RequestMethod.GET)
	public String login(String resource,Model model) {
		model.addAttribute("resource",resource);
		return "login";
	}
	@RequestMapping(value="/login.aspx",method=RequestMethod.POST)
	public String login(String resource,String username,String password,Model model,
			HttpServletRequest request,HttpServletResponse response) {
		//实现登录
		if(username!=null) {
			if(password!=null) {
				String userId = redisTemplate.opsForValue().get(username);
				if(userId!=null) {
					//根据用户的id去存储的时候,使用Mycat效率会更好
					Buyer buyer = buyerService.findBuyerById(Integer.parseInt(userId));
					//buyer应该一定有.
//					对于密码进行编码,判断是否存在
					String encodingPassword=EncodingUtils.encodePassword(password);
					if(encodingPassword.equals(buyer.getPassword())) {
						//登录成功.
						//放入到Session中,这里采用全局Session,使用Redis作为缓存Session
						provider.setToGlobalSession(CookieUtils.getLoginCookieValue(request, response),
								username);//键为username
						//回跳到最初的页面
						//设置用户名的Cookie
						Cookie cookie=new Cookie("username", username);
						cookie.setPath("/");
						cookie.setMaxAge(60*600);
						response.addCookie(cookie);
						return "redirect:"+resource;
					}
					else {
						//登录失败.
						model.addAttribute("error","密码错误!");
					}
				}else {
					model.addAttribute("error", "用户名不存在!");
				}
			}
			else {
				model.addAttribute("error","密码不能为空!");
			}
		}
		else {
			model.addAttribute("error","用户名不能为空!");
		}
		return "login";
	}
	@RequestMapping("/isLogin.aspx")
	@ResponseBody
	public MappingJacksonValue isLogin(String callback,HttpServletRequest request,HttpServletResponse response) {
		String val = provider.getFromGlobalSession(CookieUtils.getLoginCookieValue(request, response));
		Integer result=0;
		if(val!=null) {
			result=1;
		}
		Map<Object,Object> map=new HashMap<>();
		map.put("result",result);
		String username = CookieUtils.findCookieUsername(request);
		map.put("username",username);
		MappingJacksonValue jacksonValue=new MappingJacksonValue(map);
		jacksonValue.setJsonpFunction(callback);
		return jacksonValue;
	}
}
