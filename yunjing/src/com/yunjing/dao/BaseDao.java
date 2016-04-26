package com.yunjing.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.yunjing.page.PageController;
import com.yunjing.page.PageLocal;

public class BaseDao extends HibernateTemplate{
	
	
	/**
	 * 分页查询
	 * @param queryString
	 * @return
	 */
	public List<?> getAllPaging(String queryString) {
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.createQuery(queryString);
		List<?> dps = query.list();
		PageController pageController = PageLocal.getPageLocal();// 分页
		pageController.setIndexSize(dps.size());
		query = session.createQuery(queryString);
		query.setFirstResult(pageController.getCurrentIndex());
		query.setMaxResults(pageController.getPageSize());
		return query.list();
	}
	
	

}
