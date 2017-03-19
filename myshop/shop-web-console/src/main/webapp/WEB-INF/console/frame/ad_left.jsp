<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="/css/admin.css" rel="stylesheet" type="text/css"/>
<link href="/css/theme.css" rel="stylesheet" type="text/css"/>
<link href="/css/jquery.validate.css" rel="stylesheet" type="text/css"/>
<link href="/css/jquery.treeview.css" rel="stylesheet" type="text/css"/>
<link href="/css/jquery.ui.css" rel="stylesheet" type="text/css"/>

<script src="/js/jquery.js" type="text/javascript"></script>
<script src="/js/jquery.ext.js" type="text/javascript"></script>
<script src="/js/jquery.form.js" type="text/javascript"></script>
<script src="/js/itcast.js" type="text/javascript"></script>
<script src="/js/admin.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	$("#browser").treeview({
		url : "/console/position/tree"
	});
})
</script>
</head>
<body>
<div class="left">
	<%@ include file="../date.jsp" %>
	<div class="fresh">
		 <table width="100%" border="0" cellspacing="0" cellpadding="0">
	          <tr>
	            <td height="35" align="center"><img src="/images/admin/refresh-icon.png" />&nbsp;&nbsp;<a href="javascript:location.href=location.href">刷新</a></td>
	      	 </tr>
	     </table>
	</div>
</div>
<ul class="filetree treeview" id="browser">
</ul>
</div>
</body>
</html>