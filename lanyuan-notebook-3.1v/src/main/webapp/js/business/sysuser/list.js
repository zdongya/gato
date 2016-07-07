var pageii = null;
var grid = null;
$(function() {
	
	grid = lyGrid({
		pagId : 'paging',
		l_column : [ {
			colkey : "USERID",
			name : "id",
		}, {
			colkey : "NICKNAME",
			name : "用户昵称",
			isSort:true,
		}, {
			colkey : "APPTYPE",
			name : "用户来源",
			width : '90px',
			isSort:true,
			renderData : function(rowindex,data, rowdata, column) {
				if (data==0 || data=='0'){
					return "android";
				} else if (data==1 || data=='1'){ 
					return "ios";
				} else {
					return data;
				}
			}
		}, {
			colkey : "MEMO",
			name : "用户备注",
			isSort:true,
		}, {
			colkey : "MOBILENO",
			name : "用户手机号"
		}, {
			colkey : "ITYPE",
			name : "用户类型",
			width : '90px',
			isSort:true,
			renderData : function(rowindex,data, rowdata, column) {
				if (data==0 || data=='0'){
					return "手机注册";
				} else if (data==1 || data=='1'){ 
					return "微信注册";
				} else {
					return data;
				}
			}
		},{
			colkey : "REGISTERDATE",
			name : "用户注册时间",
			isSort:true,
			renderData : function(rowindex,data, rowdata, column) {
				return new Date(data).format("yyyy-MM-dd hh:mm:ss");
			}
		}, {
			colkey : "LOGINDATE",
			name : "最后登录时间",
			isSort:true
		}
//		, {
//			name : "操作",
//			renderData : function( rowindex ,data, rowdata, colkeyn) {
//				return "测试渲染函数";
//			}
//		} 
		],
		jsonUrl : rootPath + '/sysUser/findByPage.shtml',
		checkbox : true,
		serNumber : true,
		checkValue : 'USERID',
		treeGrid : {
			id: "USERID"
		}
	});
	$("#search").click("click", function() {// 绑定查询按扭
		var searchParams = $("#searchForm").serializeJson();// 初始化传参数
		grid.setOptions({
			data : searchParams
		});
	});
	$("#addAccount").click("click", function() {
		addAccount();
	});
	$("#editAccount").click("click", function() {
		editAccount();
	});
	$("#delAccount").click("click", function() {
		delAccount();
	});
	$("#permissions").click("click", function() {
		permissions();
	});
});
function editAccount() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("只能选中一个");
		return;
	}
	pageii = layer.open({
		title : "编辑",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/user/editUI.shtml?id=' + cbox
	});
}
function addAccount() {
	pageii = layer.open({
		title : "新增",
		type : 2,
		area : [ "600px", "80%" ],
		content : rootPath + '/user/addUI.shtml'
	});
}
function delAccount() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox == "") {
		layer.msg("请选择删除项！！");
		return;
	}
	layer.confirm('是否删除？', function(index) {
		var url = rootPath + '/user/deleteEntity.shtml';
		var s = CommnUtil.ajax(url, {
			ids : cbox.join(",")
		}, "json");
		if (s == "success") {
			layer.msg('删除成功');
			grid.loadData();
		} else {
			layer.msg('删除失败');
		}
	});
}
function permissions() {
	var cbox = grid.getSelectedCheckbox();
	if (cbox.length > 1 || cbox == "") {
		layer.msg("请选择一个对象！");
		return;
	}
	var url = rootPath + '/resources/permissions.shtml?userId='+cbox;
	pageii = layer.open({
		title : "分配权限",
		type : 2,
		area : [ "700px", "80%" ],
		content : url
	});
}