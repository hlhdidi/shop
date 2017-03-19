package com.hlhdidi.shop.commons.utils;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
	public static String getLoginCookieValue(HttpServletRequest request,HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if(cookies!=null) {
			for(Cookie cookie:cookies) {
				if(cookie.getName().equals("DSESSIONID")) {
					return cookie.getValue();
				}
			}
		}
		//说明没有对应的Cookie
		String sessionId = UUID.randomUUID().toString().replaceAll("-", "");
		Cookie cookie=new Cookie("DSESSIONID",sessionId);//bf783
		cookie.setMaxAge(-1);
		cookie.setPath("/");
		response.addCookie(cookie);
		return cookie.getValue();
	}
	public static String getCartCookieValue(HttpServletRequest request,HttpServletResponse response) {
		//获取request携带的cookie信息.
		Cookie[] cookies = request.getCookies();
		if(cookies!=null) {
			for(Cookie cookie:cookies) {
				if(cookie.getName().equals("cart")) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	public static String findCookieUsername(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if(cookies!=null) {
			for(Cookie cookie:cookies) {
				if(cookie.getName().equals("username")) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
}
