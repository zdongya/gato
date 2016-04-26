package com.yunjing.service;

import com.yunjing.model.User;
import com.yunjing.util.CallResult;
import com.yunjing.util.WechatUser;
/**
 * 用户模块serivce
 * @author DONGYA
 *
 */
public interface UserService {
	
	/**
	 * 注册
	 * @param user
	 * @return
	 */
	public CallResult register(User user);
	
	
	/**
	 * 使用token登录
	 * @param token
	 * @return
	 */
	public CallResult loginByToken(String token);
	
	
	/**获取用户信息
	 * @param userId
	 * @return
	 */
	public User getUserInfo(String userId);
	
	
	
	/**
	 * 微信登录，若已存在则登录，不存在则注册
	 * @param wechatId
	 * @param appId
	 * @return
	 */
	public CallResult loginByWechat(WechatUser wechatUser);
	
	
	public CallResult uploadImg(String userId, String imgUrl);
	
	public boolean checkToken(String token, String userId);
	
	
	
}
