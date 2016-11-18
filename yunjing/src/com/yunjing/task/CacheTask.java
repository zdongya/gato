package com.yunjing.task;

import java.util.TimerTask;

import com.yunjing.entity.UserIdMapCacheUtil;

/**
 * 缓存服务
 * @author DONGYA
 */
public class CacheTask extends TimerTask{

	@Override
	public void run() {
		System.out.println("更新用户id缓存中。。。");
		//更新用户缓存
		UserIdMapCacheUtil.updateUserCache();
	}

}
