<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

 

<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>云警后台管理系统</title>
      <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.3.2.min.js"></script>
      <script type="text/javascript">
      	function checkValue(){
      		var password = $.trim($("#password").val());
    		var rePassword = $.trim($("#repassword").val());
    		var username = $.trim($("#username").val());
    		var realName = $.trim($("#realName").val());
    		var email = $.trim($("#email").val());
    		var cellphone = $.trim($("#cellphone").val());
    		if(null==username||""==username){
    			alert("用户名不允许为空!");
    			return false ;
    		}
    		if("admin"==username){
        		alert("admin用户已存在，请重新设置用户名!");
    			$("#username").val(null);
    			return false;
    		}
    		if(null==password||""==password){
    			alert("密码不允许为空!");
    			return false ;
    		}
    		if(null==password||""==password||null==rePassword||""==rePassword){
    			alert("原密码和重复输入密码不允许为空!");
    			$("#password").val(null);
    			$("#repassword").val(null);
    			return false;
    		}
    		if(rePassword!=password){
    			alert("两次输入密码不一致!");
    			$("#password").val(null);
    			$("#repassword").val(null);
    			return false;
    		}
    		if(null==realName||""==realName){
    			alert("用户真实姓名不允许为空!");
    			return false ;
    		}
    		if(null==cellphone||""==cellphone){
    			alert("用户手机不运行为空!");
    			return false ;
    		}
    		if(null==email||""==email){
    			alert("邮箱不允许为空!");
    			return false ;
    		}
          	document.forms[0].submit();
      	}

      	function checkUsername(){
      		var username = $.trim($("#username").val());
      		if(null==username||""==username){
    			alert("用户名不允许为空!");
 				return false;
    		}if("admin"==username){
        		alert("admin用户已存在，请重新设置用户名!");
    			$("#username").val(null);
    			return false;
    		}else{
        		var data = {"username":username};
        		$.ajax({
        			url:"<%=request.getContextPath()%>/register_checkUserName.html",
    				type: "POST",
                    dataType: "json",
                    data: data,
                    success: function(date) {
    	                if(date.message=="FAILED"){
    		               alert("该用户已经被注册，请输入新的用户名!");
    		               $("#username").val("");
    		               $("#username")[0].focus();
    	               }
    				}

            	});
    		}
      	}
      </script>
    </head>
    <body>
        		<div class="header">
        		
        		
        	</div>
        	
	
	<div class="banner"></div>
	<div class="important">
		
		<div class="reg">
			<form action="<%=request.getContextPath()%>/register_register.html" method="post">
				<table>
				<tr><td><label>用&nbsp;户&nbsp;名：</label></td><td><input type="text" id="username" name="member.username" onblur="javascript:checkUsername();" size="20px" /></td></tr>
				<tr><td><label>登录密码：</label></td><td><input type="password" id="password" name="member.password" size="25px" /></td></tr>
				<tr><td><label>确认密码：</label></td><td><input type="password" id="repassword"  size="25px" /></td></tr>
				<tr><td><label>真实姓名：</label></td><td><input type="text" id="realName" name="member.realName" size="20px" /></td></tr>
				<tr><td><label>手机号码：</label></td><td><input type="text" id="cellphone" name="member.cellphone" size="20px" /></td></tr>
				<tr><td><label>电子邮箱：</label></td><td><input type="text" id="email" name="member.email" size="20px" /></td></tr>
				<tr><td colspan="2"><span>填写常用邮箱，以方便联系及找回密码！</span></td></tr>
				<tr><td style="padding-left: 20px" ><input type="button" value="注册" onclick="return checkValue();"/></td><td style="padding-left: 20px" ><input type="reset" value="重置" /></td></tr>
				</table>
			</form>
		</div>
	</div>
    </body>
</html>