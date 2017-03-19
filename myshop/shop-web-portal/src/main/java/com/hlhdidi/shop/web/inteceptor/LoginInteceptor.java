package com.hlhdidi.shop.web.inteceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInteceptor implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		String isLogin = request.getParameter("isLogin");
		if(isLogin.equals("true")) {
			//登录放行
			return true;
		}
		else {
			//不放行
			//踢到登录页面.
			response.sendRedirect("http://127.0.0.1:8580/login.aspx?resource=http://127.0.0.1:8680/portal/index");
			return false;
		}
	}

}
