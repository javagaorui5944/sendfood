package com.fenghuo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.fenghuo.domain.Order1;
import com.fenghuo.domain.OrderDetails1;
import com.fenghuo.domain.OrderDetails2;
import com.fenghuo.domain.customer;
import com.fenghuo.domain.orders;
import com.fenghuo.domain.staff;
@Component
public interface CustomerDao {
	public static String STAFF = "staff_id,staff_name,staff_tel,staff_password,staff_rank,staff_manage_partid,staff_email,staff_join_time,staff_status";
	@Select("select customer_name,customer_tel,customer_email from customer where customer_id=#{id}")
	List<customer> getcustomer(long id);
	
	
	@Update("update customer set customer_name=#{customer_name}"
			+ ",customer_tel=#{customer_tel},customer_email=#{customer_email}"
			+ " where customer_id=#{customer_id}")
	public int updatecustomer(customer customer);
	
	
	@Update("update dormitory set dormitory_password=#{0}"
			+ " where dormitory_id=(select dormitory_id from customer where customer_id=#{1})")
	public int updatepassword(String Newdormitory_password1,long customer_id);
	
	@Select("select dormitory_password from dormitory where dormitory_id=(select dormitory_id from customer where customer_id=#{customer_id})")
	String getpaswsword(long customer_id);
	
	@Select("select customer_tel from customer where dormitory_id=#{dormitory_id}")
	List<String> getPhones(long dormitory_id);

	/*
	 * 添加新用户
	 */
		@Insert("insert into customer(customer_id,school_id,building_id,dormitory_id,customer_name,customer_tel,customer_status) "
				+ "values (#{customer_id},#{school_id},#{building_id},#{dormitory_id},#{customer_name},#{customer_tel},#{customer_status})")
		public void addCustomer(customer customer);
	/*
	* 修改食品状态
	*/
	@Update("update customer set customer_status = #{0} where customer_id=#{1}")
	public int changeCustomer(int customer_status,long customer_id);
	
	@Update("update customer set customer_name=#{customer_name}"
			+ ",customer_tel=#{customer_tel}"
			+ " where customer_id=#{customer_id}")
	public int updateCustomer(customer customer);
	
	@Select("SELECT * FROM orders INNER JOIN staff ON orders.staff_id = staff.staff_id WHERE dormitory_id IN( SELECT dormitory_id FROM dormitory WHERE  dormitory_id IN(SELECT  dormitory_id FROM customer WHERE customer_id=#{customer_id} ))")
	public List<Order1> selectOrdersByDormitory_id(long customer_id);
	
	@Select("SELECT "+STAFF+" FROM staff WHERE staff_id=#{staff_id}")
	public staff getStaffBystaffId(long staff_id);
	
	@Select("SELECT * FROM orders WHERE order_id=#{order_id}")
	public orders getOrderByorder_Id(long order_id);
	
	@Select("SELECT *FROM snacks INNER JOIN order_item ON snacks.snacks_id = order_item.snacks_id WHERE  default_order_id IN (SELECT  default_order_id FROM orders WHERE order_id=#{order_id} )")
	public List<OrderDetails1> selectOrderDetails1(long order_id);
	
	@Select("SELECT * FROM snacks  INNER JOIN sell_item ON snacks.snacks_id = sell_item.snacks_id    WHERE order_id=#{order_id}")
	public List<OrderDetails2> selectOrderDetails2(long order_id);
	
	
}
