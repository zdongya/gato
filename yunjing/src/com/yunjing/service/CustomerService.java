package com.yunjing.service;

import java.util.List;

import com.yunjing.entity.Customer;

public interface CustomerService {

	public abstract void save(Customer customer);

	public abstract void update(Customer customer);

	public abstract Customer getById(String userid);

	public abstract void delete(String id);
	
	public abstract void delete(String[] ids);
	
	public abstract List<Customer> getAll();

}
