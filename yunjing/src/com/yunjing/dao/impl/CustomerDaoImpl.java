package com.yunjing.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.yunjing.dao.CustomerDao;
import com.yunjing.entity.Customer;

/**
 * @author author :dongya
 * @version time：2011-12-13 上午10:35:50
 */
public class CustomerDaoImpl extends HibernateTemplate implements CustomerDao {

	@Override
	public void delete(String id) {
		Customer customer = this.getById(id);
		super.delete(customer);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getAll() {
		String queryString = "FROM Customer AS model where model.status=1";
		return super.find(queryString);
	}

	@Override
	public Customer getById(String id) {
		return super.get(Customer.class, id);
	}

	@Override
	public void save(Customer customer) {
		super.save(customer);
	}

	@Override
	public void update(Customer customer) {
		super.update(customer);
	}

	/* 
	 * 通过用户名查找客服
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Customer getByUsername(String username) {
		List<Customer> customerList = super.find("FROM Customer as model where model.username=?", username);
		if(null!=customerList&&customerList.size()>0){
			return customerList.get(0);
		}
		return null;
	}
}
