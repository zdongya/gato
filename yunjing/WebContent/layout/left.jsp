<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags"%>   
 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.3.2.min.js"></script> 
<script type="text/javascript"> 
$(document).ready(function(){
    $(".acc_container:not('.acc_container:first')").hide();
    $('.acc_trigger').click(function(){
	if( $(this).next().is(':hidden') ) {
            $('.acc_trigger').removeClass('active').next().slideUp();
            $(this).toggleClass('active').next().slideDown();
	}else{
            $(this).toggleClass('active');
            $(this).next().slideUp();
        }
	return false;
    });
	
	if(screen.width>1200){
		window.parent.fms.cols="200,*";
	}else{
		window.parent.fms.cols="150,*";
		}
});
</script> 
<style type="text/css">
*{margin:0;padding:0;}
body{font-size:13px; font-family:"宋体", "微软雅黑", Tahoma, Verdana;background:#e9f4ff url(./images/left_bg.gif) repeat-y right 0;}
li{list-style:none;}
img{border:0;}
h2.leftTitle{width:auto;height:30px;background:url(<%=request.getContextPath()%>/images/left_title.gif) repeat-x 0 -30px;margin:0 0 15px 0;}
h2.leftTitle em{display:block;width:auto;height:30px;line-height:30px;padding:0 0 0 15px;background:url(<%=request.getContextPath()%>/images/left_title.gif) no-repeat right 0;
font-size:14px;font-style:normal;font-size:14px;color:#FFF;font-weight:bold;text-align:left;}

.container{width:auto;padding:0 15px;}


h3.acc{
	background: url(<%=request.getContextPath()%>/images/leftmenu.gif) no-repeat right 0;
	height: 30px;	line-height: 25px;
	font-size: 13px;
	font-weight: normal;
}
h3.acc a {
	background: url(<%=request.getContextPath()%>/images/leftmenu.gif) no-repeat left -90px;
	height:30px;
	color: #0C0D7E;
	text-decoration: none;
	display: block;
	padding:0 0 0 25px;
}
h3.acc a:hover {
	color: #ff0000;
}
h3.acc_trigger {
	background: url(<%=request.getContextPath()%>/images/leftmenu.gif) no-repeat right 0;
	height: 30px;	line-height: 25px;
	font-size: 13px;
	font-weight: normal;
	
}
h3.acc_trigger a {
	background: url(<%=request.getContextPath()%>/images/leftmenu.gif) no-repeat left -30px;
	color: #0C0D7E;
	height:30px;
	text-decoration: none;
	display: block;
	padding:0 0 0 25px;
}
h3.acc_trigger a:hover {
	color: #ff0000;
}
h3.active {background: url(<%=request.getContextPath()%>/images/leftmenu.gif) no-repeat right 0;}
h3.active  a{background: url(<%=request.getContextPath()%>/images/leftmenu.gif) no-repeat left -60px;
padding:0 0 0 25px;
}

.acc_container {
	overflow: hidden;
	font-size: 13px;
	width: auto;
	clear: both;
}
.acc_container .block {
	padding: 5px 0 10px 20px;font-size:13px;
}
.container ul li{width:auto;height:25px;line-height:25px;vertical-align:middle;padding: 0 0 0 20px;
background:url(<%=request.getContextPath()%>/images/file.gif) no-repeat left center;
}
.container ul li a:link{color:#333;text-decoration:none;}
.container ul li a:active{color:#333;text-decoration:none;}
.container ul li a:hover{color:#FF0000;text-decoration:underline;}
.container ul li a:visited{color:#333;text-decoration:none;}

.current{
  color:#ffffff;
  background:#00BFFF;
}
</style>
<script type="text/javascript">
	function change_bg(obj){
		var a = document.getElementsByTagName("a")
		for(var i=0;i<a.length;i++){a[i].className="";}
		obj.className="current";
	}
</script>
</head>

<body>
<h2 class="leftTitle"><em>工作菜单</em></h2>
<div class="container">


<authz:authorize ifAnyGranted="USER_ADMIN">
	<h3 class="acc_trigger"><a href="#">用户管理</a></h3> 
	<div class="acc_container"> 
		<ul class="block">	
			<li><a href="<%=request.getContextPath()%>/member/member_index.html" target="mainFrame" onclick="change_bg(this)">用户列表</a></li>
			
			<li><a href="<%=request.getContextPath()%>/member/banner_index.html" target="mainFrame" onclick="change_banner(this)">app首页图片</a></li>         
		</ul> 
	</div>
	<h3 class="acc_trigger"><a href="#">设备管理</a></h3> 
	<div class="acc_container"> 
		<ul class="block">
			<!--
			<li><a href="<%=request.getContextPath()%>/device/device_index.html" target="mainFrame"  onclick="change_bg(this)" class="current">设备列表</a></li>
             <li><a href="<%=request.getContextPath()%>/managerment/proIn_index.html" target="mainFrame" >设备远程诊断</a></li>
             
             <li><a href="<%=request.getContextPath()%>/zone/zone_index.html" target="mainFrame" onclick="change_bg(this)">防区管理</a></li>
             -->
            <li><a href="<%=request.getContextPath()%>/device/device_query.html" target="mainFrame"  onclick="change_bg(this)">设备查询</a></li>
            
            
		</ul> 
	</div>
	<h3 class="acc_trigger"><a href="#">报警信息列表</a></h3> 
	<div class="acc_container"> 
		<ul class="block">
			<li><a href="<%=request.getContextPath()%>/warn/warn_index.html" target="mainFrame"  onclick="change_bg(this)">报警信息列表</a></li>
		</ul> 
	</div>
	
	<h3 class="acc_trigger"><a href="#">操作日志管理</a></h3> 
	<div class="acc_container"> 
		<ul class="block">
			<li><a href="<%=request.getContextPath()%>/log/log_index.html" target="mainFrame" onclick="change_bg(this)">操作日志列表</a></li>
			<!-- 
			<li><a href="<%=request.getContextPath()%>/log/log_query.html" target="mainFrame" onclick="change_bg(this)">操作日志查询</a></li>
			 -->
		</ul> 
	</div>
	
	<h3 class="acc_trigger"><a href="#">用户反馈管理</a></h3> 
	<div class="acc_container"> 
		<ul class="block">
			<li><a href="<%=request.getContextPath()%>/fk/fk_index.html" target="mainFrame" onclick="change_bg(this)">用户反馈列表</a></li>
		</ul> 
	</div>
	
	<!-- 
	<h3 class="acc_trigger"><a href="#">平台运维报警配置</a></h3> 
	<div class="acc_container"> 
		<ul class="block">
			<li><a href="<%=request.getContextPath()%>/managerment/rento_toShenpi.html?status=0" target="mainFrame">操作日志记录</a></li>
			<li><a href="<%=request.getContextPath()%>/managerment/rento_toShenpi.html?status=2" target="mainFrame" >操作日志查询</a></li>
		</ul> 
	</div>
	
	 -->
</authz:authorize>
	
</div>

</body>
</html>
