<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="odb" uri="/odb"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.3.2.min.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/My97DatePicker/WdatePicker.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=request.getContextPath()%>/css/default.css" rel="stylesheet" type="text/css" />
<title>日志查询</title>
<script>
function search(){
	$("#searchForm").submit();
}

function exportExcel(){
	var action = $("#searchForm").attr("action");
	var exportAction = "<%=request.getContextPath()%>/log/log_exportExcel.html";
	$("#searchForm").attr("action", exportAction);
	$("#searchForm").submit();
	$("#searchForm").attr("action", action);
}
</script>
</head>


<body>
<form action="<%=request.getContextPath()%>/log/log_index.html" method="post" id="searchForm">
<table class="searchTable">
     <tbody>
	     <tr>
		     <td width="10%" nowrap="nowrap" bgcolor="#f1f1f1" align="right">用户昵称：</td>
		     <td><input type="text" class="span1-1" name="log.nname" value="${log.nname}"  /> </td>
		     
		      <td width="10%" nowrap="nowrap" bgcolor="#f1f1f1" align="right">操作时间从</td>
		     <td width="10%"><input class="Wdate"  type="text" onClick="WdatePicker()" name="log.beginDate" value="${log.beginDate}"/></td>
		     <td width="10%" nowrap="nowrap" bgcolor="#f1f1f1" align="right">到</td>
		     <td width="10%"><input class="Wdate"  type="text" onClick="WdatePicker()" name="log.endDate" value="${log.endDate}"/></td>
		     
		     <td width="10%" nowrap="nowrap" bgcolor="#f1f1f1" align="right">操作类型：</td>
		     <td>
		     <select name="log.operatorType" >
		          <option value =""  <s:if test='log.operatorType==""'>selected="selected"</s:if> >--请选择--</option>
				  <option value ="1" <s:if test='log.operatorType=="1"'>selected="selected"</s:if> >报警处理</option>
				  <option value ="2" <s:if test='log.operatorType=="2"'>selected="selected"</s:if> >布防</option>
				  <option value ="3" <s:if test='log.operatorType=="3"'>selected="selected"</s:if> >撤防</option>
			</select>
		     
		     </td>
		    
		     <td> <img src="<%=request.getContextPath()%>/images/search.png" onclick="javascript:search();"></img></td>
		     <td> <input type="button" value="导出excel" onclick="javascript:exportExcel();" ></input></td>
	     </tr>
     </tbody>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="dataList">
  <tr>
    <th colspan="8">
    <em>日志列表</em>
    </th>
  </tr>
  <tr class="title">
	<td>操作时间</td>
	<td>操作人id</td>
	<td>操作用户</td>
	<td>ip地址</td>
	<td>设备名称</td>
	<td>防区名称</td>
	<td>操作描述</td>
    <td>操作项</td>
  </tr>
 <s:if test="null!=operatorLogs&&operatorLogs.size()>0">
 <s:iterator value="operatorLogs" id="model">
  <tr>
  	<td>${model.createTime}</td>
  	<td>${model.operator.id}</td>
    <td>${model.operator.nickName}</td>
     <td>${model.ipAddr}</td>
     
    <td>${model.device.deviceName}</td>
    <td>${model.zone.zoneName}</td>
    <td>${model.memo}</td>
    <td>${model.typeName}</td>
    
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
