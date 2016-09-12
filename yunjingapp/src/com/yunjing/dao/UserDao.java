package com.yunjing.dao;

import com.yunjing.model.User;

public interface UserDao {
	
	
	public User getUserById(String userId);
	
	/**
	 * 保存用户
	 * @param user
	 */
	public void saveUser(User user);
	
	
    /**
     * 根据微信号查找用户
     * @param userName
     * @return
     */
    public User findUserByWechat(String wechatId);
    
    /**
     * 根据手机号查询记录条数
     * @param mobileNo
     * @return
     */
    public int getCountByMobile(String mobileNo);
    
    
    /**
     * 更新用户信息
     * @param user
     */
    public void update(User user);
    
    
    /**
     * 通过token查询用户信息
     * @param token
     * @return
     */
    public User findUserByToken(String token);
    
    
    
    /**
     * 手机号登录校验
     * @param mobileNo
     * @param password
     * @return
     */
    public boolean loginCheck(String mobileNo, String password);
    
    
    /**登录成功后更新token信息
     * @param userId
     * @param token
     * @param overTime
     */
    public void updateTokenAfterLogin(String userId, String token, String overTime, String xmAppId, String loginDate);
    
    
    /**
     * 更新用户头像
     * @param imgUrl
     */
    public void updateHeadImg(String userId,String imgUrl);

	public User findUserByTokenAndUserId(String token, String userId);

	public boolean checkUserMobile(String mobileNo);

	public User getMobLoginUser(String mobileNo, String passWord);
	
	/**
	 * 发送短信
	 * @param mobileNo:手机号
	 * @param yzm:验证码
	 * @param smsContent:短信内容
	 * @param service:短信服务商
	 * @param type:短信类型
	 */
	public void addSms(String mobileNo, String yzm, String smsContent, String service, int type);

	/**
	 * 校验短信验证码
	 * @param mobileNo
	 * @param yzm
	 * @param type:短信类型 0注册  1忘记密码
	 * @return
	 */
	public boolean checkYzm(String mobileNo, String yzm, int type);

	/**
	 * 更新用户密码(忘记密码)
	 * @param mobileNo
	 * @param pwd
	 */
	public void updatePwd(String mobileNo, String pwd);
	
	
	/**
	 * 更新用户密码(重置密码)
	 * @param mobileNo
	 * @param pwd
	 */
	public void updatePwd(String mobileNo,String oldPwd, String pwd);

	public void updateNickName(String userId, String nickName);
	

}
