<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="authz" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>云警管理后台</title>
<style type="text/css">
*{margin:0;padding:0;}
body{font-size:13px; font-family:"宋体", "微软雅黑", Tahoma, Verdana;background-color:#EFEFEF;}
li{list-style:none;}
img{border:0;}
.header{width:auto;height:100px;padding:20px 0 0 0;
background:#0c2288 url(<%=request.getContextPath()%>/images/top_yunjing.png) no-repeat right center;margin:0 auto;clear:both;}
.header .logo{width:auto;height:77px;clear:both;
/*background: url(<%=request.getContextPath()%>/images/logo.png) no-repeat;behavior: url(<%=request.getContextPath()%>/images/iepngfix.htc);*/
background-image: url(./images/logo.png)!important;/* FF IE7 */
background-repeat: no-repeat;
_filter: progid:DXImageTransform.Microsoft.AlphaImageLoader(src='<%=request.getContextPath()%>/images/logo.png'); /* IE6 */
_background-image: none; /* IE6 */
}

.header .user{width:450px;height:23px;line-height:23px;color:#0C0D7E;font-weight:bold;padding:0;
font-style:normal;text-align:center;float:right; margin-top: 75px;}

.menu{width:auto;height:45px;background:url(<%=request.getContextPath()%>/images/menu_bg.gif) repeat-x 0 0;margin:0 auto;clear:both;}
.menu ul{width:980px;height:45px;margin:0 auto;}
.menu ul li{height:45px;line-height:38px;background:url(<%=request.getContextPath()%>/images/menu_bg.gif) no-repeat right -45px;padding:0 20px;float:left;}
.menu ul li.last{background:url(<%=request.getContextPath()%>/images/menu_bg.gif) no-repeat right -135px;float:right;}
.menu ul li a:link{color:#FFF; text-decoration:none;}
.menu ul li a:active{color:#FFF; text-decoration:none;}
.menu ul li a:hover{color:#FFF; text-decoration:none;}
.menu ul li a:visited{color:#FFF; text-decoration:none;}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" >
function modifyPwd(){
	var newPwd = window.prompt("请输入新密码","");
	if (null != newPwd && ''!=newPwd){			 
			 $.ajax({  
				 url :"<%=request.getContextPath()%>/login_updatePwd.html?newPwd=" + newPwd,  //后台处理程序  
				 type:"post",    //数据发送方式  
				 async:false,  
				 dataType:"json",   //接受数据格式             
				 error: function(){  
				 alert("服务器没有返回数据，可能服务器忙，请重试");  
				 },  

				success: function(json){
					var code = json.code;
					if (code == '0000'){
						alert('修改密码成功,请重新登录');
						window.location.href="<%=request.getContextPath()%>/logout.html";
					} else {
						alert(json.desc);
					}
				}
				});          

		}
}
</script>
</head>

<body>
<div class="header">
	<!--<div class="logo"></div>-->
	<div class="user">
    	云警管理后台  
    	<!-- 
    	|<a href="<%=request.getContextPath()%>/register_toRegister.html" target="_parent" >注册</a>
    	-->
    	<s:if test="loginFlag==true">
    	|欢迎您：<authz:authentication property="name"/>
    		<a href="<%=request.getContextPath()%>/logout.html" target="_parent" >注销</a>
    	&nbsp;&nbsp;<a href="#" onclick="javascript:modifyPwd();"  >修改密码</a>  
    	</s:if>
    	<s:else>
    	|欢迎您：游客
    	<a href="<%=request.getContextPath()%>/login_index.html" target="_parent" >登录</a>
    	</s:else>		 		    	
    </div>
</div>
</body>
</html>
