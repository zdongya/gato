<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.3.2.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>建材管理</title>
<link href="<%=request.getContextPath()%>/css/default.css" rel="stylesheet" type="text/css" />
<script> 
	function checkNumber(value){
		var reg=/^([1-9]\d*)$/;
		return reg.test(value);
	}

	function check(){
		var number = $.trim($("#number").val());
		if(!checkNumber(number)){
			alert("入库数量必须是正整数!");
			$("#number").val(null);
			$("#number")[0].focus();
		}
	}

	function save(){
		var number = $.trim($("#number").val());
		if(null==number||""==number){
			alert("入库建材数量不能为空!");
		}else{
			
				document.forms[0].submit();
		}
	}

</script>
</head>

<body>
<form action="<%=request.getContextPath()%>/managerment/proIn_add.html" method="post">
<input type="hidden" name="proInDto.materialId" value="${material.id}"/>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="dataList">
  <tr>
    <th colspan="4">
    <em>建材入库</em>
    <ul>
        <li><a href="javascript:save();">保存</a></li>
        <li><a href="javascript:void(0)" onclick="javascript:history.go(-1)">返回</a></li>
    </ul>
    </th>
  </tr>
  <tr>
    <td>建材名称：</td>
    <td>${material.name}</td>
    <td>所属类别：</td>
    <td>${material.materialType.name}</td>
  </tr>
  <tr>
    <td>入库数量：</td>
    <td><input name="proInDto.number" id="number" type="text" maxlength="8"  size="20" class="ipt" onblur="javascript:check();" /></td>
    <td>入库人：</td>
    <td><authz:authentication property="name"/></td>
  </tr>
</table>
</form>
</body>

</html>
