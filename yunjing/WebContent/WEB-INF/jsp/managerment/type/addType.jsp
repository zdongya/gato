<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.3.2.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>建材类别管理</title>
<link href="<%=request.getContextPath()%>/css/default.css" rel="stylesheet" type="text/css" />
<script>

	function textCounter(field, maxlimit) {   
	 
		if (field.value.length > maxlimit) 
			field.value = field.value.substring(0, maxlimit);  
 
	 }   
	
	function saveType(){
		var name = $.trim($("#name").val());
		if(null==name||""==name){
			alert("类别名称不允许为空!");
			$("#name")[0].focus();
		}else{
			document.forms[0].submit();
		}
	}

	function checkName(){
		var name = $.trim($("#name").val());
		if(null==name||""==name){
			alert("类别名称不允许为空!");
			$("#name")[0].focus();
			return false;
		}else{
			var data = {"name":name};
			$.ajax({
				url:"<%=request.getContextPath()%>/managerment/type_checkName.html",
				type: "POST",
                dataType: "json",
                data: data,
                success: function(date) {
	                if(null!=date&&null!=date.name){
		                alert("该类别已经存在，请重新填写!");
		                $("#name").val("");
		                $("#name")[0].focus();
	                }
				}
			}
			);
		}
	}

</script>
</head>

<body>
<form action="<%=request.getContextPath()%>/managerment/type_add.html" method="post">

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="dataList">
  <tr>
    <th colspan="4">
    <em>添加类别</em>
    <ul>
        <li><a href="javascript:saveType();">保存</a></li>
        <li><a href="javascript:void(0)" onclick="javascript:history.go(-1)">返回</a></li>
    </ul>
    </th>
  </tr>
  <tr>
    <td>类别名称：</td>
    <td><input name="materialType.name" id="name" type="text" size="20" class="ipt" onblur="checkName();" /></td>
    <td>类别描述：</td>
    <td><textarea name="materialType.describle" cols="28" rows="5" onKeyUp="textCounter(this,100);"></textarea></td>
  </tr>
</table>
</form>
</body>

</html>
