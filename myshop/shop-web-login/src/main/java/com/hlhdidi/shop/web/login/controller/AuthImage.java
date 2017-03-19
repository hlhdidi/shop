package com.hlhdidi.shop.web.login.controller;

import java.io.IOException;  

import javax.servlet.ServletException;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hlhdidi.shop.web.login.utils.VerifyCodeUtils;

/**
 * <p><b>AuthImage Description:</b> (验证码)</p>
 * <b>DATE:</b> 2016年6月2日 下午3:53:12
 */
@Controller
public class AuthImage {  
    @RequestMapping("/authImage")
    public void authImage(HttpServletResponse response,HttpServletRequest request) throws IOException {
    	 response.setHeader("Pragma", "No-cache");  
         response.setHeader("Cache-Control", "no-cache");  
         response.setDateHeader("Expires", 0);  
         response.setContentType("image/jpeg");  
           
         //生成随机字串  
         String verifyCode = VerifyCodeUtils.generateVerifyCode(4);  
         HttpSession session = request.getSession();  
         //删除以前的
         session.removeAttribute("verifyCode");
         session.setAttribute("verifyCode", verifyCode.toLowerCase());  
         //生成图片  
         int w = 100, h = 30;  
         VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
    }
} 