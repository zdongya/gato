<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="odb" uri="/odb"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=request.getContextPath()%>/css/default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.3.2.min.js"></script>
<title>page</title>
<script>
	function deleteMaterial(typeId){
		if(confirm("确定删除该建材吗？")){
			window.location.href="<%=request.getContextPath()%>/managerment/material_delete.html?materialId="+typeId;
		}
		
	}
	
	function search(){
		$("#searchForm").submit();
	}
</script>
</head>


<body>
<form action="<%=request.getContextPath()%>/zone/zone_index.html" method="post" id="searchForm">
<table class="searchTable">
     <tbody>
	     <tr>
		     <td width="10%" nowrap="nowrap" bgcolor="#f1f1f1" align="right">防区遍号：</td>
		     <td><input type="text" class=" span1-1" name="zone.zoneNo" value = "${zone.zoneNo}" /></td>
		     
		     <td width="10%" nowrap="nowrap" bgcolor="#f1f1f1" align="right">防区名称：</td>
		     <td><input type="text" class=" span1-1" name="zone.zoneName" value="${zone.zoneName}" /></td>
		     <td> <img src="<%=request.getContextPath()%>/images/search.png" onclick="javascript:search();"></img></td>
	     </tr>
     </tbody>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="dataList">
  <tr>
    <th colspan="9">
    <em>防区列表</em>
    
    </th>
  </tr>
  <tr class="title">

	<td>防区编号 </td>
	<td>防区名称</td>
	<td>防区描述</td>
    <td>防区联系人</td>
    <td>联系电话 </td>
    <td>防区地理位置</td>
    <td>操作</td>
  </tr>
 <s:if test="null!=zones&&zones.size()>0">
 <s:iterator value="zones" id="model">
  <tr>
    <td>${model.zoneNo}</td>
    <td>${model.zoneName}</td>
    <td>${model.zoneDesc}</td>
    <td>${model.zoneContactor}</td>
    <td>${model.zonePhone}</td>
    <td>${model.zoneLoc}</td>
    <td>
    <a href="javascript:void(0)">编辑</a>
    </td>
  </tr>
  </s:iterator>
  </s:if>

  <tr class="page">
    <td colspan="7">&nbsp;</td>
  </tr>
</table>
</form>
 <odb:pageController/>
</body>

</html>
