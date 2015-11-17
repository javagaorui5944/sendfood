package com.fenghuo.dao;
import com.fenghuo.domain.customer;
import com.fenghuo.domain.staff;

/**
 * 过滤器用到的Dao
 * */
public interface IAuthorityDao {
	
	/**
	 * 向redis里面放入职员信息
	 * */
	public boolean addStaff(staff staff, String token);
	/**
	 * 向redis里面放用户信息
	 * */
	public boolean addCustomer(customer customer,String token);
	
	

}
