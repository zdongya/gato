<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.3.2.min.js"></script>
<link href="<%=request.getContextPath()%>/css/default.css" rel="stylesheet" type="text/css" />
<title>page</title>
<script>
	function deleteType(typeId){
		if(confirm("确定删除该建材类别吗？")){
			var data = {"typeId":typeId};
			$.ajax({
				url:"<%=request.getContextPath()%>/managerment/type_checkType.html",
				type: "POST",
                dataType: "json",
                data: data,
                success: function(date) {
	                if("have"==date){
		                alert("该类别下有建材，不允许删除!");
		                return false;
	                }else{
	                	window.location.href="<%=request.getContextPath()%>/managerment/type_delete.html?typeId="+typeId;
	                }
				}
				}
			);

		}
		
	}

	function toEdit(typeId){
		showModalDialog('<%=request.getContextPath()%>/managerment/type_toEdit.html?typeId='+typeId,'编辑','dialogWidth:1000px;dialogHeight:800px;dialogLeft:100px;dialogTop:100px;center:yes;help:yes;resizable:yes;status:yes')
		
	}
</script>
</head>


<body>
<form>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="dataList">
  <tr>
    <th colspan="6">
    <em>建材类别列表</em>
    <ul>
    	<li><a href="<%=request.getContextPath()%>/managerment/type_toAdd.html">新建建材类别</a></li>
    </ul> 
  </tr>
  <tr class="title">

	<td>类别名称 </td>
    <td>创建时间</td>
    <td>查看该类别下的所有建材</td>
    <td>操作</td>
  </tr>
 <s:if test="null!=materialTypes&&materialTypes.size()>0">
 <s:iterator value="materialTypes" id="materialType">
  <tr >
    <td>${materialType.name}</td>
    <td>${materialType.createTime}</td>
    <td><a href="<%=request.getContextPath()%>/managerment/type_disChildren.html?typeId=${materialType.id}">查看该类别下的所有建材</a></td>
    <td>
    	<a href="<%=request.getContextPath()%>/managerment/type_toEdit.html?typeId=${materialType.id}">编辑</a>
    	&nbsp;&nbsp;<a href="#" onclick="javascript:deleteType(${materialType.id});" >删除</a>
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
