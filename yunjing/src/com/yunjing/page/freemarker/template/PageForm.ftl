<form id="_hrkpi_pageController_form" action="${requestUri}" <#if requestMethod == 'post'>method="post"</#if>>
<#if requestMethod != 'post' || requestMethod != 'get'>
<input type="hidden" name="_method" value="${requestMethod}"/>
</#if>
<#list request_attributes?keys as mapKey>
<#list request_attributes[mapKey] as mapValue>
<input type="hidden" name="${mapKey}" value="${mapValue}"/>
</#list>
</#list>
<input type="hidden" id="_pageController_currentIndex" name="_pageController_currentIndex"/>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	   <td width="30%" align="left" valign="bottom"></td>
	   <td width="5%" height="25" align="left" valign="bottom"/>
	   <td width="8%" align="right" valign="bottom"><font >第<#if indexSize==0>0<#elseif indexSize!=0>${currentPage+1}<#else></#if>页/共${totalPage}页</font></td>
	   <td width="15%" align="right" valign="bottom">
	   <a href="javascript:startIndex()"><font >首页<font></a>|
	   <a href="javascript:previousIndex()"><font >上一页<font></a>|
	   <a href="javascript:nextIndex()"><font >下一页<font></a>|
	   <a href="javascript:lastIndex()"><font >尾页<font></a>
	   </td>
	   <td width="5%" align="center" valign="bottom"><input id="_goIndex" type="text" style="width:30px;height:20px"/></td>
	   <td width="3%" align="left" valign="bottom"><img src="${contextPath}/images/go.gif" width="21" height="17" onclick="javascript:redirectIndex()"/></td>
	</tr>
</table>
</form>
<script type="text/javascript">
	var form = document.getElementById("_hrkpi_pageController_form");
	var currentIndex = document.getElementById("_pageController_currentIndex");
	var redirect = document.getElementById("_goIndex");
	function startIndex()
	{
		currentIndex.value = ${startIndex};
		form.submit();
	}
	
	function lastIndex()
	{
		currentIndex.value = ${lastIndex};
		form.submit();
	}
	
	function nextIndex()
	{
		currentIndex.value = ${nextIndex};
		form.submit();
	}
	
	function previousIndex()
	{
		currentIndex.value = ${previousIndex};
		form.submit();
	}
	
	function redirectIndex()
	{
		var pageNum = redirect.value;
		
		if (testPositiveInteger(pageNum))
		{
			var result = (pageNum-1) * ${pageSize};
			currentIndex.value = result <= ${indexSize-1} ? result : ${lastIndex};
			form.submit();
		}
		else
		{
			alert("您输入的跳转页数必须为正整数！");
			redirect.value = "";
		}
	}
	
	function testPositiveInteger(pageNum)
	{
		var patrn=/^\d+$/;
		if (!patrn.exec(pageNum)) 
			return false;
		if(0==pageNum||"0"==pageNum)
			return false;
		else
			return true;
	}
</script>