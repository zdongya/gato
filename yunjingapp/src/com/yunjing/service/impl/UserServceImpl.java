package com.yunjing.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yunjing.dao.UserDao;
import com.yunjing.model.User;
import com.yunjing.service.UserService;
import com.yunjing.util.CallResult;
import com.yunjing.util.CheckUtil;
import com.yunjing.util.Constants;
import com.yunjing.util.DateUtil;
import com.yunjing.util.Md5Util;
import com.yunjing.util.Utils;
import com.yunjing.util.WechatUser;
@Service(value = "userService")
public class UserServceImpl implements UserService {
	private static Logger logger = Logger.getLogger(UserServceImpl.class);
	@Autowired
	private UserDao userDao;

	@Override
	@Transactional
	public CallResult register(User user) {
		CallResult result = new CallResult();
		if (CheckUtil.isNullString(user.getUserId())){
			int type = user.getItype();
			if (type == 1){ //微信注册
				User userDb = userDao.findUserByWechat(user.getWechatId());
				if (null != userDb){
					logger.info("微信用户【" + user.getWechatId() + "】注册失败，已存在用户不能重复注册");
					result.setCode("-2000");
					result.setDesc("该微信用户已经在系统注册过");
				}
			} else if (type == 0){
				int count = userDao.getCountByMobile(user.getMobileNo());
				if (count != 0){
					logger.info("用户【" + user.getMobileNo() + "】注册失败，已存在用户不能重复注册");
					result.setCode("-3000");
					result.setDesc("您的手机号已经在系统注册过，不能重复注册");
				}
			} else {
				result.setCode("-4000");
				result.setDesc("错误的注册方式");
			}
			if (result.getCode().equals("10000")){ //可以注册
				user.setUserId(Utils.getUUID());
				user.setToken(Utils.createToken(user.getAppId(), user.getUserId()));
				user.setOverTime(Utils.getTokenOverTime());
				userDao.saveUser(user);
				result.setToken(user.getToken());
				result.setUserId(user.getUserId());
				result.setDesc("注册成功");
				logger.info("注册成功，用户【微信号：" + user.getWechatId() + ",手机号：" + user.getMobileNo() + ",token:" + user.getToken() + "】");
				
			}
		} else {
			result.setCode("-1000");
			result.setDesc("用户已存在");
		}
		
		return result;
	}

	@Override
	public CallResult loginByToken(String token) {
		CallResult result = new CallResult();
		User user = userDao.findUserByToken(token);
		if (null == user){
			result.setCode("-9999");
			result.setDesc("token不存在或已过期");
		} else {
			result.setUserId(user.getUserId());
			result.setToken(token);
			result.setDesc("登录成功");;
		}
		return result;
	}

	@Override
	public User getUserInfo(String userId) {
		return userDao.getUserById(userId);
	}

	@Override
	@Transactional
	public CallResult loginByWechat(WechatUser wechatUser) {
		String loginDate = DateUtil.getNowDateTime();
		CallResult result = new CallResult();
		User user = userDao.findUserByWechat(wechatUser.getUnionid());
		if (null == user){ //不存在
			user = new User();
			user.setWechatId(wechatUser.getUnionid());
			user.setNickName(wechatUser.getNickname());
			user.setIcoin(wechatUser.getHeadimgurl());
			user.setXmAppId(wechatUser.getXmAppId());
			user.setLoginDate(loginDate);
			user.setAppType(wechatUser.getAppType());
			user.setItype(1); //微信用户
			
//			user.setPhoneModel(wechatUser.getPhoneModel());
//			user.setPhoneSystem(wechatUser.getPhoneSystem());
//			user.setPhoneBrand(wechatUser.getPhoneBrand());
			
			result = register(user);
			if (result.getCode().equals("10000")){
				result.setDesc("登录成功");
			} else {
				result.setDesc("登录失败");
			}
		} else { //已经存在则直接登录
			String token = Utils.createToken(user.getAppId(), user.getUserId());
			String overTime = Utils.getTokenOverTime();
			String xmAppId = wechatUser.getXmAppId();
			userDao.updateTokenAfterLogin(user.getUserId(), wechatUser.getAppType(), token, overTime, xmAppId, loginDate);
			result.setToken(token);
			result.setUserId(user.getUserId());
			logger.info("微信用户【userid:" + user.getUserId() + ",nickname:" + user.getNickName() + "】登录成功" );
		}
		return result;
	}

	@Override
	public CallResult uploadImg(String userId, String imgUrl) {
		CallResult result = new CallResult();
		userDao.updateHeadImg(userId, imgUrl);
		result.setDesc("上传头像成功");
		return result;
	}

	@Override
	public boolean checkToken(String token, String userId) {
		User user = userDao.findUserByTokenAndUserId(token, userId);
		return null == user?false:true;
	}

	@Override
	@Transactional
	public CallResult loginByMobile(String appType, String mobileNo, String password, String ... appIds) {
		String loginDate = DateUtil.getNowDateTime();
		CallResult result = new CallResult();
		boolean flag = userDao.checkUserMobile(mobileNo);
		if (flag){
			User user = userDao.getMobLoginUser(mobileNo, password);
			if (null == user){
				result.setCode("-8888");
				result.setDesc("密码错误");
			} else {
				result.setDesc("登录成功");
				String token = Utils.createToken(user.getAppId(), user.getUserId());
				String overTime = Utils.getTokenOverTime();
				String xmAppId = appIds[0];
				userDao.updateTokenAfterLogin(user.getUserId(), appType, token, overTime, xmAppId, loginDate);
				result.setToken(token);
				result.setUserId(user.getUserId());
				logger.info("手机用户【mobileNo:" + mobileNo + "】登录成功" );
			}
		} else {
			result.setCode("-1111");
			result.setDesc("您的手机号还未注册");
		}
		return result;
	}

	@Override
	@Transactional
	public CallResult addSms(String mobileNo, int type) {
		CallResult result = new CallResult();
		String yzm = CheckUtil.randomNum();
		if (!CheckUtil.isNullString(yzm)){
			String smsContent = Constants.getSmsContentByType(type, yzm);
			String service = Constants.getSmsService();
			userDao.addSms(mobileNo, yzm, smsContent, service ,type);
			result.setDesc("发送短信成功");
		} else {
			result.setCode("-2000");
			result.setDesc("未知的业务类型");
		}
		return result;
	}

	@Override
	@Transactional
	public CallResult registerMobileUser(String mobileNo, String pwd, String yzm, String[] appIds, String appType) {
		CallResult result = new CallResult();
		boolean isRightYzm = userDao.checkYzm(mobileNo, yzm, 0);
		if (isRightYzm){
			User user = new User();
			user.setItype(0);
			user.setMobileNo(mobileNo);
			String passWord = Md5Util.getMD5Str(pwd + Constants.md5Key); //加密结果
			user.setPassword(passWord);
			user.setXmAppId(appIds[0]);
			user.setAppType(appType);
			user.setNickName(mobileNo); //手机号作为昵称
			result = register(user);
			if (result.getCode().equals("10000")){ //注册成功
				result = loginByMobile(appType, mobileNo, passWord, appIds);
			}
		} else {
			result.setCode("-1001");
			result.setDesc("验证码错误");
		}
		return result;
	}

	@Override
	@Transactional
	public CallResult forgetSetPwd(String mobileNo, String yzm, String password, String appType, String[] appIds) {
		CallResult result = new CallResult();
		boolean isRightYzm = userDao.checkYzm(mobileNo, yzm, 1);
		if (isRightYzm){ //验证码正确
			String pwd = Md5Util.getMD5Str(password + Constants.md5Key); //密码加密
			userDao.updatePwd(mobileNo,pwd);
			result = loginByMobile(appType, mobileNo, pwd, appIds);
		} else {
			result.setCode("-1000");
			result.setDesc("验证码不正确");
		}
		return result;
	}

	@Override
	@Transactional
	public CallResult updateNickName(String userId, String nickName) {
		CallResult result = new CallResult();
		userDao.updateNickName(userId, nickName);
		result.setDesc("更新昵称成功");
		return result;
	}
	
	
	@Override
	@Transactional
	public CallResult updateXmAppId(String userId, String appType, String xmAppId) {
		CallResult result = new CallResult();
		userDao.updateXmAppId(userId, appType, xmAppId);
		return result;
	}
	
}
