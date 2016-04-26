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

</head>


<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="dataList">
  <tr>
    <th colspan="6">
    <em>建材入库记录</em>
    <ul>
    	<li>
    	<a onclick="javascript:history.go(-1)" href="javascript:void(0)">返回</a>
    	</li>
    </ul>
    </th>
  </tr>
  <tr class="title">
	<td>入库单号 </td>
	<td>建材名称 </td>
	<td>所属类别</td>
    <td>入库时间</td>
    <td>入库数量</td>
    <td>目前库存</td>
  </tr>
 <s:if test="null!=proInList&&proInList.size()>0">
 <s:iterator value="proInList" id="proIn">
  <tr>
  	<td>${proIn.id} </td>
    <td>${proIn.material.name}</td>
    <td>${proIn.material.materialType.name}</td>
    <td>${proIn.createTime}</td>
    <td>${proIn.number}</td>
    <td>${proIn.material.inventory}</td>
  </tr>
  </s:iterator>
  </s:if>
  <tr class="page">
    <td colspan="6">&nbsp;</td>
  </tr>
</table>
<odb:pageController/>
</body>

</html>
