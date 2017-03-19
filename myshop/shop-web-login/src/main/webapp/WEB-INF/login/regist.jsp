<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="Cache-Control" content="no-cache,must-revalidate" />
    <title>欢迎注册</title>
    <link type="text/css" rel="stylesheet"  href="${pageContext.request.contextPath }/css/ui-base.css"/>
        <script type="text/javascript">window.pageConfig = { compatible: true }; </script>
         <script type="text/javascript" src="${pageContext.request.contextPath }/js/basic.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.6.4.js"></script>
     <script>
        seajs.off("load");
        seajs.off("fetch");
        seajs.data.charset = 'UTF-8';
    </script>
    <link type="text/css" rel="stylesheet"  href="${pageContext.request.contextPath }/css/header-1130.css" source="widget"/>
    </head>
<body>
    
<div id="form-header" class="header">
    <div class="logo-con w clearfix">
        <div class="logo-title">欢迎注册本网站!</div>
        <div class="have-account">已有账号?<a href="http://127.0.0.1:8580/login.aspx">请登录</a></div>
    </div>

</div>
<div class="container w">

    <div class="main clearfix" id="form-main">
        <div class="reg-form fl">
            <form action="${pageContext.request.contextPath }/regist" id="register-form" method="post">
                <div class="form-item form-item-account" id="form-item-account">
                    <label>用 户 名</label>
                    <input type="text" id="form-username" name="username" class="field" autocomplete="off" maxlength="30"
                    onblur="checkUserName()"   placeholder="您的账户名和登录名" default='<i class="i-def"></i>支持中文、字母、数字、“-”“_”的组合，4-20个字符' />
                    <i class="i-status">
                    </i>
                    	<span style="display: none;" id="errorUsername"><img src="${pageContext.request.contextPath }/images/success.jpg"></img></span>
                    	<span style="display: none;" id="correctUsername"><img src="${pageContext.request.contextPath }/images/error.png"></img></span>
                </div>
                <div class="input-tip">
                    <span></span>
                </div>
                <div class="form-item">
                    <label>设 置 密 码</label>
                    <input type="password" name="password" id="form-pwd" class="field" maxlength="20"
                           placeholder="建议使用两种字符组合" default="<i class=i-def></i>建议使用字母、数字和符号两种及以上的组合，6-20个字符" />
                    <i class="i-status"></i>
                </div>
                <div class="input-tip">
                    <span></span>
                </div>
                 <div class="item-email-wrap">
                        <div class="form-item">
                            <label>常 用 邮 箱</label>
                            <input type="text" name="emailhidden" id="form-emailhide" class="field ignore" autocomplete="off"
                                   placeholder="建议使用常用邮箱" id="email-input"
                                   default='<i class="i-def"完成验证后，你可以用该邮箱登录和找回密码' />
                            <i class="i-status"></i>
                        </div>
                        <div class="input-tip">
                            <span></span>
                        </div>
                           <div class="orPhone"><a href="javascript:;">手机验证</a></div>
                    </div>
                                <div class="item-phone-wrap">
                                            <div class="form-item form-item-phone">
                            <label class="select-country" id="select-mail" country_id="mail">常用邮箱:<a href="javascript:void(0) " tabindex="-1" class="arrow"></a></label>
                            <input type="text" id="form-email" name="email" class="field" placeholder="输入您的邮箱接收激活邮件."
                                   autocomplete="off" maxlength="20" default='<i class="i-def"></i>完成邮箱激活,激发更多功能.' />
                            <i class="i-status"></i>
                        </div>
                                        <div class="input-tip">
                        <span></span>
                  		  </div>
                                    </div>
                                <div class="item-phone-wrap">
                                            <div class="form-item form-item-phone">
                            <label class="select-country" id="select-country" country_id="0086">手机号码:<a href="javascript:void(0) " tabindex="-1" class="arrow"></a></label>
                            <input type="text" id="form-phone" name="phone" class="field" placeholder="建议使用常用手机"
                                   autocomplete="off" maxlength="11" default='<i class="i-def"></i>完成验证后，你可以用该手机登录和找回密码' />
                            <i class="i-status"></i>
                        </div>
                                        <div class="input-tip">
                        <span></span>
                  		  </div>
                                    </div>
                                    <div class="form-item form-item-authcode">
                        <label>验　证　码</label>
                        <input type="text" autocomplete="off" name="authCode" id="authCode" maxlength="6" class="field form-authcode"
                               placeholder="请输入验证码" default='<i class="i-def"></i>看不清？点击图片更换验证码' />
                        <img class="img-code" title="验证码" id="imgAuthCode" src="${pageContext.request.contextPath }/authImage"
                             onclick="javascript:changeImg()"
                             ver_colorofnoisepoint="#888888"/><span><font color="red">${verifyError }</font></span>
                    </div>
                    <div class="input-tip">
                        <span></span>
                    </div>
                                                    <div class="form-item form-item-phonecode">
                        <label>手机验证码</label>
                        <input type="text" name="mobileCode" maxlength="6" id="phoneCode" class="field phonecode"
                               placeholder="请输入手机验证码" autocomplete="off"/>
                        <button id="getPhoneCode" class="btn-phonecode" type="button" onclick="javascript:timerPhoneCode()">»获取验证码</button>
                        <i class="i-status"></i>
                    </div>
                    <div class="input-tip">
                        <span> <font color="red">${error }</font></span>
                    </div>
                     <div>
                    <button type="submit" class="btn-register">立即注册</button>
                </div>
                
            </form>
        </div>
    </div>
</div>
<div id="form-footer" class="footer w">
    <div class="links">
        <a rel="nofollow" target="_blank" href="//www.jd.com/intro/about.aspx">关于我们</a>|
        <a rel="nofollow" target="_blank" href="//www.jd.com/contact/">联系我们</a>|
        <a rel="nofollow" target="_blank" href="//zhaopin.jd.com/">人才招聘</a>|
      	<a rel="nofollow" target="_blank" href="//www.jd.com/contact/joinin.aspx">商家入驻</a>|
        <a rel="nofollow" target="_blank" href="//www.jd.com/intro/service.aspx">广告服务</a>|
        <a rel="nofollow" target="_blank" href="//app.jd.com/">谢谢联系</a>|
        <a target="_blank" href="//club.jd.com/links.aspx">友情链接</a>|
        <a target="_blank" href="//media.jd.com">胡龙联盟</a>|
        <a href="//club.jd.com/" target="_blank">胡龙社区</a>|
        <a href="//gongyi.jd.com" target="_blank">胡龙公益</a>|
        <a target="_blank" href="//en.jd.com/" clstag="pageclick|keycount|20150112ABD|9">English Site</a>
    </div>
    <div class="copyright">
        Copyright&copy;2004-2016&nbsp;&nbsp;胡龙网&nbsp;版权所有
    </div>
</div>


<script type="text/javascript">
    var localmisc = $("#localmisc");
    if(1==localmisc.val()){
        seajs.use('../misc2016/js/localRegister', function (reg) {
            reg.init();
        })
    }else{
        seajs.use('//misc.360buyimg.com/user/reg/1.0.0/js/register.1130', function (reg) {
            reg.init();
        })
    }
</script>
<script type="text/javascript" src="//seq.jd.com/jseqf.html?bizId=JD_register_pc&platform=js&version=1"></script>
        <script src="//payrisk.jd.com/js/td.js"></script>
    <script type="text/javascript">
    var i=60; 
    var temp;
    $(function () {
            getJdEid(function (eid, fpid) {
                $("#eid").val(eid);
                $("#sessionId").val(fpid);
            });
        });
        function changeImg(){
    		var img = document.getElementById("imgAuthCode");  
    		img.src = "${pageContext.request.contextPath}/authImage?date=" + new Date();;
    	} 
        function timerPhoneCode(){
        	var phone=$("#form-phone").val();
        	//检验是否满足phone的格式!
        	if(!(/^1[0-9][0-9]\d{4,8}$/.test(phone))){ 
                alert("请输入正确的手机号码!"); 
                return; 
            } 
        	temp=window.setInterval("timer()", 1000);
        	//发送手机验证码
        	$.ajax({
       		 	type: "POST",
       	   		url: "${pageContext.request.contextPath}/sendPhoneCode.aspx",
       	   		data:{"phone":phone},
       	   		success: function(msg){
       	  		 },
				dataType:"json"
       		}); 
        }
        function timer(){
        	if(i>=0){
        		$("#getPhoneCode").html("还有"+(i--)+"秒");
        	}
        	else {
        		window.clearInterval(temp);
        		$("#getPhoneCode").html("点我重新发送手机验证码!");
        	} 
        }
        function checkUserName() {
        	var username=$("#form-username").val();
        	$.ajax({
       		 	type: "POST",
       	   		url: "${pageContext.request.contextPath}/checkUsernameExists.aspx",
       	   		data:{"username":username},
       	   		success: function(msg){
       	  			if(msg.result=="yes") {
       	  				//用户名存在
       	  				$("#errorUsername").hide();
       	  				$("#correctUsername").show();
       	  			} 
       	  			else {
       	  				//用户名不存在.
       	  				$("#correctUsername").hide();
   	  					$("#errorUsername").show();
       	  			}
       	   		},
				dataType:"json"
       		}); 
        }
    </script>
    <a target="_blank" href="//surveys.jd.com/index.php?r=survey/index/sid/447941/lang/zh-Hans" class="feedback"
   style="margin-top: -85px; position: fixed; right: 0px; bottom: 50%;"></a>
    </body>
</html>