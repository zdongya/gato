package com.yunjing.dao;

import java.util.List;

import com.yunjing.entity.Customer;

public interface CustomerDao {
	public abstract void save(Customer customer);

	public abstract void update(Customer customer);

	public abstract void delete(String id);

	public abstract Customer getById(String id);
	
	public abstract List<Customer> getAll();
	
	public abstract Customer getByUsername(String username);

}
