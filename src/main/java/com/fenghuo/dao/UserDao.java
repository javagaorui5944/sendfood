package com.fenghuo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.fenghuo.domain.customer;
import com.fenghuo.domain.staff;

@Component
public interface UserDao {
	public static String STAFF = "staff_id,staff_name,staff_tel,staff_password,staff_rank,staff_manage_partid,staff_email,staff_join_time,staff_status";
    public static String CUSTOMER = "customer_id,school_id,building_id,dormitory_id,customer_name,customer_tel,customer_money,customer_email";
	
    /*
	 * 删除用户及相关联的信息 通过staff_id
	 *@justin
	 * */	
	@Delete("delete a,b from staff as a left join staff_role as b on a.staff_id=b.staff_id where a.staff_id=#{staff_id}")
	public int deleteUserAll(int staff_id);
    
    /*	
	 * 列出所有用户信息
	 *
	 * 	
	*/
	@Select("select staff_id,staff_name from staff where staff_name like concat(#{staff_name}, '%')  and staff_status = 10")
	List<staff> getStuffsBySearchName(@Param("staff_name")String staff_name);
	
	/*
	 * 列出所有用户id与姓名
	 *
	 * */	
	
	@Select("select "+STAFF+" from staff limit #{0},#{1}")
	List<staff> getStaffList(long page,int pageSize);
	
	
	/*
	 * 列出所有用户数量
	 *
	 * */	
	
	@Select("select count(*) from staff")
	long getCountStaffList();
	
	/*
	 * 删除用户
	 *@param 用户id
	 * */	
//	@Delete("delete t1,t2 from staff t1 left join staff_role t2 on t1.staff_id=t2.staff_id where t1.staff_id=#{user_id}")
	@Update("update staff set staff_status = 0 where staff_id=#{user_id}")
	public int deleteUser(int user_id);
	

	@Select("select dormitory_password from dormitory where dormitory_id = #{id}")
	public String  getPasswordByCustomerId(Long id);
	
	
	@Select("select "+CUSTOMER+"  from  customer where customer_tel = #{telphone} ")
	public customer getCustomerByTel(String telphone);
	
	@Select("select school_id,building_id,dormitory_id from  customer where customer_id = #{customer_id}")
	public customer getCustomerById(long customer_id);
	
	@Select("select "+STAFF+" from staff where staff_tel = #{telphone} and staff_status = 10")
	public staff getStaffByStffTel(String telphone);
	
	@Select("select orders.staff_id,staff_tel from orders inner join staff on orders.staff_id=staff.staff_id "
			+ " where order_id = #{0}")
	public staff getStaffByOrderId(long OrderId);
	
	
	
	
}
