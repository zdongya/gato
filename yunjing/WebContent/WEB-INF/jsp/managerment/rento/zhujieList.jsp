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
    <em>
    租借记录
    </em>
    <ul>
    	<li>
    	<a onclick="javascript:history.go(-1)" href="javascript:void(0)">返回</a>
    	</li>
    </ul>
    </th>
  </tr>
  <tr class="title">
	<!-- <td>申请单号</td>  -->
	<td>申请人</td>
	<td>建材名称/单价(天) </td>
    <td>租借时间</td>
    <td>申请租借数量</td>
    <td>金额</td>
    <td>状态</td>
 
  </tr>
 <s:if test="null!=rentoList&&rentoList.size()>0">
 <s:iterator value="rentoList" id="rento">
  <tr>
    <!-- <td>${rento.id}</td> -->
    <td>${rento.member.username}</td>
    <td>${rento.material.name}/(${rento.price}元)</td>
    <td>${rento.rentTime}</td>
    <td>${rento.number}</td>
    <td>${rento.totalPrice}元</td>
    <td>
    <s:if test="#rento.status==3">已归还</s:if>
    <s:else>未归还</s:else>
    
    </td>
  </tr>
  </s:iterator>
  </s:if>
  <tr class="page">
    <td colspan="6">&nbsp;</td>
  </tr>
</table>
</form>
</body>
 <odb:pageController/>
</html>
