package com.fenghuo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.fenghuo.domain.customer;
import com.fenghuo.domain.school;


@Component
public interface SchoolDao {
	
	/*
	 * 根据学校编号查找学校code
	 * */
	@Select("select school_code,school_name from school where school_id=#{school_id}")
	 school getSchoolCode(long school_id);
	
	/*
	 * 根据员工编号查找其所负责的寝室里的员工
	 * */
	@Select("select customer_id,customer_name,customer_tel,customer.school_id,customer.building_id,customer.dormitory_id,customer_status,dormitory_name,school_name,"
			+ "building_name from customer inner join dormitory on customer.dormitory_id=dormitory.dormitory_id "
			+ "inner join school on customer.school_id=school.school_id "
			+ "inner join building on customer.building_id=building.building_id WHERE customer.school_id "
			+ "IN (SELECT school_id from school_staff WHERE staff_id = #{0})  and "
			+ "customer_name LIKE concat('%',#{3}, '%') ORDER BY customer_id LIMIT #{1},#{2}")
	 List<customer> getSchool_StaffByKey(long staff_id,long page,int pageSize,String value);
	 
	 /*
		 * 根据员工编号查找其所负责的寝室里的员工
		 * */
		@Select("select customer_id,customer_name,customer_tel,customer.school_id,customer.building_id,customer.dormitory_id,customer_status,dormitory_name,school_name,"
			+ "building_name from customer inner join dormitory on customer.dormitory_id=dormitory.dormitory_id "
				+ "inner join school on customer.school_id=school.school_id "
				+ "inner join building on customer.building_id=building.building_id WHERE customer.school_id "
				+ "IN (SELECT school_id from school_staff WHERE staff_id = #{0}) ORDER BY customer_id LIMIT #{1},#{2}")
		 List<customer> getSchool_Staff(long staff_id,long page,int pageSize);
		 
			@Select("select count(*) from customer WHERE customer.school_id IN (SELECT school_id from school_staff WHERE staff_id = #{0})")
			 long getCountSchool_Staff(long staff_id);
			
			@Select("select count(*) from customer WHERE customer.school_id IN (SELECT school_id from school_staff WHERE staff_id = #{0}) and "
					+ "customer_name LIKE concat('%',#{1}, '%')")
			 long getCountSchool_StaffByKey(long staff_id,String value);
			
		@Select("select school_id,school_name from school WHERE school_status = 10")
		List<school> getSchool();	
		
		@Select("select school.school_id,school_name,storage_id from school "
				+ "left join storage on school.school_id=storage.school_id WHERE school_id in (select school_id from school_staff "
				+ "where staff_id = #{staff_id}) and school_status = 10")
		List<school> getMySchool(long staff_id);
}
