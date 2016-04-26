package com.yunjing.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;

import com.yunjing.dao.PushDao;
import com.yunjing.entity.Push;
import com.yunjing.util.CallResult;
import com.yunjing.util.DateUtil;
import com.yunjing.util.MyBatisFactory;
/**
 * 消息推送service
 * @author DONGYA
 *
 */
public class PushService {
	private Log log =  LogFactory.getLog("pushService");
	
	public CallResult updatePushResult(String msgId, String istate){
		SqlSession session = null;
		CallResult callResult = new CallResult();
		
		try {
			session = MyBatisFactory.getInstance().openSession();
			PushDao pushDao = session.getMapper(PushDao.class);
			String pushDate = DateUtil.getNowDateTime();
			Map<String, String> parm = new HashMap<String, String>();
			parm.put("msgId", msgId);
			parm.put("istate", istate);
			parm.put("pushDate", pushDate);
			pushDao.updatePushResult(parm);
			if (null != session){
				session.commit();
			}
		} catch (Exception e){
			log.error(e.getMessage(), e);
			session.rollback();
			if (callResult.getCode().equals("0000")){
				callResult.setCode("-1000");
				callResult.setDesc("更新推送状态失败");
			}
		} finally {
			if (null != session){
				session.close();
			}
		}
		return callResult;
		
	}
	
	public List<Push> getToPushMsgs(int service){
		SqlSession session = null;
		try {
			List<Push> msgs = null;
			session = MyBatisFactory.getInstance().openSession();
			PushDao pushDao = session.getMapper(PushDao.class);
			if (service == 0){
				msgs = pushDao.getToPushMsgs();
			} else {
				msgs = pushDao.getXmToPushMsgs();
			}
			return msgs;
		} catch (Exception e) {
			log.error("获取待推送消息失败。。。");
			log.error(e.getMessage(), e);
			return null;
		} finally {
			if (null != session){
				session.close();
			}
		}
		
	}
	
	public static void main(String[] args) {
		PushService service = new PushService();
		List<Push> list = service.getToPushMsgs(2);
		if (null != list && list.size()>0){
			for (Push msg:list){
				System.out.println(msg);
			}
		}
		
	}
}
