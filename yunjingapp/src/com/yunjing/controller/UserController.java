package com.yunjing.controller;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yunjing.model.User;
import com.yunjing.service.UserService;
import com.yunjing.util.CallResult;
import com.yunjing.util.CheckUtil;
import com.yunjing.util.Utils;
import com.yunjing.util.WechatUser;

@Controller
public class UserController {
	private static Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	/**
	 * 微信注册或则登录接口
	 * 
	 * @param wechatId
	 * @param appId
	 * @return
	 */
	@RequestMapping(value = "/user/wechatLogin")
	public @ResponseBody CallResult wechatLogin(@ModelAttribute WechatUser wechatUser) {
		logger.info("调用微信登录接口:" + wechatUser.toString());
		return userService.loginByWechat(wechatUser);
	}

	/**
	 * token登录接口
	 * @param wechatId
	 * @param appId
	 * @return
	 */
	@RequestMapping(value = "/user/tokenLogin")
	public @ResponseBody CallResult tokenLogin(@RequestParam(value = "token") String token) {
		return userService.loginByToken(token);
	}

	@RequestMapping(value = "/user/uploadHeadImg")
	public @ResponseBody CallResult uploadHeadImg(@RequestParam(value = "file") MultipartFile file, @RequestParam(value="userId") String userId,@RequestParam(value="token") String token) {
		logger.info("开始上传图像。。。");
		CallResult result = new CallResult();
		try {
			String fileName = file.getOriginalFilename();
			String path = Utils.UPLOAD_IMG_DIR;
			File targetFile = new File(path, fileName);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			// 保存图片
	
			file.transferTo(targetFile);
			result = userService.uploadImg(userId, fileName);
			logger.info("上传头像成功");
		}catch (Exception e) {
			result.setCode("-1000");
			result.setDesc("系统错误，请稍后再试");
			logger.error(e.getMessage(), e);
			
		}
		return result;
	}
	
	
	
	@RequestMapping(value = "/user/mobLogin")
	public @ResponseBody CallResult mobLogin(@RequestParam(value = "mobileNo") String mobileNo, @RequestParam(value = "password") String password, 
			@RequestParam(value = "xmAppId", required=false ) String xmAppId) {
		CallResult result = new CallResult();
		try{
			logger.info("手机用户:" + mobileNo + "正在登录。。。");
			if (CheckUtil.isNullString(xmAppId)){
				xmAppId = "";
			}
			String[] appIds = new String[]{xmAppId};
			result = userService.loginByMobile(mobileNo, password, appIds);
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			result.setCode("-10000");
			result.setDesc("系统异常");
		}
		return result;
	}
	
	
	/**
	 * @param mobileNo:手机号
	 * @param type:验证码类型  0注册验证码  1找回密码验证码
	 * @return
	 */
	@RequestMapping(value = "/user/sendYzm")
	public @ResponseBody CallResult sendYzm(@RequestParam(value = "mobileNo") String mobileNo, @RequestParam(value = "type") int type) {
		CallResult result = new CallResult();
		try {
			logger.info("手机用户:" + mobileNo + "获取验证码中。。。");
			if (Utils.isMobilephone(mobileNo)){
				result = userService.addSms(mobileNo, type);
			} else {
				result.setCode("-1000");
				result.setDesc("手机格式不正确");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setCode("-10000");
			result.setDesc("系统异常");
		}
		return result;
	}
	
	
	@RequestMapping(value = "/user/mobRegister")
	public @ResponseBody CallResult mobRegister(@ModelAttribute User user, @RequestParam(value = "yzm") String yzm) {
		CallResult result = new CallResult();
		try {
			logger.info("手机用户:" + user.getMobileNo() + "注册中。。。,xmAppId:" + user.getXmAppId());
			if (CheckUtil.isNullString(user.getMobileNo()) || CheckUtil.isNullString(user.getPassword()) || CheckUtil.isNullString(user.getAppType()) || CheckUtil.isNullString(yzm)){
				result.setCode("-1000");
				result.setDesc("参数错误");
			} else {
				if (CheckUtil.isNullString(user.getXmAppId())){
					user.setXmAppId("");
				}
				String[] appIds = new String[]{user.getXmAppId()};
				result = userService.registerMobileUser(user.getMobileNo(), user.getPassword(), yzm, appIds, user.getAppType());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setCode("-10000");
			result.setDesc("系统异常");
		}
		return result;
		
	}
	
	

}
