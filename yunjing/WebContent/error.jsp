<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>云警管理后台</title>
</head>
<frameset rows="120,*"  frameborder="no" border="0" framespacing="0">
  <frame src="<%=request.getContextPath()%>/layout_top.html" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame" />
  <frame src="<%=request.getContextPath()%>/errorbody.jsp"  />
</frameset>
<noframes><body>

</body></noframes>
</html>
