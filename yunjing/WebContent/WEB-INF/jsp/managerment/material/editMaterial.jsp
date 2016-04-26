<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title> 钢管建材租赁管理系统</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.3.2.min.js"></script>
<link href="<%=request.getContextPath()%>/css/default.css" rel="stylesheet" type="text/css" />
<script>

	function textCounter(field, maxlimit) {   
		 
		if (field.value.length > maxlimit) 
			field.value = field.value.substring(0, maxlimit);  
	
	 }   

	function checkNumber(value){
		var reg=/^(0|([1-9]\d*))$/;
		return reg.test(value);
	}


	function editType(){
		document.forms[0].submit();
	}

	function checkName(){
		var name = $.trim($("#name").val());
		var oldName = $("#oldName").val();
		if(null==name||""==name){
			alert("类别名称不允许为空!");
			$("#name")[0].focus();
			return false;
		}
		if(name==oldName){
			return false;
		}
		else{
			var data = {"name":name};
			$.ajax({
				url:"<%=request.getContextPath()%>/managerment/material_checkName.html",
				type: "POST",
                dataType: "json",
                data: data,
                success: function(date) {
	                if(null!=date&&null!=date.name){
		                alert("该建材已经存在，请重新填写!");
		                $("#name").val(oldName);
		                $("#name")[0].focus();
	                }
				}
			}
			);
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
		document.forms[0].submit();
	}

</script>
</head>

<body> 
<form action="<%=request.getContextPath()%>/managerment/material_edit.html" method="post" >
	<input type="hidden" name="materialDto.id" value="${material.id}"/>
	<input type="hidden" id="oldName" value="${material.name}"/>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="dataList">
	<tr>
    <th colspan="4">
    <em>建材编辑</em>
    <ul>
        <li><a href="javascript:saveMaterial();">保存</a></li>
        <li><a href="javascript:void(0)" onclick="javascript:history.go(-1)">返回</a></li>
    </ul>
    </th>
  </tr>
  <tr>
    <td>建材名称：</td>
    <td><input name="materialDto.name" id="name" type="text" size="20" class="ipt" onblur="checkName();" value="${material.name}" /></td>
    <td>价格：</td>
    <td><input name="materialDto.price" id="price" type="text" size="20" class="ipt"  value="${material.price}" onblur="checkPrice();"/></td>
  </tr>
  <tr>
    <td>库存：</td>
    <td>${material.inventory}</td>
    <td>所属类别：</td>
    <td>    	
	    <select id="typeId" name="materialDto.typeId">
	    	<s:iterator value="materialTypes" id="materialType">
	    		<option value="${materialType.id}" <s:if test="#materialType.id==material.id">selected="selected"</s:if>>${materialType.name}</option>
	    	</s:iterator>
	    </select> 
    </td>
  </tr>
  
   <tr>
    <td>描述：</td>
    <td><input name="materialDto.describle" id="describle" type="text" size="20" class="ipt"  value="${material.describle}" /></td>
    <td></td>
    <td></td>
  </tr>
  </table>
</form>
</body>

</html>
