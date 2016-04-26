<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="odb" uri="/odb"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=request.getContextPath()%>/css/default.css" rel="stylesheet" type="text/css" />
<title>page</title>
<script>
	function deleteMaterial(typeId){
		if(confirm("确定删除该建材吗？")){
			window.location.href="<%=request.getContextPath()%>/managerment/material_delete.html?materialId="+typeId;
		}
		
	}
</script>
</head>


<body>
<form>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="dataList">
  <tr>
    <th colspan="6">
    <em>建材列表</em>
    <ul>
    	<li><a href="<%=request.getContextPath()%>/managerment/material_toAdd.html">新增建材</a></li>
    </ul>
    </th>
  </tr>
  <tr class="title">

	<td>建材名称 </td>
	<td>租赁价格</td>
	<td>所属类别</td>
    <td>创建时间</td>
    <td>库存</td>
    <td>操作</td>
  </tr>
 <s:if test="null!=materials&&materials.size()>0">
 <s:iterator value="materials" id="material">
  <tr>
 
    
    <td>${material.name}</td>
    <td>${material.price}元/每天</td>
    <td>${material.materialType.name}</td>
    <td>${material.createTime}</td>
    <td>${material.inventory}</td>
    <td>
    	<a href="<%=request.getContextPath()%>/managerment/proIn_toAdd.html?materialId=${material.id}" >入库</a>
    	&nbsp;&nbsp;<a href="<%=request.getContextPath()%>/managerment/material_toEdit.html?materialId=${material.id}">编辑</a>
    	&nbsp;&nbsp;<a href="#" onclick="javascript:deleteMaterial(${material.id});" >删除</a>
    </td>
  </tr>
  </s:iterator>
  </s:if>

  <tr class="page">
    <td colspan="6">&nbsp;</td>
  </tr>
</table>
</form>
 <odb:pageController/>
</body>

</html>
