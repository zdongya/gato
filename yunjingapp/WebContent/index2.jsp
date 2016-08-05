<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传图片</title>
</head>
<body>
	<form action="<%=request.getContextPath() %>/user/uploadHeadImg.do" method="post" enctype="multipart/form-data">
		<input type="hidden" name="userId" value="b73522b7001e49d7a21701edcfd5f76a"/>
		<input type="hidden" name="token" value="13dfc9975aad80aefd07405e3f5194c5"/>
		<input type="file" name="file" /> <input type="submit" value="Submit" />
	</form>
</body>
</html>