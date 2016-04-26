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
<title>日志查询</title>
<script>
function search(){
	$("#searchForm").submit();
}
</script>
</head>


<body>
<form action="<%=request.getContextPath()%>/log/log_query.html" method="post" id="searchForm">
<table class="searchTable">
     <tbody>
	     <tr>
		     <td width="10%" nowrap="nowrap" bgcolor="#f1f1f1" align="right">用户昵称：</td>
		     <td><input type="text" class=" span1-1" name="log.operator.nickName" value = "${log.operator.nickName}" /></td>
		     
		     <td width="10%" nowrap="nowrap" bgcolor="#f1f1f1" align="right">设备名称：</td>
		     <td><input type="text" class=" span1-1" name="log.name" value="${log.name}" /></td>
		     <td> <img src="<%=request.getContextPath()%>/images/search.png" onclick="javascript:search();"></img></td>
	     </tr>
     </tbody>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="dataList">
  <tr>
    <th colspan="4">
    <em>日志列表</em>
    </th>
  </tr>
  <tr class="title">

	<td>操作人</td>
	<td>操作时间</td>
	<td>日志内容</td>
    <td>设备名称</td>
  </tr>
 <s:if test="null!=operatorLogs&&operatorLogs.size()>0">
 <s:iterator value="operatorLogs" id="model">
  <tr>
    <td>${model.operator.nickName}</td>
    <td>${model.createTime}</td>
    <td>${model.memo}</td>
    <td>${model.name}</td>
  </tr>
  </s:iterator>
  </s:if>

  <tr class="page">
    <td colspan="4">&nbsp;</td>
  </tr>
</table>
</form>
 <odb:pageController/>
</body>

</html>
