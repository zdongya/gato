package com.yunjing.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

import com.yunjing.dao.CustomerDao;
import com.yunjing.entity.AbstractUser;
import com.yunjing.entity.Customer;
import com.yunjing.service.CustomerService;
import com.yunjing.util.DateUtil;

public class CustomerServiceImpl implements CustomerService {

	private CustomerDao customerDao;

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public Customer getById(String userId) {
		return this.customerDao.getById(userId);
	}

	@Override
	@Transactional
	public void save(Customer customer) {
		this.customerDao.save(customer);
	}

	@Override
	@Transactional
	public void update(Customer customer) {	
		customer.setAddDate(DateUtil.getNowDateToTimestamp());
		Customer dbCustomer = this.getById(customer.getId());
		BeanUtils.copyProperties(customer, dbCustomer);
		this.customerDao.update(dbCustomer);
	}

	@Override
	public List<Customer> getAll() {
		return this.customerDao.getAll();
	}

	@Transactional
	public void delete(String id) {
		this.customerDao.delete(id);
	}

	@Override
	@Transactional
	public void delete(String[] ids) {
		for (String id : ids) {
			Customer customer = this.getById(id);
			customer.setStatus(AbstractUser.Status.UNAVAILABLE.ordinal());
		}
	}
}
