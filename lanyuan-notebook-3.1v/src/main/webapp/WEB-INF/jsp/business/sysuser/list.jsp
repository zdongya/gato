<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/business/sysuser/list.js"></script>
<%-- 时间控件 -------- 可能导致样式问题,和某些js事件失效 如加上,,自行处理 --%>
<link rel="stylesheet" type="text/css" media="all" href="${pageContext.request.contextPath}/js/date/daterangepicker-bs3.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/date/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/date/moment.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/date/daterangepicker.js"></script>

<script type="text/javascript">
       $(document).ready(function() {
          $('#reservation').daterangepicker(null, function(start, end, label) {
            console.log(start.toISOString(), end.toISOString(), label);
          });
       });
</script>



	<div class="m-b-md">
		<form class="form-inline" role="form" id="searchForm"
			name="searchForm">
			<div class="form-group">
				<label class="control-label"> <span
					class="h4 font-thin v-middle">昵称:</span></label> 
					<input class="input-medium ui-autocomplete-input" id="nickName" name="sysUserFormMap.NICKNAME">
			</div>
			
			<div class="form-group">
				<label class="control-label"> <span
					class="h4 font-thin v-middle">注册时间:</span></label> 
					
			</div>
			
            <div class="input-prepend input-group">
              <span class="add-on input-group-addon"><i class="glyphicon glyphicon-calendar fa fa-calendar"></i></span><input type="text" readonly style="width: 200px" name="twoDate" id="reservation" class="form-control" value="" /> 
            </div>
			
			<a href="javascript:void(0)" class="btn btn-default" id="search">查询</a>
			<a href="javascript:grid.exportData('/sysUser/export.shtml')" class="btn btn-info" id="search">导出excel</a>
		</form>
	</div>
	<header class="panel-heading">
	<div class="doc-buttons">
		<c:forEach items="${res}" var="key">
			${key.description}
		</c:forEach>
	</div>
	</header>
	<div class="table-responsive">
		<div id="paging" class="pagclass"></div>
	</div>
