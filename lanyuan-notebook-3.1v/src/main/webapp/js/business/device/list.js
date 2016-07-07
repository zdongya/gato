var pageii = null;
var grid = null;
$(function() {
	
	grid = lyGrid({
		pagId : 'paging',
		pageSize : 5,
		l_column : [ {
			colkey : "deviceNo",
			name : "id",
			hide : true
		}, {
			colkey : "deviceNo",
			name : "设备编号",
		}, {
			colkey : "deviceName",
			name : "设备名称",
			isSort:true,
		}, {
			colkey : "online",
			name : "设备上线状态",
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
			colkey : "addDate",
			name : "注册时间",
			isSort:true,
			renderData : function(rowindex,data, rowdata, column) {
				return new Date(data).format("yyyy-MM-dd hh:mm:ss");
			}
		}, {
			colkey : "version",
			name : "版本"
		}, {
			colkey : "updateDate",
			name : "最后登记时间",
			isSort:true,
			renderData : function(rowindex,data, rowdata, column) {
				if (null == data || '' == data){
					return "";
				} else {
					return new Date(data).format("yyyy-MM-dd hh:mm:ss");
				}
				
			}
		},{
			name : "操作",
			renderData : function( rowindex ,data, rowdata, colkeyn) {
				return "<a id='userList'>绑定用户列表</a>&nbsp;&nbsp;<a id='zoneList'>防区列表</a>";
			}
		} 
		],
		jsonUrl : rootPath + '/device/findByPage.shtml',
		checkbox : false,
		serNumber : true,
		checkValue : 'deviceNo'
	});
	$("#search").click("click", function() {// 绑定查询按扭
		var searchParams = $("#searchForm").serializeJson();// 初始化传参数
		grid.setOptions({
			data : searchParams
		});
	});
	
	$("#userList").click("click", function() {
		//获取选中的值
		var id = $(this).parent().parent().children("td").eq(1).find("input").val();
		alert(id);
		userList(id);
	});
	$("#zoneList").click("click", function() {
		zoneList();
	});
});

function userList(id) {
//	pageii = layer.open({
//		title : "设备用户列表",
//		type : 2,
//		area : [ "1000px", "90%" ],
//		content : rootPath + '/device/deviceUsers.shtml?sysUserFormMap.deviceNo=' + id
//	});
	
	function queryDeviceUsers(deviceNo, deviceName){
		var pageUrl = "<%=request.getContextPath()%>/device/device_queryUsers.html?deviceNo=" + deviceNo + "&deviceName=" + encodeURI(encodeURI(deviceName));
		window.open(pageUrl, "newwindow", "height=600, width=1000, toolbar= no, menubar=no, scrollbars=no, resizable=no, location=no, status=no");
	}
}