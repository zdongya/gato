package com.yunjing.dao;

import java.util.List;
import java.util.Map;

import com.yunjing.entity.Push;

public interface PushDao {
	
	/**
	 * 获得mqtt推送消息
	 * @return
	 */
	public List<Push> getToPushMsgs();
	
	public void updatePushResult(Map<String,String> parm);
	
	/**
	 * 获取小米推送消息
	 * @return
	 */
	public List<Push> getXmToPushMsgs();

}
