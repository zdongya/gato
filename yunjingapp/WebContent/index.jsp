<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传图片</title>
</head>
<body>
	<form action="<%=request.getContextPath() %>/user/uploadHeadImg.do" method="post" enctype="multipart/form-data">
		<input type="hidden" name="userId" value="0692ab9753fd433c9e43de65f99d0e8c"/>
		<input type="hidden" name="token" value="3b89aa990f5eac16d34781170a7aca88"/>
		<input type="file" name="file" /> <input type="submit" value="Submit" />
	</form>
</body>
</html>