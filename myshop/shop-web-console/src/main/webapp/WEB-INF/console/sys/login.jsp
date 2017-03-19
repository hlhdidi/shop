<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div style="background-color: white;background-image:url('/images/loginbg.jpg');
	background-repeat:no-repeat;background-attachment:fixed;width: 1150px;height: 500px;
	background-size:100% 100%;padding-top: 300px;padding-left: 400px">
		<form action="${pageContext.request.contextPath }/j_spring_security_check" method="post">
		<b>用户名:</b><input type="text" name="j_username" ><br><br>
		<b>密码:</b><input type="text" name="j_password"><br><br>
		<input type="submit" value="提交">
		</form>
	</div>
</body>
</html>