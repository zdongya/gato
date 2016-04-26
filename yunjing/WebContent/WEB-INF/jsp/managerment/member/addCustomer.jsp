<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
<link href="<%=request.getContextPath()%>/css/default.css" rel="stylesheet" type="text/css" />
<script>
	function saveCustomer(){
		document.forms[0].submit();
	}

</script>
</head>

<body>
<form action="<%=request.getContextPath()%>/managerment/customer_add.html" method="post">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="dataList">
  <tr>
    <th colspan="4">
    <em>增加用户</em>
    <ul>
        <li><a href="javascript:saveCustomer();">保存</a></li>
        <li><a href="javascript:void(0)" onclick="javascript:history.go(-1)">返回</a></li>
    </ul>
    </th>
  </tr>
  <tr>
    <td>客服登陆名称：</td>
    <td><input name="customer.username" id="username" type="text" size="20" class="ipt" /></td>
    <td>客服真实名称：</td>
    <td><input  type="text" name="customer.realName" id="realName" size="20" class="ipt" /></td>
  </tr>
  <tr>
    <td>客服密码：</td>
    <td><input  id="password" name="customer.password" type="password" size="20" class="ipt" /></td>
    <td>重新输入密码：</td>
    <td><input  id="repassword"  type="password" size="20" class="ipt" /></td>
  </tr>
</table>
</body>
</form>
</html>
