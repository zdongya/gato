package com.yunjing.util;

import java.io.OutputStream;
import java.util.List;

import com.yunjing.entity.Device;
import com.yunjing.entity.Member;
import com.yunjing.entity.OperatorLog;
import com.yunjing.entity.Retroaction;
import com.yunjing.entity.WarningInfo;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelUtils {

	/**
	 * 导出excel
	 * @param excelName
	 * @param headers
	 * @param members
	 * @param out
	 * @param date
	 */
	public static void exportExcel(String excelName, List<?> datas, OutputStream out, int type) throws Exception{
		
		WritableWorkbook  wwb = Workbook.createWorkbook(out);
		if (null != datas && datas.size() > 0){
			WritableSheet ws = wwb.createSheet(excelName, 0);
			if (type == 0){ //导出用户信息
				ws.addCell(new Label(0, 0, "用户昵称"));
				ws.addCell(new Label(1, 0, "用户备注"));
				ws.addCell(new Label(2, 0, "用户手机号"));
				ws.addCell(new Label(3, 0, "用户微信号"));
				ws.addCell(new Label(4, 0, "用户类型"));
				ws.addCell(new Label(5, 0, "用户邮箱"));
				ws.addCell(new Label(6, 0, "用户注册时间"));
				ws.addCell(new Label(7, 0, "用户编号"));
				for (int i = 0; i < datas.size(); i++){
					Member memberDto = (Member)datas.get(i);
					if (i < 65535) {
						ws.addCell(new Label(0, i + 1, memberDto.getNickName()));
						ws.addCell(new Label(1, i + 1, memberDto.getMemo()));
						ws.addCell(new Label(2, i + 1, memberDto.getMobileNo()));
						ws.addCell(new Label(3, i + 1, memberDto.getWechatId()));
						ws.addCell(new Label(4, i + 1, memberDto.getTypeName()));
						ws.addCell(new Label(5, i + 1, memberDto.getEmail()));
						ws.addCell(new Label(6, i + 1, DateUtil.sqlTimeStatmpToString(memberDto.getRegisterDate())));
						ws.addCell(new Label(7, i + 1, String.valueOf(memberDto.getId())));
					}
				}
			} else if (type == 1){ //导出设备信息
				ws.addCell(new Label(0, 0, "设备序列号"));
				ws.addCell(new Label(1, 0, "设备名称"));
				ws.addCell(new Label(2, 0, "注册时间"));
				ws.addCell(new Label(3, 0, "设备地址"));
				ws.addCell(new Label(4, 0, "版本"));
				ws.addCell(new Label(5, 0, "联系人"));
				ws.addCell(new Label(6, 0, "联系电话"));
				ws.addCell(new Label(7, 0, "地址"));
				ws.addCell(new Label(8, 0, "最后登记时间"));
				ws.addCell(new Label(9, 0, "设备状态"));
				for (int i = 0; i < datas.size(); i++){
					Device device = (Device)datas.get(i);
					if (i < 65535) {
						ws.addCell(new Label(0, i + 1, device.getDeviceNo()));
						ws.addCell(new Label(1, i + 1, device.getDeviceName()));
						ws.addCell(new Label(2, i + 1, DateUtil.sqlTimeStatmpToString(device.getAddDate())));
						ws.addCell(new Label(3, i + 1, device.getDeviceLocal()));
						ws.addCell(new Label(4, i + 1, device.getVersion()));
						ws.addCell(new Label(5, i + 1, device.getContactPerson()));
						ws.addCell(new Label(6, i + 1, device.getCellphone()));
						ws.addCell(new Label(7, i + 1, device.getAddress()));
						ws.addCell(new Label(8, i + 1, DateUtil.sqlTimeStatmpToString(device.getUpdateDate())));
						ws.addCell(new Label(9, i + 1, device.getOnlineState()));
					}
				}
			} else if (type == 2){ //导出报警信息列表
				
				ws.addCell(new Label(0, 0, "报警编号"));
				ws.addCell(new Label(1, 0, "防区编号"));
				ws.addCell(new Label(2, 0, "报警时间"));
				ws.addCell(new Label(3, 0, "处理结果"));
				ws.addCell(new Label(4, 0, "报警类型"));
				ws.addCell(new Label(5, 0, "处理人昵称"));
				ws.addCell(new Label(6, 0, "解决时间"));
				ws.addCell(new Label(7, 0, "备注"));
				ws.addCell(new Label(8, 0, "设备名称"));
				ws.addCell(new Label(9, 0, "设备版本信息"));
				ws.addCell(new Label(10, 0, "处理人id"));
				for (int i = 0; i < datas.size(); i++){
					WarningInfo warningInfo = (WarningInfo)datas.get(i);
					if (i < 65535) {
						ws.addCell(new Label(0, i + 1, warningInfo.getWarningId()));
						String zoneNo = "";
						String deviceName = "";
						String deviceVersion = "";
						if (null != warningInfo.getZone() && !CheckUtil.isNullString(warningInfo.getZone().getZoneNo())){
							zoneNo = warningInfo.getZone().getZoneNo();
							deviceName = warningInfo.getZone().getDevice().getDeviceName();
							deviceVersion = warningInfo.getZone().getDevice().getVersion();
						}
						ws.addCell(new Label(1, i + 1, zoneNo));
						ws.addCell(new Label(2, i + 1, warningInfo.getWarnDate()));
						ws.addCell(new Label(3, i + 1, warningInfo.getStateName()));
						ws.addCell(new Label(4, i + 1, warningInfo.getWarnTypeName()));
						String nickName = "";
						if (null != warningInfo.getOperator()){
							nickName = warningInfo.getOperator().getNickName();
						}
						ws.addCell(new Label(5, i + 1, nickName));
						ws.addCell(new Label(6, i + 1, warningInfo.getHandleDate()));
						ws.addCell(new Label(7, i + 1, warningInfo.getMemo()));
						ws.addCell(new Label(8, i + 1, deviceName));
						ws.addCell(new Label(9, i + 1, deviceVersion));
						ws.addCell(new Label(10, i + 1, warningInfo.getOperator().getId() + ""));
					}
				}
				
			} else if (type == 3){ //导出操作日志列表
				ws.addCell(new Label(0, 0, "操作时间"));
				ws.addCell(new Label(1, 0, "操作用户"));
				ws.addCell(new Label(2, 0, "ip地址"));
				ws.addCell(new Label(3, 0, "设备名称"));
				ws.addCell(new Label(4, 0, "防区名称"));
				ws.addCell(new Label(5, 0, "操作描述"));
				ws.addCell(new Label(6, 0, "操作项"));
				ws.addCell(new Label(7, 0, "操作人id"));
				for (int i = 0; i < datas.size(); i++){
					OperatorLog log = (OperatorLog)datas.get(i);
					if (i < 65535) {
						String zoneName = "";
						if (null != log.getZone()){
							zoneName = log.getZone().getZoneName();
						}
						ws.addCell(new Label(0, i + 1, DateUtil.sqlTimeStatmpToString(log.getCreateTime())));
						ws.addCell(new Label(1, i + 1, log.getOperator().getNickName()));
						ws.addCell(new Label(2, i + 1, log.getIpAddr()));
						ws.addCell(new Label(3, i + 1, log.getDevice().getDeviceName()));
						ws.addCell(new Label(4, i + 1, zoneName));
						ws.addCell(new Label(5, i + 1, log.getMemo()));
						ws.addCell(new Label(6, i + 1, log.getTypeName()));
						ws.addCell(new Label(7, i + 1, log.getOperator().getId() + ""));
					}
				}
				
			} else if (type == 4){ //导出反馈列表
				ws.addCell(new Label(0, 0, "用户昵称"));
				ws.addCell(new Label(1, 0, "用户微信号"));
				ws.addCell(new Label(2, 0, "反馈时间"));
				ws.addCell(new Label(3, 0, "反馈内容"));
				ws.addCell(new Label(4, 0, "联系方式"));
				ws.addCell(new Label(5, 0, "备注"));
				ws.addCell(new Label(6, 0, "用户id"));
				for (int i = 0; i < datas.size(); i++){
					Retroaction retroaction = (Retroaction)datas.get(i);
					if (i < 65535) {
						ws.addCell(new Label(0, i + 1, retroaction.getUser().getNickName()));
						ws.addCell(new Label(1, i + 1, retroaction.getUser().getWechatId()));
						ws.addCell(new Label(2, i + 1, DateUtil.sqlTimeStatmpToString(retroaction.getAddDate())));
						ws.addCell(new Label(3, i + 1, retroaction.getContents()));
						ws.addCell(new Label(4, i + 1, retroaction.getContact()));
						ws.addCell(new Label(5, i + 1, retroaction.getMemo()));
						ws.addCell(new Label(6, i + 1, retroaction.getUser().getId() + ""));
					}
				}
			}
			
			wwb.write();
			wwb.close();
		}
		
	}

}
