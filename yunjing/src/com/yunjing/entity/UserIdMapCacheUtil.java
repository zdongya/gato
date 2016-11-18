package com.yunjing.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.yunjing.dao.CacheDao;
import com.yunjing.util.MyBatisFactory;
/**
 * 用户userId---id映射
 * @author DONGYA
 *
 */
public class UserIdMapCacheUtil {
	private static Map<String, Integer> userIdMap ;
	private static int maxId;
	
	public static synchronized void updateUserCache(){
		if (null == userIdMap){
			maxId = 0;
			userIdMap = new HashMap<String, Integer>();
		}
		List<UserIdMap> idMaps = getIdMaps(maxId);
		System.out.println("新增用户id缓存条数：" + idMaps.size());
		for (int i=0; i<idMaps.size(); i++){
			UserIdMap idmap = idMaps.get(i);
			userIdMap.put(idmap.getUserId(), idmap.getId());
			System.out.println(idmap.getUserId() + ":" + idmap.getId());
			if (i == idMaps.size() - 1){
				maxId = idmap.getId();
			}
		}
		
	}
	
	private static List<UserIdMap> getIdMaps(int id) {
		SqlSession session = null;
		try {
			session = MyBatisFactory.getInstance().openSession();
			CacheDao cacheDao = session.getMapper(CacheDao.class);
			return cacheDao.getUserIdMap(id);
		} catch (Exception e){
			System.out.println("查询userid--id映射 列表失败---");
			e.printStackTrace();
			return null;
		} finally {
			if (null != session){
				session.close();
			}
		}
	}

	public static String getId(String userId){
		try {
			return String.valueOf(userIdMap.get(userId));
		} catch (Exception e) {
			e.printStackTrace();
			return "正在获取";
		}
	}
}
