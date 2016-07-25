package com.yunjing.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
import com.yunjing.util.Constants;
import com.yunjing.util.Md5Util;
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
	public @ResponseBody Map<String,String> uploadHeadImg(@RequestParam(value = "file") MultipartFile file, @RequestParam(value="userId") String userId,@RequestParam(value="token") String token) {
		logger.info("开始上传图像。。。");
		Map<String, String> map = new HashMap<String, String>();
		CallResult result = new CallResult();
		try {
			boolean loginFlag = userService.checkToken(token, userId);
			if (!loginFlag){
				result.setCode("-6666");
				result.setDesc("未登录");
			} else {
				String fileName = file.getOriginalFilename();
				logger.info("上传头像中，文件名：" + fileName);
				
				fileName = userId + "." + fileName.split("\\.")[1];
				String path = Utils.UPLOAD_IMG_DIR;
				File targetFile = new File(path, fileName);
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}
				// 保存图片
		
				file.transferTo(targetFile);
				result = userService.uploadImg(userId, Utils.IMG_URL + fileName);
				logger.info("上传头像成功");
				result.setDesc("上传头像成功");
				map.put("headImg", Utils.IMG_URL + fileName);
			}
		}catch (Exception e) {
			result.setCode("-1000");
			result.setDesc("系统错误，请稍后再试");
			logger.error(e.getMessage(), e);
			
		}
		map.put("code", result.getCode());
		map.put("desc", result.getDesc());
		return map;
	}
	
	
	//手机号登录接口
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
			String passWord = Md5Util.getMD5Str(password + Constants.md5Key); //加密结果
			result = userService.loginByMobile(mobileNo, passWord, appIds);
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
	
	
	/**
	 * @param 忘记密码重设密码接口，设置完成后直接登录
	 * @return
	 */
	@RequestMapping(value = "/user/forgeSetPwd")
	public @ResponseBody CallResult forgeSetPwd(@ModelAttribute User user, @RequestParam(value = "yzm") String yzm) {
		CallResult result = new CallResult();
		try {
			if (CheckUtil.isNullString(user.getMobileNo()) || CheckUtil.isNullString(user.getPassword()) || CheckUtil.isNullString(yzm)){
				result.setCode("-1000");
				result.setDesc("参数错误");
			} else {
				logger.info("手机用户:" + user.getMobileNo() + "忘记密码开始重设密码");
				if (CheckUtil.isNullString(user.getXmAppId())){
					user.setXmAppId("");
				}
				String[] appIds = new String[]{user.getXmAppId()};
				result = userService.forgetSetPwd(user.getMobileNo(), yzm, user.getPassword(), appIds);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setCode("-10000");
			result.setDesc("系统异常");
		}
		return result;
	}
	
}
