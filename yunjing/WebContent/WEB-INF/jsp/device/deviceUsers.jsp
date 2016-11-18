<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="odb" uri="/odb"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=request.getContextPath()%>/css/default.css" rel="stylesheet" type="text/css" />
<title>设备用户列表</title>
</head>


<body>
<form>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="dataList">
  <tr>
    <th colspan="9">
    <em style="overflow: hidden;white-space: nowrap;text-overflow: ellipsis;width:100%">设备用户列表(<s:property value="#request.deviceName" />)</em>
    </th>
  </tr>
  <tr class="title">
    <td>用户id </td>
  	<td>用户昵称 </td>
	<td>用户备注</td>
	<td>用户手机号</td>
	<td>用户微信昵称</td>
    <td>注册方式</td>
    <td>用户邮箱</td>
    <td>用户注册时间</td>
    <td>最后登录时间</td>

  </tr>
 <s:if test="null!=members&&members.size()>0">
 <s:iterator value="members" id="member">
  <tr>
    <td>${member.id}</td>
    <td>${member.nickName}</td>
    <td>${member.memo}</td>
    <td>${member.mobileNo}</td>
    <td>${member.nickName}</td>
    <td>${member.typeName}</td>
    <td>${member.email}</td>
    <td>${member.registerDate}</td>
    <td>${member.loginDate}</td>
  </tr>
  </s:iterator>
  </s:if>
</table>
</form>
</body>

</html>
