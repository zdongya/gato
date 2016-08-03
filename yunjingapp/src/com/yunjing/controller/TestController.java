package com.yunjing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 数据查询控制器
 * @author DONGYA
 *
 */
@Controller
public class TestController {
	
	@RequestMapping(value="/testSendSms")
	public @ResponseBody String testSendSms(@RequestParam(value="userCode", required=false) String userCode,@RequestParam(value="userPass",required=false) String userPass,
			@RequestParam(value="Msg", required=false) String Msg,
			@RequestParam(value="Channel", required=false) String Channel,@RequestParam(value="DesNo", required=false) String DesNo){
		System.out.println("userCode:" + userCode);
		System.out.println("userPass:" + userPass);
		System.out.println("Msg:" + Msg);
		System.out.println("Channel:" + Channel);
		System.out.println("DesNo:" + DesNo);
		return Msg;
	}
}
