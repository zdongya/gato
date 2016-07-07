var pageii = null;
var grid = null;
$(function() {
	
	grid = lyGrid({
		pagId : 'paging',
		usePage : false,
		l_column : [  {
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
		}],
		jsonUrl : rootPath + '/device/deviceUsers.shtml',
		checkbox : false,
		serNumber : false
	});
	
});
