<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.3.2.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>新增banner图</title>
<link href="<%=request.getContextPath()%>/css/default.css" rel="stylesheet" type="text/css" />
<script> 
function saveBanner(){
	document.forms[0].submit();
}
</script>
</head>

<body>
<form action="<%=request.getContextPath()%>/upload/upload_add.html" method="post">

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="dataList">
  <tr>
    <th colspan="4">
    <em>新增banner图</em>
    <ul>
        <li><a href="javascript:saveBanner();">保存</a></li>
        <li><a href="javascript:void(0)" onclick="javascript:history.go(-1)">返回</a></li>
    </ul>
    </th>
  </tr>
  <tr>
    <td>链接地址：</td>
    <td><input name="imgHref" id="imgHref" type="text" size="200" class="ipt"  /></td>
  </tr>
  <tr>
    <td>选择图片：</td>
    <td><input type="file" name="uploadFile" /></td>
  </tr>
  
</table>
</form>
</body>

</html>
