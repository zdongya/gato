<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<%=request.getContextPath()%>/css/default.css" rel="stylesheet" type="text/css" />
<script>
	function deleteUser(userId){
		window.location.href="<%=request.getContextPath()%>/managerment/member_delete.html?memberId="+userId;
	}
</script>
<title>page</title>

</head>


<body>
<form>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="dataList">
  <tr>
    <th colspan="6">
    <em>会员列表</em>
    
    </th>
  </tr>
  <tr class="title">
    <td>登录名   </td>
	<td>真实姓名   </td>
    <td>手机号</td>
    <td>激活时间</td>
    <td>操作</td>
  </tr>
 <s:if test="null!=members&&members.size()>0">
 <s:iterator value="members" id="member">
  <tr>
    <td>${member.username}</td>
    <td>${member.realName}</td>
    <td>${member.cellphone}</td>
    <td>${member.createTime}</td>
    <td>
    <a href="<%=request.getContextPath()%>/managerment/member_toView.html?memberId=${member.id}">查看</a>&nbsp;&nbsp;
    <a href="#" onclick="javascript:deleteUser('${member.id}');">删除用户</a>
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

</html>
