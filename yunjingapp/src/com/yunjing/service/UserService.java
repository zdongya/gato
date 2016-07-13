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
	
	public static final String md5Key = "gato_mobile";
	
	
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
	
	/**
	 * 使用手机号登录
	 * @param mobileNo
	 * @param password
	 * @param appId:推送id  0：小米推送ID
	 * @return
	 */
	public CallResult loginByMobile(String mobileNo,String password, String ... appIds); //可变长参数


	/**
	 * 保存短信信息
	 * @param mobileNo:手机号
	 * @param type:业务类型
	 * @return
	 */
	public CallResult addSms(String mobileNo, int type);


	/**
	 * 手机号注册
	 * @param mobileNo:手机号
	 * @param pwd:密码
	 * @param yzm:短信验证码
	 * @param appIds:推送设备编号  0小米推送编号
	 * @return
	 */
	public CallResult registerMobileUser(String mobileNo, String pwd, String yzm, String[] appIds);
	
	
	
}
