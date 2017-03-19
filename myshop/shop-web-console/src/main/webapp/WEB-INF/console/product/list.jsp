<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>babasport-list</title>
<script type="text/javascript">
//上架
function isShow(){
	//请至少选择一个
	var size = $("input[name='ids']:checked").size();
	if(size == 0){
		alert("请至少选择一个");
		return;
	}
	//你确定删除吗
	if(!confirm("你确定上架吗")){
		return;
	}
	//提交 Form表单
	$("#jvForm").attr("action","/console/product/isShow?name=${name}&brandId=${brandId}&isShow=${isShow}&pageNo=${pageNo}");
	$("#jvForm").attr("method","post");
	$("#jvForm").submit();
	
}
</script>
</head>
<body>
<div class="box-positon">
	<div class="rpos">当前位置: 商品管理 - 列表</div>
	<form class="ropt">
		<input class="add" type="button" value="添加" onclick="window.location.href='${pageContext.request.contextPath}/console/product/addUI'"/>
	</form>
	<div class="clear"></div>
</div>
<div class="body-box">
<form action="${pageContext.request.contextPath }/console/product/productList" method="post" style="padding-top:5px;">
名称: <input type="text" name="name" value="${name }"/>
	<select name="brandId">
		<option value="">请选择品牌</option>
		<c:forEach items="${brands}" var="b">
			<option value="${b.id }" ${brandId==b.id?"selected":"" }>${b.name }</option>
		</c:forEach>
	</select>
	<select name="isShow">
		<option value="1" ${isShow==true?"selected":"" }>上架</option>
		<option selected="selected" value="0" ${isShow==false?"selected":"" }>下架</option>
	</select>
	<input type="submit" class="query" value="查询"/>
</form>
<form id="jvForm">
<table cellspacing="1" cellpadding="0" width="100%" border="0" class="pn-ltable">
	<thead class="pn-lthead">
		<tr>
			<th width="20"><input type="checkbox" onclick="Pn.checkbox('ids',this.checked)"/></th>
			<th>商品编号</th>
			<th>商品名称</th>
			<th>图片</th>
			<th width="4%">新品</th>
			<th width="4%">热卖</th>
			<th width="4%">推荐</th>
			<th width="4%">上下架</th>
			<th width="12%">操作选项</th>
		</tr>
	</thead>
	<tbody class="pn-ltbody">
		<c:forEach items="${page.list }" var="p">
		<tr bgcolor="#ffffff" onmouseover="this.bgColor='#eeeeee'" onmouseout="this.bgColor='#ffffff'">
			<td><input type="checkbox" name="ids" value="${p.id }"/></td>
			<td>${p.id }</td>
			<td align="center">${p.name }</td>
			<td align="center"><img width="50" height="50" src="${p.firstImg }"/></td>
			<td align="center">${p.isNew==true?"新品":"常规" }</td>
			<td align="center">${p.isHot==true?"是":"否" }</td>
			<td align="center">${p.isCommend==true?"是":"否" }</td>
			<td align="center">${p.isShow==true?"上架":"下架" }</td>
			<td align="center">
			<a href="#" class="pn-opt">查看</a> | <a href="#" class="pn-opt">修改</a> | <a href="#" onclick="if(!confirm('您确定删除吗？')) {return false;}" class="pn-opt">删除</a> | 
			<a href="${pageContext.request.contextPath }/console/sku/editSku/${p.id}" class="pn-opt">库存</a>
			</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
<div class="page pb15">
	<span class="r inb_a page_b">
	<c:forEach items="${page.pageView }" var="view">
			${view }
		</c:forEach>
	</span>
</div>
<div style="margin-top:15px;"><input class="del-button" type="button" value="删除" onclick="optDelete();"/><input class="add" type="button" value="上架" onclick="isShow();"/><input class="del-button" type="button" value="下架" onclick="isHide();"/></div>
</form>
</div>
</body>
</html>