<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.3.2.min.js"></script>
<link href="<%=request.getContextPath()%>/css/default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	function save(){
		var oldPassword = $.trim($("#oldPassword").val());
		var newPassword = $.trim($("#newPassword").val());
		var rePassword = $.trim($("#rePassword").val());
		if(null==oldPassword||""==oldPassword){
			alert("原密码不允许为空!");
			return false ;
		}
		if(null==newPassword||""==oldPassword||null==rePassword||""==rePassword){
			alert("新密码和重复输入密码不允许为空!");
			return false;
		}
		if(rePassword!=newPassword){
			alert("两次输入密码不一致!");
			return false;
		}
			var data = {"oldPassword":oldPassword};
			$.ajax({
				url:"<%=request.getContextPath()%>/member/member_checkPassword.html",
				type: "POST",
                dataType: "json",
                data: data,
                success: function(date) {
	                if(date=="success"){
		                $("#psForm").submit();
	                }else{
		                alert("原密码不正确!");
	                }
				}
			}
			);
	}
</script>
</head>

<body>
<form id="psForm" action="<%=request.getContextPath()%>/member/member_editPassword.html">
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="dataList">
  <tr>
    <th colspan="4">
    <em>修改密码</em>
    <ul>
    	<li><a href="#" onclick="javascript:save();">保存</a></li>
        <li><a href="javascript:void(0)" onclick="javascript:history.go(-1)">返回</a></li>
    </ul>
    </th>
  </tr>
  
  <tr>
    <td>原密码：</td>
    <td><input type="text" id="oldPassword" /></td>
  </tr>
  <tr>
    <td>新密码：</td>
    <td><input type="text" id="newPassword" name="newPassword"/></td>
  </tr>
  <tr>
    <td>重复输入密码：</td>
    <td><input type="text" id="rePassword" /></td>
  </tr>

</table>
</form>
</body>
</html>
