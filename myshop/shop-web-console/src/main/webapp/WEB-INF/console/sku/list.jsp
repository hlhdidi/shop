<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../head.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>babasport-list</title>
<script>
	function updateSku(id) {
		//m p i l f
		$("#m"+id).attr("disabled",false);
		$("#p"+id).attr("disabled",false);
		$("#i"+id).attr("disabled",false);
		$("#l"+id).attr("disabled",false);
		$("#f"+id).attr("disabled",false);
	}
	function addSku(id) {
		var m=$("#m"+id).attr("disabled",true).val();
		var p=$("#p"+id).attr("disabled",true).val();
		var i=$("#i"+id).attr("disabled",true).val();
		var l=$("#l"+id).attr("disabled",true).val();
		var f=$("#f"+id).attr("disabled",true).val();
		$.post("${pageContext.request.contextPath}/console/sku/update",{
			"marketPrice":m,
			"price":p,
			"stock":i,
			"upperLimit":l,
			"deliveFee":f,
			"id":id
		},
				function(data) {
					if(data.mes=="success") {
						alert("更新成功!");
					}
		},"json");
	}
</script>
</head>
<body>
<div class="box-positon">
	<div class="rpos">当前位置: 库存管理 - 列表</div>
	<div class="clear"></div>
</div>
<div class="body-box">
<form method="post" id="tableForm">
<table cellspacing="1" cellpadding="0" border="0" width="100%" class="pn-ltable">
	<thead class="pn-lthead">
		<tr>
			<th width="20"><input type="checkbox" onclick="Pn.checkbox('ids',this.checked)"/></th>
			<th>商品编号</th>
			<th>商品颜色</th>
			<th>商品尺码</th>
			<th>市场价格</th>
			<th>销售价格</th>
			<th>库       存</th>
			<th>购买限制</th>
			<th>运       费</th>
			<th>是否赠品</th>
			<th>操       作</th>
		</tr>
	</thead>
	<tbody class="pn-ltbody">
			<c:forEach items="${skus }" var="s">
			<tr bgcolor="#ffffff" onmouseover="this.bgColor='#eeeeee'" onmouseout="this.bgColor='#ffffff'">
				<td><input type="checkbox" name="ids" value="${s.id }"/></td>
				<td>${s.id }</td>
				<td align="center">${s.color.name }</td>
				<td align="center">${s.size }</td>
				<td align="center"><input type="text" id="m${s.id }" value="${s.marketPrice }" disabled="disabled" size="10"/></td>
				<td align="center"><input type="text" id="p${s.id }" value="${s.price }" disabled="disabled" size="10"/></td>
				<td align="center"><input type="text" id="i${s.id }" value="${s.stock }" disabled="disabled" size="10"/></td>
				<td align="center"><input type="text" id="l${s.id }" value="${s.upperLimit }" disabled="disabled" size="10"/></td>
				<td align="center"><input type="text" id="f${s.id }" value="${s.deliveFee }" disabled="disabled" size="10"/></td>
				<td align="center">不是</td>
				<td align="center"><a href="javascript:updateSku(${s.id })" class="pn-opt">修改</a> | <a href="javascript:addSku(${s.id })" class="pn-opt">保存</a></td>
			</tr>
			</c:forEach>
	</tbody>
</table>
</form>
</div>
</body>
</html>