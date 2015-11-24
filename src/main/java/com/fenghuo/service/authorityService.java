package com.fenghuo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenghuo.dao.IAuthorityDao;
import com.fenghuo.domain.customer;
import com.fenghuo.domain.staff;
@Service
public class authorityService {
	@Autowired
	private IAuthorityDao AuthorityDao;
	/**
	 * 向redis里面放用户信息
	 * */
	public boolean addStaff(staff staff, String token){
		return AuthorityDao.addStaff(staff, token);
	}
	/**
	 * 向redis里面放消费者信息
	 * */
	public boolean addCustomer(customer customer,String token){
		return AuthorityDao.addCustomer(customer,token);
	}
	
}
