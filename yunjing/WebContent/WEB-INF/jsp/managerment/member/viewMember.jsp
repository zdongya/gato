<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
<link href="<%=request.getContextPath()%>/css/default.css" rel="stylesheet" type="text/css" />
</head>

<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="dataList">
  <tr>
    <th colspan="4">
    <em>查看会员</em>
    <ul>
        <li><a href="javascript:void(0)" onclick="javascript:history.go(-1)">返回</a></li>
    </ul>
    </th>
  </tr>
  <tr>
    <td>会员登陆名称：</td>
    <td><input name="member.username" id="username" type="text" size="20" class="ipt" value="${member.username}"/></td>
    <td>会员真实名称：</td>
    <td><input  type="text" name="member.realName" id="realName" size="20" class="ipt" value="${member.realName}" /></td>
  </tr>
  
  <tr>
    <td>手机号：</td>
    <td><input name="member.cellPhone" id="cellPhone" type="text" size="20" class="ipt" value="${member.cellphone}" /></td>
    <td>邮箱：</td>
    <td><input  type="text" name="member.email" id="email" size="20" class="ipt" value="${member.email}" /></td>
  </tr>
</table>
</body>
</html>
