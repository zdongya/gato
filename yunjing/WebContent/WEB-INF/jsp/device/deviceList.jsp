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
    <th colspan="9">
    <em>设备列表</em>
    
    </th>
  </tr>
  <tr class="title">

	<td>设备序列号 </td>
	<td>设备名称</td>
	<td>设备地址</td>
    <td>版本</td>
    <td>设备用户名</td>
    <td>联系人</td>
    <td>联系电话</td>
    <td>地址</td>
  </tr>
 <s:if test="null!=devices&&devices.size()>0">
 <s:iterator value="devices" id="device">
  <tr>
    <td>${device.deviceNo}</td>
    <td>${device.deviceName}</td>
    <td>${device.deviceLocal}</td>
    <td>${device.version}</td>
    <td>${device.deviceUserName}</td>
    <td>${device.contactPerson}</td>
    <td>${device.cellphone}</td>
    <td>${device.address}</td>
  </tr>
  </s:iterator>
  </s:if>

  <tr class="page">
    <td colspan="8">&nbsp;</td>
  </tr>
</table>
</form>
 <odb:pageController/>
</body>

</html>
