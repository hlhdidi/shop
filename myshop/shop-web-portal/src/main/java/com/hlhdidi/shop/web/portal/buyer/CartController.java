package com.hlhdidi.shop.web.portal.buyer;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.hlhdidi.shop.commons.utils.CookieUtils;
import com.hlhdidi.shop.interfaces.buyer.BuyerService;
import com.hlhdidi.shop.interfaces.buyer.SessionProvider;
import com.hlhdidi.shop.interfaces.product.SkuService;
import com.hlhdidi.shop.pojo.product.Sku;
import com.hlhdidi.shop.pojo.user.Cart;
import com.hlhdidi.shop.pojo.user.CartItem;

@RequestMapping("/portal/cart")
@Controller
public class CartController {
	
	@Autowired
	private SkuService skuService;
	@Autowired
	private BuyerService buyerService;
	@Autowired
	private SessionProvider sessionProvider;
	
	@RequestMapping("/addToCart")
	public String addToCart(String username,Boolean isLogin,
			Integer amount,Long skuId,HttpServletRequest request,HttpServletResponse response,Model model) {
		//加入到购物车.
		Cart cart=null;
		CartItem cartItem=new CartItem();
		Sku sku=skuService.findSkuById(skuId);
		cartItem.setSku(sku);
		cartItem.setAmount(amount);
		//判断是否登录
		if(isLogin!=false) {
			//已经登录了.
			//取出redis中存放的cart
			cart=buyerService.findCartFromRedis(username);
			if(cart==null) cart=new Cart();
			//判断是否有cookie.这样子做的原因是为了和未登录前的cookie合并.
			String cookieval = CookieUtils.getCartCookieValue(request, response);
			if(cookieval!=null) {
				//如果有cookie,合并,删除cookie
					Cart c=JSON.parseObject(cookieval, Cart.class);
					c=buyerService.convertCart(c);
					List<CartItem> items = c.getItems();
					for(CartItem item:items) {
						cart.addItemToCart(item);
					}
					Cookie cookie=new Cookie("cart",null);
					cookie.setPath("/");
					cookie.setMaxAge(0);//清空cookie
					response.addCookie(cookie);
			}
			//如果没有直接加入.
			cart.addItemToCart(cartItem);
			//将cart加入到redis中.
			buyerService.addCartToRedis(username,cart);
		}
		else {
			//还没有登录.
			//取出cookie.与cookie合并
			//没有cookie,新建一个cookie
			String cookieval = CookieUtils.getCartCookieValue(request, response);
			if(cookieval!=null) {
				//如果有cookie,在cookie中加入购物项
					cart=JSON.parseObject(cookieval, Cart.class);
					//这个cart里面缺少中文参数
					cart=buyerService.convertCart(cart);
			}
			else {
				cart=new Cart();	//如果没有Cookie.新建一个cart
			}
			//如果没有直接加入.
			cart.addItemToCart(cartItem);	//添加购物项.
			//转Json
			Cookie cookie=new Cookie("cart",JSON.toJSONString(cart));	//加入Cookie
			cookie.setPath("/");
			cookie.setMaxAge(6000*60);
			response.addCookie(cookie);
		}
		model.addAttribute("buyerCart", cart);
		return "cart";
	}
	@RequestMapping("/toCart")
	public String toCart(String username,Boolean isLogin,
			HttpServletRequest request,HttpServletResponse response,Model model) {
		Cart cart=null;
		//判断是否登录
		if(isLogin!=false) {
			//已经登录
			//已经登录了.
			//取出redis中存放的cart
			cart=buyerService.findCartFromRedis(username);
			if(cart==null) cart=new Cart();
			//判断是否有cookie.这样子做的原因是为了和未登录前的cookie合并.
			String cookieval = CookieUtils.getCartCookieValue(request, response);
			if(cookieval!=null) {
				//如果有cookie,合并,删除cookie
					Cart c=JSON.parseObject(cookieval, Cart.class);
					c=buyerService.convertCart(c);
					List<CartItem> items = c.getItems();
					for(CartItem item:items) {
						cart.addItemToCart(item);
					}
					Cookie cookie=new Cookie("cart",null);
					cookie.setPath("/");
					cookie.setMaxAge(0);//清空cookie
					response.addCookie(cookie);
					//将cart加入到redis中.
					buyerService.addCartToRedis(username,cart);
			}
		}
		else {
			//没有登录.
			//还没有登录.
			//取出cookie.与cookie合并
			//没有cookie,新建一个cookie
			String cookieval = CookieUtils.getCartCookieValue(request, response);
			if(cookieval!=null) {
				//如果有cookie,在cookie中加入购物项
					cart=JSON.parseObject(cookieval, Cart.class);
					//这个cart里面缺少中文参数
					cart=buyerService.convertCart(cart);
			}
			else {
				cart=new Cart();	//如果没有Cookie.新建一个cart
			}
		}
		model.addAttribute("buyerCart", cart);
		return "cart";
	}
}
