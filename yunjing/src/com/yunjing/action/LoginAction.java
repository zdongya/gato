package com.yunjing.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.yunjing.entity.AbstractUser;
import com.yunjing.entity.Customer;
import com.yunjing.service.CustomerService;
import com.yunjing.util.CallResult;
import com.yunjing.util.CheckUtil;
import com.yunjing.util.Utils;

public class LoginAction extends BaseAction{
	private CustomerService customerService;
	private String newPwd;
	
	public String index(){
		return "toLogin";
	}
	
	public String login(){
		return "success";
	}
	
	//重置密码
	public void updatePwd(){
		CallResult result = new CallResult();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			if (!CheckUtil.isNullString(newPwd)){
				Customer user =  (Customer)getLoginUser();
				user.setPassword(newPwd);
				customerService.update(user);
				result.setDesc("修改用户密码成功");
			} else {
				result.setCode("-2000");
				result.setDesc("新密码不能为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode("-1000");
			result.setDesc("重置密码失败，系统错误");
		} finally {
			Utils.writerJsonResult(response, result);
		}
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

}
