package com.yunjing.action;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.yunjing.entity.OperatorLogDto;
import com.yunjing.service.OperatorLogService;
import com.yunjing.util.CallResult;
import com.yunjing.util.ExcelUtils;

public class OperatorLogAction extends BaseAction{
	private OperatorLogService operatorLogService;
	private List<?> operatorLogs;
	private OperatorLogDto log;
	
	
	/**
	 * 查询所有日志
	 * @return
	 */
	public String index(){
		try {
			operatorLogs = operatorLogService.getLogPaging(log);
			return "index";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		
	}
	
	/**
	 * 查询所有日志
	 * @return
	 */
	public String query(){
		try {
			operatorLogs = operatorLogService.getLogPaging(log);
			return "search";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		
	}
	
	
	public void exportExcel(){
		CallResult result = new CallResult();
		try {
			operatorLogs = operatorLogService.getAll(log);
			if (null != operatorLogs && operatorLogs.size() > 0){
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setHeader("Connection", "close");  
			    response.setHeader("Content-Type", "application/vnd.ms-excel;charset=UTF-8"); 
				OutputStream out = response.getOutputStream();
				String excelName = "操作日志报表";  
				//转码防止乱码  
				response.addHeader("Content-Disposition", "attachment;filename="+new String( excelName.getBytes("gb2312"), "ISO8859-1" )+".xls");
				ExcelUtils.exportExcel(excelName, operatorLogs, out, 3);
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

	public List<?> getOperatorLogs() {
		return operatorLogs;
	}

	public void setOperatorLogs(List<?> operatorLogs) {
		this.operatorLogs = operatorLogs;
	}


	public OperatorLogDto getLog() {
		return log;
	}

	public void setLog(OperatorLogDto log) {
		this.log = log;
	}

	public void setOperatorLogService(OperatorLogService operatorLogService) {
		this.operatorLogService = operatorLogService;
	}


	
	
}
