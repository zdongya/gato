package com.yunjing.action;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.yunjing.entity.WarningInfoDto;
import com.yunjing.service.WarningInfoService;
import com.yunjing.util.CallResult;
import com.yunjing.util.ExcelUtils;

public class WarningInfoAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private WarningInfoService warningInfoService;
	private List<?> warnings;
	private WarningInfoDto warningInfo;
	
	/**
	 * 查询所有报警信息
	 * @return
	 */
	public String index(){
		try {
			warnings = warningInfoService.getAllPaging(warningInfo);
			return "index";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		
	}
	
	public void exportExcel(){
		CallResult result = new CallResult();
		try {
			warnings = warningInfoService.getAll(warningInfo);
			if (null != warnings && warnings.size() > 0){
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setHeader("Connection", "close");  
			    response.setHeader("Content-Type", "application/vnd.ms-excel;charset=UTF-8"); 
				OutputStream out = response.getOutputStream();
				String excelName = "报警信息报表";  
				//转码防止乱码  
				response.addHeader("Content-Disposition", "attachment;filename="+new String( excelName.getBytes("gb2312"), "ISO8859-1" )+".xls");
				ExcelUtils.exportExcel(excelName, warnings, out, 2);
				out.close();
			} else {
				result.setDesc("暂时");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode("-1000");
			result.setDesc("导出excel失败，系统错误");
		}  
		
	}

	public void setWarningInfoService(WarningInfoService warningInfoService) {
		this.warningInfoService = warningInfoService;
	}

	public List<?> getWarnings() {
		return warnings;
	}

	public void setWarnings(List<?> warnings) {
		this.warnings = warnings;
	}

	public WarningInfoDto getWarningInfo() {
		return warningInfo;
	}

	public void setWarningInfo(WarningInfoDto warningInfo) {
		this.warningInfo = warningInfo;
	}
	
	
	
}
