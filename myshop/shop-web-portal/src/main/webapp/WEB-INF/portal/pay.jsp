<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>订单结算页 -新巴巴商城</title>
<!--结算页面样式-->
<link rel="stylesheet" type="text/css" href="/css/base.css" media="all" />
<script type="text/javascript" src="/js/jquery-1.6.4.js"></script>
<script type="text/javascript" src="/js/base.js"></script>
<link type="text/css" rel="stylesheet" href="/css/a_002.css"
	source="widget">
	<link type="text/css" rel="stylesheet" href="/css/a.css">
</head>

<body id="mainframe">
	<!-- 引入header.jsp -->
	<jsp:include page="commons/shortcut.jsp" />
	<div class="container"><h1>在线支付页面</h1></div>
	<h2>请在30分钟内选择您的银行并且完成支付!</h2>
	<div class="container" style="text-align: center">
		<div>
			<hr />
			<form id="orderForm" action="${pageContext.request.contextPath }/portal/order/pay" method="post" class="form-horizontal" style="margin-top: 5px; margin-left: 150px;">
				
				<!-- 提交oid -->
				<input type="hidden" name="orderid" value="${orderId }">
				<input type="hidden" name="isLogin" value="${isLogin }">
				<input type="hidden" name="username" value="${username }">
			<hr />
			<div style="text-align: right; margin-right: 120px;">
				您需要支付的金额: <strong style="color: #ff6600;">￥${orderMoney }元
				<input type="hidden" name="money" value="${orderMoney }" />
				</strong>
			</div>
			<div style="margin-top: 5px; margin-left: 150px;">
				<strong>选择银行：</strong>
				<p>
					<br /> 
						<input type="radio" name="pd_FrpId" value="ICBC-NET-B2C" checked="checked" />
							工商银行 <img src="/bank_img/icbc.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
					
					 <input type="radio"
						name="pd_FrpId" value="BOC-NET-B2C" />中国银行 <img
						src="/bank_img/bc.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="pd_FrpId" value="ABC-NET-B2C" />农业银行 <img
						src="/bank_img/abc.bmp" align="middle" /> <br /> <br /> <input
						type="radio" name="pd_FrpId" value="BOCO-NET-B2C" />交通银行 <img
						src="/bank_img/bcc.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="pd_FrpId" value="PINGANBANK-NET" />平安银行
					<img src="/bank_img/pingan.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="pd_FrpId" value="CCB-NET-B2C" />建设银行 <img
						src="/bank_img/ccb.bmp" align="middle" /> <br /> <br /> <input
						type="radio" name="pd_FrpId" value="CEB-NET-B2C" />光大银行 <img
						src="/bank_img/guangda.bmp" align="middle" />&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="radio" name="pd_FrpId" value="CMBCHINA-NET-B2C" />招商银行
					<img src="/bank_img/cmb.bmp" align="middle" />

				</p>
				<hr />
				<p style="text-align: right; margin-right: 100px;">
					<a href="javascript:document.getElementById('orderForm').submit();">
						<img src="/images/regist-btn.jpg" width="204" height="51"
						border="0" />
					</a>
				</p>
				<hr />

			</div>
			
			</form>
			
		</div>

	</div>

	<!-- 引入footer.jsp -->
	<jsp:include page="commons/footer.jsp" />

</body>

</html>