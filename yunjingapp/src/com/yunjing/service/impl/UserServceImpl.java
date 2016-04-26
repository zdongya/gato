package com.yunjing.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yunjing.dao.UserDao;
import com.yunjing.model.Device;
import com.yunjing.model.User;
import com.yunjing.service.UserService;
import com.yunjing.util.CallResult;
import com.yunjing.util.CheckUtil;
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
			User userDb = userDao.findUserByWechat(user.getWechatId());
			if (null == userDb){
				int count = userDao.getCountByMobile(user.getMobileNo());
				if (count == 0){ //用户不存在，插入数据库
					user.setUserId(Utils.getUUID());
					user.setToken(Utils.createToken(user.getAppId(), user.getUserId()));
					user.setOverTime(Utils.getTokenOverTime());
					userDao.saveUser(user);
					result.setToken(user.getToken());
					result.setUserId(user.getUserId());
					result.setDesc("注册成功");
					logger.info("注册成功，用户【微信号：" + user.getWechatId() + ",手机号：" + user.getMobileNo() + ",token:" + user.getToken() + "】");
				} else {
					logger.info("用户【" + user.getMobileNo() + "】注册失败，已存在用户不能重复注册");
					result.setCode("-3000");
					result.setDesc("您的手机号已经在系统注册过，不能重复注册");
				}
			} else {
				logger.info("微信用户【" + user.getWechatId() + "】注册失败，已存在用户不能重复注册");
				result.setCode("-2000");
				result.setDesc("该微信用户已经在系统注册过");
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
		CallResult result = new CallResult();
		User user = userDao.findUserByWechat(wechatUser.getUnionid());
		if (null == user){ //不存在
			user = new User();
			user.setWechatId(wechatUser.getUnionid());
			user.setNickName(wechatUser.getNickname());
			user.setIcoin(wechatUser.getHeadimgurl());
			user.setXmAppId(wechatUser.getXmAppId());
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
			userDao.updateTokenAfterLogin(user.getUserId(), token, overTime, xmAppId);
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
}
