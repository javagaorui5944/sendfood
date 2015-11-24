package com.fenghuo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenghuo.dao.CustomerDao;
import com.fenghuo.domain.Order1;
import com.fenghuo.domain.OrderDetails1;
import com.fenghuo.domain.OrderDetails2;
import com.fenghuo.domain.customer;
import com.fenghuo.domain.orders;
import com.fenghuo.domain.staff;

@Service
public class CustomerService {

@Autowired
private CustomerDao customerdao;

	public List<customer> getcustomer(long id){
		return customerdao.getcustomer(id);
	}
	
	public int updatecustomer(customer customer){
		return customerdao.updatecustomer(customer);
	}
	
	public List<String> getPhones(long dormitory_id){
		return customerdao.getPhones(dormitory_id);
	}
	
	public int updatepassword(long customer_id,String Newdormitory_password1){
		return customerdao.updatepassword(Newdormitory_password1, customer_id);
	}
	public String selectpassword(Long id){
		return customerdao.getpaswsword(id);
	}
	
	public void addCustomer(customer customer){
		customerdao.addCustomer(customer);
	}
	
	public int changeCustomer(int customer_status,long customer_id){
		return customerdao.changeCustomer(customer_status,customer_id);
	}
	
	public int updateCustomer(customer customer){
		return customerdao.updateCustomer(customer);
	}
	public List<Order1> selectOrdersByDormitory_id(long customer_id){
		return customerdao.selectOrdersByDormitory_id(customer_id);
	}
	
	public staff getStaffBystaffId(long staff_id){
		return customerdao.getStaffBystaffId(staff_id);
	}
	public orders getOrderByorder_Id(long order_id){
		return customerdao.getOrderByorder_Id(order_id);
	}
	public List<OrderDetails1> selectOrderDetails1(long order_id){
		return customerdao.selectOrderDetails1(order_id);
	}
	public List<OrderDetails2> selectOrderDetails2(long order_id){
		return customerdao.selectOrderDetails2(order_id);
	}
}
