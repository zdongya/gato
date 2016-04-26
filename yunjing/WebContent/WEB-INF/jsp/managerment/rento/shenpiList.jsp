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
function agree(rentoId,status,number){
	window.location.href="<%=request.getContextPath()%>/managerment/rento_shenpi.html?rentoId="+rentoId
	+"&status="+status+"&number="+number;
}
</script>
</head>


<body>
<form>
<table width="120%" border="0" cellpadding="0" cellspacing="0" class="dataList">
  <tr>
    <th colspan="6">
    <em>
    <s:if test="status==0">待审批租借申请</s:if>
    <s:if test="status==2">待审批归还申请</s:if>
    </em>
    <ul>
    	<li>
    	<a onclick="javascript:history.go(-1)" href="javascript:void(0)">返回</a>
    	</li>
    </ul>
    </th>
  </tr>
  <tr class="title">
  <!-- 
	<td>申请单号</td>
	 -->
	<td>申请人号</td>
	<td>建材名称/单价(天) </td>
    <td>申请时间</td>
    <td>
    <s:if test="status==0">申请租借数量</s:if><s:if test="status==2">申请归还数量/金额</s:if>
    </td>
    <td>目前库存</td>
    <td>操作</td>
  </tr>
 <s:if test="null!=rentoList&&rentoList.size()>0">
 <s:iterator value="rentoList" id="rento">
  <tr>
 
    
    <!-- <td>${rento.id}</td> -->
    <td>${rento.member.username}</td>
    <td>${rento.material.name}/(${rento.price}元)</td>
    <td>${rento.material.createTime}</td>
    <td>${rento.number}<s:if test="status==2">/(${rento.totalPrice}元)</s:if></td>
    <td>${rento.material.inventory}</td>
    <td>
    	<s:if test="status==0">
	    	<s:if test="(#rento.material.inventory)>=(#rento.number)">
	    	<a href="#" onclick="javascript:agree(${rento.id},1,${rento.number})" >同意租借</a>
	    	</s:if>
    		<a href="#" onclick="javascript:agree(${rento.id},-1,${rento.number})" >不同意租借</a>
    	</s:if>
    	
    	<s:if test="status==2">
    	<a href="#" onclick="javascript:agree(${rento.id},3,${rento.number})" >同意归还</a>
    	<a href="#" onclick="javascript:agree(${rento.id},-2,${rento.number})" >不同意归还</a>
    	</s:if>
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
