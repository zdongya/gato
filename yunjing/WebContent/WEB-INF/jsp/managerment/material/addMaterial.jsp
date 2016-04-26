<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.3.2.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>建材管理</title>
<link href="<%=request.getContextPath()%>/css/default.css" rel="stylesheet" type="text/css" />
<script> 

function checkNumber(value){
	var reg=/^(0|([1-9]\d*))$/;
	return reg.test(value);
}

function check(){
	var number = $.trim($("#inventory").val());
	if(!checkNumber(number)){
		alert("库存必须是自然数,若暂无库存请填0!");
		$("#inventory").val(0);
		$("#inventory")[0].focus();
	}
}

function checkPrice(){

	var number = $.trim($("#price").val());
	if(!checkNumber(number)){
		alert("价格必须是整数!");
		$("#price").val(0);
		$("#price")[0].focus();
	}
	
}
	
	function saveMaterial(){
		var name = $.trim($("#name").val());
		var value = $("#typeId").val();
		if(null==name||""==name){
			alert("建材名称不能为空!");
		}else{
			if(null==value||""==value){
				alert("请先添加建材类别!");
			}else{
				document.forms[0].submit();
			}
			
		}
	}

	function checkName(){
		var name = $.trim($("#name").val());	
		if(null==name||""==name){
			alert("建材名称不允许为空!");
			$("#name")[0].focus();
			return false;
		}else{
			var data = {"name":name};
			$.ajax({
				url:"<%=request.getContextPath()%>/managerment/material_checkName.html",
				type: "POST",
                dataType: "json",
                data: data,
                success: function(date) {
	                if(null!=date&&null!=date.name){
		                alert("该建材已经存在，请重新填写!");
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
<form action="<%=request.getContextPath()%>/managerment/material_add.html" method="post">

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="dataList">
  <tr>
    <th colspan="4">
    <em>添加建材</em>
    <ul>
        <li><a href="javascript:saveMaterial();">保存</a></li>
        <li><a href="javascript:void(0)" onclick="javascript:history.go(-1)">返回</a></li>
    </ul>
    </th>
  </tr>
  <tr>
    <td>建材名称：</td>
    <td><input name="materialDto.name" id="name" type="text" size="20" class="ipt" onblur="checkName();" /></td>
    <td>租赁价格(元/每天)：</td>
    <td><input name="materialDto.price" id="price" type="text" size="20" class="ipt" onblur="checkPrice();" /></td>
  </tr>
  <tr>
    <td>库存：</td>
    <td><input name="materialDto.inventory" id="inventory" type="text" size="20" maxlength="8" class="ipt" onblur="check();" /></td>
    <td>所属类别：</td>
    <td>    	
	    <select id="typeId" name="materialDto.typeId">
	    	<s:iterator value="materialTypes" id="materialType">
	    		<option value="${materialType.id}">${materialType.name}</option>
	    	</s:iterator>
	    </select> 
    </td>
  </tr>
  
    <tr>
    <td>描述：</td>
    <td><input name="materialDto.describle" id="describle" type="text" size="20" class="ipt"  /></td>
    <td></td>
    <td></td>
  </tr>
</table>
</form>
</body>

</html>
