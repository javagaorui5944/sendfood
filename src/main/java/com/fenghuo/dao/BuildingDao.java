package com.fenghuo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.fenghuo.domain.building;
import com.fenghuo.domain.customer;
import com.fenghuo.domain.school;


@Component
public interface BuildingDao {
	
	/*
	 * 根据楼栋查找楼栋code
	 * */
	@Select("select building_code,building_name from building where building_id=#{building_id}")
	 building getBuildingCode(long building_id);
	
			/*
			 * 根据员工编号查找其所负责的寝室里的员工
			 * */
			@Select("select customer_id,customer_name,customer_tel,customer.school_id,customer.building_id,customer.dormitory_id,customer_status,dormitory_name,school_name,"
			+ "building_name from customer inner join dormitory on customer.dormitory_id=dormitory.dormitory_id "
					+ "inner join school on customer.school_id=school.school_id "
					+ "inner join building on customer.building_id=building.building_id WHERE customer.building_id "
					+ "IN (SELECT building_id from building_staff WHERE staff_id = #{0})  and "
					+ "customer_name LIKE concat('%',#{3}, '%') ORDER BY customer_id LIMIT #{1},#{2}")
			 List<customer> getBuilding_StaffByKey(long staff_id,long page,int pageSize,String value);
			 
			 /*
				 * 根据员工编号查找其所负责的寝室里的员工
				 * */
				@Select("select customer_id,customer_name,customer_tel,customer_email,customer.school_id,customer.building_id,customer.dormitory_id,customer_status,dormitory_name,school_name,"
			+ "building_name from customer inner join dormitory on customer.dormitory_id=dormitory.dormitory_id "
						+ "inner join school on customer.school_id=school.school_id "
						+ "inner join building on customer.building_id=building.building_id WHERE customer.building_id "
						+ "IN (SELECT building_id from building_staff WHERE staff_id = #{0}) ORDER BY customer_id LIMIT #{1},#{2}")
				 List<customer> getBuilding_Staff(long staff_id,long page,int pageSize);
				 
				 /*
					 * 根据员工编号查找其所负责的寝室里的员工
					 * */
					@Select("select count(*) from customer WHERE customer.building_id IN (SELECT building_id from building_staff WHERE staff_id = #{0})")
					 long getCountBuilding_Staff(long staff_id);
					
					/*
					 * 根据员工编号查找其所负责的寝室里的员工
					 * */
					@Select("select count(*) from customer WHERE customer.building_id IN (SELECT building_id from building_staff WHERE staff_id = #{0}) and "
							+ "customer_name LIKE concat('%',#{3}, '%')")
					 long getCountBuilding_StaffByKey(long staff_id,String value);
		
		@Select("select building_id,building_name from building WHERE school_id = #{school_id} and building_status = 10")
		List<building> getBuildingBySchool_id(long school_id);	
		
		@Select("select building.school_id,school_name,school_code from building inner join school on "
				+ "building.school_id = school.school_id WHERE building_id=#{0}")
		school getSchoolByBuilding(long buildingId);
					
}
