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
<title>page</title>
<script>
	//重置密码
	function updatePwd(userId){
		if(confirm("确定重置所选用户的密码吗？")){
			 
			 $.ajax({  
				 url :"<%=request.getContextPath()%>/member/member_updatePwd.html?member.userId=" + userId,  //后台处理程序  
				 type:"post",    //数据发送方式  
				 async:false,  
				 dataType:"json",   //接受数据格式             
				 error: function(){  
				 alert("服务器没有返回数据，可能服务器忙，请重试");  
				 },  

				success: function(json){
					var code = json.code;
					if (code == '0000'){
						alert('重置密码成功');
					} else {
						alert(json.desc);
					}
				}
				});          

		}
	}
	
	function search(){
		$("#searchForm").submit();
	}
	
	function exportExcel(){
		var action = $("#searchForm").attr("action");
		var exportAction = "<%=request.getContextPath()%>/member/member_exportExcel.html";
		$("#searchForm").attr("action", exportAction);
		$("#searchForm").submit();
		$("#searchForm").attr("action", action);
	}
	
	function addMemoDilog(userId){
		var memo = window.prompt("请输入备注","");
		if (null != memo && ''!=memo){
			memo = encodeURI(encodeURI(memo));
			var url = "<%=request.getContextPath()%>/member/member_updateMemo.html?member.userId=" + userId + "&member.memo=" + memo  //后台处理程序  
			$.ajax({  
				 url : url,
				 type:"post",    //数据发送方式  
				 async:false,  
				 dataType:"json",   //接受数据格式             
				 error: function(){  
				 alert("服务器没有返回数据，可能服务器忙，请重试");  
				 },  

				success: function(json){
					var code = json.code;
					if (code == '0000'){
						alert('设置用户备注成功');
						window.location.reload();
					} else {
						alert(json.desc);
					}
				}
				});          

		}
	}
</script>
</head>


<body>
<form action="<%=request.getContextPath()%>/member/member_index.html" method="post" id="searchForm">
<table class="searchTable">
     <tbody>
	     <tr>
	     
	     	 <td width="10%" nowrap="nowrap" bgcolor="#f1f1f1" align="right">注册时间从</td>
		     <td width="10%"><input class="Wdate"  type="text" onClick="WdatePicker()" name="member.beginDate" value="${member.beginDate}"/></td>
		     <td width="10%" nowrap="nowrap" bgcolor="#f1f1f1" align="right">到</td>
		     <td width="10%"><input class="Wdate"  type="text" onClick="WdatePicker()" name="member.endDate" value="${member.endDate}"/></td>
		     <td width="10%" nowrap="nowrap" bgcolor="#f1f1f1" align="right">昵称：</td>
		     <td><input type="text" class=" span1-1" name="member.nickName" value="${member.nickName}" /></td>
		     <td> <img src="<%=request.getContextPath()%>/images/search.png" onclick="javascript:search();"></img></td>
		     <td> <input type="button" value="导出excel" onclick="javascript:exportExcel();" ></input></td>
	     </tr>
     </tbody>
</table>
       
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="dataList">
  <tr>
    <th colspan="9">
    <em>用户列表</em>
    </th>
    
  </tr>
  <tr class="title">
	<td>用户昵称 </td>
	<td>用户备注</td>
	<td>用户手机号</td>
	<td>用户微信昵称</td>
    <td>用户类型</td>
    <td>用户邮箱</td>
    <td>用户注册时间</td>
    <td>最后登录时间</td>
    <td>操作</td>
  </tr>
 <s:if test="null!=members&&members.size()>0">
 <s:iterator value="members" id="model">
  <tr>
    <td>${model.nickName}</td>
    <td>${model.memo}</td>
    <td>${model.mobileNo}</td>
    <td>${model.nickName}</td>
    <td>${model.typeName}</td>
    <td>${model.email}</td>
    <td>${model.registerDate}</td>
    <td>${model.loginDate}</td>
    <td>
    <!-- 
    <a href="javascript:updatePwd('${model.userId}');">重置密码</a>
    -->
    <a href="javascript:addMemoDilog('${model.userId}');">添加备注</a>&nbsp;&nbsp;
    </td>
  </tr>
  </s:iterator>
  </s:if>

  <tr class="page">
    <td colspan="9">&nbsp;</td>
  </tr>
</table>
</form>
 <odb:pageController/>
</body>

</html>
