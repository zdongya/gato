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
	
	function deleteMaterial(typeId){
		if(confirm("确定删除该建材吗？")){
			window.location.href="<%=request.getContextPath()%>/managerment/material_delete.html?materialId="+typeId;
		}
		
	}
	
	function exportExcel(){
		var action = $("#searchForm").attr("action");
		var exportAction = "<%=request.getContextPath()%>/warn/warn_exportExcel.html";
		$("#searchForm").attr("action", exportAction);
		$("#searchForm").submit();
		$("#searchForm").attr("action", action);
	}

</script>
</head>


<body>
<form action="<%=request.getContextPath()%>/warn/warn_index.html" method="post" id="searchForm">
<table class="searchTable">
     <tbody>
	     <tr>
	     	 <td width="10%" nowrap="nowrap" bgcolor="#f1f1f1" align="right">报警时间从</td>
		     <td width="16%"><input class="Wdate"  type="text" onClick="WdatePicker()" name="warningInfo.beginDate" value="${warningInfo.beginDate}"/></td>
		     <td width="10%" nowrap="nowrap" bgcolor="#f1f1f1" align="right">到</td>
		     <td width="16%"><input class="Wdate"  type="text" onClick="WdatePicker()" name="warningInfo.endDate" value="${warningInfo.endDate}"/></td>
		    <!-- 
		     <td width="10%" nowrap="nowrap" bgcolor="#f1f1f1" align="right">序列号：</td>
		     <td><input type="text" class=" span1-1" name="device.deviceNo" value = "${device.deviceNo}" /></td>
		     -->
		    
		     <td width="10%" nowrap="nowrap" bgcolor="#f1f1f1" align="right">处理结果：</td>
		     <td>
		     <select name="warningInfo.istate" >
		          <option value =""  <s:if test='warningInfo.istate==""'>selected="selected"</s:if> >--请选择--</option>
				  <option value ="0" <s:if test='warningInfo.istate=="0"'>selected="selected"</s:if> >未解决</option>
				  <option value ="1" <s:if test='warningInfo.istate=="1"'>selected="selected"</s:if> >已解决</option>
				  <option value ="2" <s:if test='warningInfo.istate=="2"'>selected="selected"</s:if> >误报</option>
			</select>
		     
		     </td>
		     <td> <img src="<%=request.getContextPath()%>/images/search.png" onclick="javascript:search();"></img></td>
		     <td> <input type="button" value="导出excel" onclick="javascript:exportExcel();" ></input></td>
	     </tr>
     </tbody>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="dataList">
  <tr>
    <th colspan="9">
    <em>报警信息列表</em>
    
    </th>
  </tr>
  <tr class="title">

	<td>报警编号 </td>
	<td>防区编号</td>
	<td>报警时间</td>
    <td>处理结果</td>
    <td>报警类型</td>
    <td>解决人</td>
    <td>解决时间</td>
    <td>备注</td>
  </tr>
	 
 <s:if test="null!=warnings&&warnings.size()>0">
 <s:iterator value="warnings" id="warn">
  <tr>
    <td>${warn.warningId}</td>
    <td>${warn.zoneNo}</td>
    <td>${warn.warnDate}</td>
    <td>${warn.stateName}</td>
    <td>${warn.warnTypeName}</td>
    <td>${warn.handler}</td>
    <td>${warn.handleDate}</td>
    <td>${warn.memo}</td>
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
