package com.yunjing.action;

import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.yunjing.entity.MemberDto;
import com.yunjing.service.MemberService;
import com.yunjing.util.CallResult;
import com.yunjing.util.ExcelUtils;
import com.yunjing.util.Utils;

public class MemberAction extends BaseAction{
	private static final String newPwd = "888888";
	private MemberService memberService;
	private List<?> members;
	private MemberDto member;
	
	/**
	 * 查询所有用户
	 * @return
	 */
	public String index(){
		try {
			members = memberService.getAllPaging(member);
			return "index";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	//重置密码
	public void updatePwd(){
		CallResult result = new CallResult();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			String pwd = Utils.getMD5Str(newPwd);
			memberService.alterMemberPwd(member.getUserId(), pwd);
			result.setDesc("设置用户备注成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode("-1000");
			result.setDesc("重置密码失败，系统错误");
		} finally {
			Utils.writerJsonResult(response, result);
		}
	}
	
	//更新备注
	public void updateMemo(){
		CallResult result = new CallResult();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			String memo  = URLDecoder.decode(member.getMemo(),"UTF-8");
			memberService.alterMemberMemo(member.getUserId(), memo);
			result.setDesc("重置密码成功");
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode("-1000");
			result.setDesc("重置密码失败，系统错误");
		} finally {
			Utils.writerJsonResult(response, result);
		}
	}
	
	public void exportExcel(){
		CallResult result = new CallResult();
		try {
			members = memberService.getAll(member);
			if (null != members && members.size() > 0){
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setHeader("Connection", "close");  
			    response.setHeader("Content-Type", "application/vnd.ms-excel;charset=UTF-8"); 
				OutputStream out = response.getOutputStream();
				String excelName = "用户信息报表";  
				//转码防止乱码  
				response.addHeader("Content-Disposition", "attachment;filename="+new String( excelName.getBytes("gb2312"), "ISO8859-1" )+".xls");
				ExcelUtils.exportExcel(excelName, members, out, 0);
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


	public MemberDto getMember() {
		return member;
	}

	public void setMember(MemberDto member) {
		this.member = member;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public List<?> getMembers() {
		return members;
	}

	public void setMembers(List<?> members) {
		this.members = members;
	}
	
	
}
