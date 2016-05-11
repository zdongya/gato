<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="odb" uri="/odb"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.3.2.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=request.getContextPath()%>/css/default.css" rel="stylesheet" type="text/css" />
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/My97DatePicker/WdatePicker.js"></script>
<title>设备查询</title>
<script>
function search(){
	$("#searchForm").submit();
}

function exportExcel(){
	var action = $("#searchForm").attr("action");
	var exportAction = "<%=request.getContextPath()%>/device/device_exportExcel.html";
	$("#searchForm").attr("action", exportAction);
	$("#searchForm").submit();
	$("#searchForm").attr("action", action);
}

function queryDeviceZones(deviceNo, deviceName){
	var pageUrl = "<%=request.getContextPath()%>/device/device_queryZones.html?deviceNo=" + deviceNo + "&deviceName=" + encodeURI(encodeURI(deviceName));
	window.open(pageUrl, "newwindow", "height=600, width=1000, toolbar= no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
}

function queryDeviceUsers(deviceNo, deviceName){
	var pageUrl = "<%=request.getContextPath()%>/device/device_queryUsers.html?deviceNo=" + deviceNo + "&deviceName=" + encodeURI(encodeURI(deviceName));
	window.open(pageUrl, "newwindow", "height=600, width=1000, toolbar= no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
}
</script>
</head>


<body>
<form action="<%=request.getContextPath()%>/device/device_query.html" method="post" id="searchForm">
<table class="searchTable">
     <tbody>
	     <tr>
	     	 <td width="10%" nowrap="nowrap" bgcolor="#f1f1f1" align="right">注册时间从</td>
		     <td width="16%"><input class="Wdate"  type="text" onClick="WdatePicker()" name="device.beginDate" value="${device.beginDate}"/></td>
		     <td width="10%" nowrap="nowrap" bgcolor="#f1f1f1" align="right">到</td>
		     <td width="16%"><input class="Wdate"  type="text" onClick="WdatePicker()" name="device.endDate" value="${device.endDate}"/></td>
		    
		     <td width="10%" nowrap="nowrap" bgcolor="#f1f1f1" align="right">序列号：</td>
		     <td><input type="text" class=" span1-1" name="device.deviceNo" value = "${device.deviceNo}" /></td>
		     <td width="10%" nowrap="nowrap" bgcolor="#f1f1f1" align="right">设备名称：</td>
		     <td><input type="text" class=" span1-1" name="device.deviceName" value="${device.deviceName}" /></td>
		     <td> <img src="<%=request.getContextPath()%>/images/search.png" onclick="javascript:search();"></img></td>
		     <td> <input type="button" value="导出excel" onclick="javascript:exportExcel();" ></input></td>
	     </tr>
     </tbody>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="dataList">
  <tr>
    <th colspan="5">
    <em>设备列表</em>
    
    </th>
  </tr>
  <tr class="title">

	<td>设备序列号 </td>
	<td>设备名称</td>
	<td>注册时间 </td>
    <td>版本</td>
    <td>操作</td>
  </tr>
 <s:if test="null!=devices&&devices.size()>0">
 <s:iterator value="devices" id="model">
  <tr>
    <td>${model.deviceNo}</td>
    <td>${model.deviceName}</td>
    <td>${model.addDate}</td>
    <td>${model.version}</td>
    <td>
	    <a href="javascript:queryDeviceUsers('${model.deviceNo}','${model.deviceName}');">绑定用户列表</a>&nbsp;&nbsp;
	    <a href="javascript:queryDeviceZones('${model.deviceNo}','${model.deviceName}');">防区列表</a>
    </td>
  </tr>
  </s:iterator>
  </s:if>
  <tr class="page">
    <td colspan="5">&nbsp;</td>
  </tr>

</table>
</form>
 <odb:pageController/>
</body>

</html>
