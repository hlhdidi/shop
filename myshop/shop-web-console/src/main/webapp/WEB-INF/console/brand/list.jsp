<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>品牌展示</title>
<script type="text/javascript"> 
function checkBox(name,checked) {
	$("input[name='"+name+"']").attr("checked",checked);
}
function optDelete() {
	var checks=$("input[name=ids]:checked");
	//至少选择一个
	if(checks.size()==0) {
		return;
	}
	//提示是否确认删除
	if(confirm("您确认要删除吗?")) {
		$("#toDelForm").submit();
	}		
}
</script>
<link rel="stylesheet" href="/css/style.css" />
</head>
<body>
<div class="box-positon">
	<div class="rpos">当前位置: 品牌管理 - 列表</div>
	<form class="ropt">
		<input class="add" type="button" value="添加" onclick="javascript:window.location.href='${pageContext.request.contextPath}/console/brand/addUI'"/>
	</form>
	<div class="clear"></div>
</div>
<div class="body-box">
<form action="${pageContext.request.contextPath }/console/brand/list.action" method="post" style="padding-top:5px;">
品牌名称: <input type="text" name="name" value="${name}"/>
	<select name="isDisplay">
		<option value="1" ${isDisplay==1?"selected":"" }>是</option>
		<option value="0" ${isDisplay==0?"selected":"" }>否</option>
	</select>
	<input type="submit" class="query" value="查询"/>
</form>
<form action="${pageContext.request.contextPath }/console/brand/deleteIds" method="post" id="toDelForm">
<table cellspacing="1" cellpadding="0" border="0" width="100%" class="pn-ltable">
	<thead class="pn-lthead">
		<tr>
			<th width="20"><input type="checkbox" onclick="checkBox('ids',this.checked)"/></th>
			<th>品牌ID</th>
			<th>品牌名称</th>
			<th>品牌图片</th>
			<th>品牌描述</th>
			<th>排序</th>
			<th>是否可用</th>
			<th>操作选项</th>
		</tr>
	</thead>
	<tbody class="pn-ltbody">
		<c:forEach items="${page.list }" var="b">
		<tr bgcolor="#ffffff" onmouseout="this.bgColor='#ffffff'" onmouseover="this.bgColor='#eeeeee'">
			<td><input type="checkbox" value="${b.id }" name="ids"/></td>
			<td align="center">${b.id }</td>
			<td align="center">${b.name }</td>
			<td align="center"><img width="40" height="40" src="${b.imgUrl }"/></td>
			<td align="center">${b.description }</td>
			<td align="center">${b.sort }</td>
			<td align="center">
				<c:if test="${b.isDisplay==1 }">是</c:if>
				<c:if test="${b.isDisplay==0 }">否</c:if>
			</td>
			<td align="center">
			<a class="pn-opt" href="${pageContext.request.contextPath }/console/brand/editUI/${b.id}">修改</a> | <a class="pn-opt" onclick="if(!confirm('您确定删除吗？')) {return false;}" href="${pageContext.request.contextPath }/console/brand/delete/${b.id}">删除</a>
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
</form>
<div class="page pb15">
	<span class="r inb_a page_b">
<!-- 分页条展示. -->
 <c:forEach items="${page.pageView}" var="view">
 	${view }
 </c:forEach>
 </span>
</div>
<div style="margin-top:15px;"><input class="del-button" type="button" value="删除" onclick="optDelete();"/></div>
</div>
</body>
</html>