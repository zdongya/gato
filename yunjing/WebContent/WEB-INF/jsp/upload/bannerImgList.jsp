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
	function toAddBanner(){
		window.location.href="<%=request.getContextPath()%>/upload/upload_toAdd.html";
	}
	function deleteBanner(id){
		var url = "<%=request.getContextPath()%>/upload/upload_deleteBanner.html?id=" + id; //后台处理程序  
		$.ajax({  
			 url : url,
			 type:"post",    //数据发送方式  
			 async:false,  
			 dataType:"json",   //接受数据格式             
			 error: function(){  
			 alert("服务器没有返回数据，可能服务器忙，请重试");  
			 },  

			success: function(json){
				var code = json.code;
				if (code == '0000'){
					alert('删除成功');
					window.location.reload();
				} else {
					alert(json.desc);
				}
			}
			});          

	}
</script>
</head>


<body>
<form action="<%=request.getContextPath()%>/upload/upload_index.html" method="post" id="searchForm">
<table class="searchTable">
     <tbody>
     	<tr>
		     <td> <input type="button" value="上传客户端banner图" onclick="javascript:toAddBanner();" ></input></td>
	     </tr>
     </tbody>
</table>

<table width="100%" border="0" cellpadding="0" cellspacing="0" class="dataList">
  <tr>
    <th colspan="4">
    <em>客户端banner图片列表</em>
    
    </th>
  </tr>
  <tr class="title">

	<td>序号</td>
	<td>图片名称</td>
    <td>网页地址</td>
    <td>操作</td>
  </tr>
	 
 <s:if test="null!=bannerImgs&&bannerImgs.size()>0">
 <s:iterator value="bannerImgs" id="bannerImg">
  <tr>
    <td>${bannerImg.id}</td>
    <td>${bannerImg.imgName}</td>
    <td>${bannerImg.imgHref}</td>
    <td>
	    <a href="javascript:deleteBanner('${bannerImg.id}');">删除</a>
    </td>
  </tr>
  </s:iterator>
  </s:if>
</table>
</form>
 <odb:pageController/>
</body>

</html>
<script type="text/javascript">
	var errDesc ="${sessionScope.errDesc}" 
</script>