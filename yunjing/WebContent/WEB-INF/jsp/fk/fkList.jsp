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
<title>page</title>
<script>
	function search(){
		$("#searchForm").submit();
	}
	
	function exportExcel(){
		var action = $("#searchForm").attr("action");
		var exportAction = "<%=request.getContextPath()%>/fk/fk_exportExcel.html";
		$("#searchForm").attr("action", exportAction);
		$("#searchForm").submit();
		$("#searchForm").attr("action", action);
	}
	
	function addMemoDilog(id){
		var memo = window.prompt("请输入备注","");
		if (null != memo && ''!=memo){
			memo = encodeURI(encodeURI(memo));
			var url = "<%=request.getContextPath()%>/fk/fk_updateMemo.html?retroaction.id=" + id + "&retroaction.memo=" + memo  //后台处理程序  
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
						alert('设置备注成功');
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
<form action="<%=request.getContextPath()%>/fk/fk_index.html" method="post" id="searchForm">
<table class="searchTable">
     <tbody>
     	<tr>
		     <td> <input type="button" value="导出excel" onclick="javascript:exportExcel();" ></input></td>
	     </tr>
     </tbody>
</table>


<table width="100%" border="0" cellpadding="0" cellspacing="0" class="dataList">
  <tr>
    <th colspan="9">
    <em>用户反馈列表</em>
    
    </th>
  </tr>
  <tr class="title">

	<td>用户昵称 </td>
	<td>用户微信号</td>
	<td>反馈时间</td>
    <td>反馈内容</td>
    <td>联系方式</td>
    <td>备注</td>
    <td>操作</td>
  </tr>
	 
 <s:if test="null!=retroactions&&retroactions.size()>0">
 <s:iterator value="retroactions" id="retroaction">
  <tr>
    <td>${retroaction.user.nickName}</td>
    <td>${retroaction.user.wechatId}</td>
    <td>${retroaction.addDate}</td>
    <td>${retroaction.contents}</td>
    <td>${retroaction.contact}</td>
    <td>${retroaction.memo}</td>
    <td><a href="javascript:addMemoDilog('${retroaction.id}');">添加备注</a></td>
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
