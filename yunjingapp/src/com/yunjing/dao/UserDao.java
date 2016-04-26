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
    public void updateTokenAfterLogin(String userId, String token, String overTime, String xmAppId);
    
    
    /**
     * 更新用户头像
     * @param imgUrl
     */
    public void updateHeadImg(String userId,String imgUrl);

	public User findUserByTokenAndUserId(String token, String userId);

}
