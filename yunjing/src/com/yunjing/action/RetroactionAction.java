package com.yunjing.action;

import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.yunjing.entity.RetroactionDto;
import com.yunjing.service.RetroactionService;
import com.yunjing.util.CallResult;
import com.yunjing.util.ExcelUtils;
import com.yunjing.util.Utils;

public class RetroactionAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	private RetroactionService retroactionService;
	private List<?> retroactions;
	private RetroactionDto retroaction;
	
	
	
	/**
	 * 查询所有反馈
	 * @return
	 */
	public String index(){
		try {
			retroactions = retroactionService.getAllPaging(retroaction);
			return "index";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		
	}
	
	//更新备注
	public void updateMemo(){
		CallResult result = new CallResult();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			String memo  = URLDecoder.decode(retroaction.getMemo(),"UTF-8");
			retroactionService.alterMemo(retroaction.getId(), memo);
			result.setDesc("更新备注成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode("-1000");
			result.setDesc("更新备注失败，系统错误");
		} finally {
			Utils.writerJsonResult(response, result);
		}
	}

	
	public void exportExcel(){
		CallResult result = new CallResult();
		try {
			retroactions = retroactionService.getAll(retroaction);
			if (null != retroactions && retroactions.size() > 0){
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setHeader("Connection", "close");  
			    response.setHeader("Content-Type", "application/vnd.ms-excel;charset=UTF-8"); 
				OutputStream out = response.getOutputStream();
				String excelName = "用户反馈报表";  
				//转码防止乱码  
				response.addHeader("Content-Disposition", "attachment;filename="+new String( excelName.getBytes("gb2312"), "ISO8859-1" )+".xls");
				ExcelUtils.exportExcel(excelName, retroactions, out, 4);
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
	
	
	public List<?> getRetroactions() {
		return retroactions;
	}
	public void setRetroactions(List<?> retroactions) {
		this.retroactions = retroactions;
	}
	public RetroactionDto getRetroaction() {
		return retroaction;
	}
	public void setRetroaction(RetroactionDto retroaction) {
		this.retroaction = retroaction;
	}
	public void setRetroactionService(RetroactionService retroactionService) {
		this.retroactionService = retroactionService;
	}
	
	

}
