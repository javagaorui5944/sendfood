package com.fenghuo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.fenghuo.domain.building;
import com.fenghuo.domain.customer;
import com.fenghuo.domain.dormitory;

@Component
public interface DormitoryDao {
	
	/*
	 * 根据楼栋查找楼栋code
	 * */
	@Select("select dormitory_code,dormitory_name,building_id from dormitory where dormitory_id=#{dormitory_id}")
	 dormitory getDormitoryCode(long building_id);
	/*
	 * 根据员工编号查找其所负责的寝室里的员工
	 * */
	@Select("select customer_id,customer_name,customer_tel,customer.school_id,customer.building_id,customer.dormitory_id,customer_status,dormitory_name,school_name,"
			+ "building_name from customer inner join dormitory on customer.dormitory_id=dormitory.dormitory_id "
			+ "inner join school on customer.school_id=school.school_id "
			+ "inner join building on customer.building_id=building.building_id WHERE customer.dormitory_id "
			+ "IN (SELECT dormitory_id from dormitory WHERE staff_id = #{0})  and "
			+ "customer_name LIKE concat('%',#{3}, '%') ORDER BY customer_id LIMIT #{1},#{2}")
	 List<customer> getDormitory_StaffByKey(long staff_id,long page,int pageSize,String value);
	 
	 /*
		 * 根据员工编号查找其所负责的寝室里的员工
		 * */
		@Select("select customer_id,customer_name,customer_tel,customer.school_id,customer.building_id,customer.dormitory_id,customer_status,dormitory_name,school_name,"
			+ "building_name from customer inner join dormitory on customer.dormitory_id=dormitory.dormitory_id "
				+ "inner join school on customer.school_id=school.school_id "
				+ "inner join building on customer.building_id=building.building_id WHERE customer.dormitory_id "
				+ "IN (SELECT dormitory_id from dormitory WHERE staff_id = #{0}) ORDER BY customer_id LIMIT #{1},#{2}")
		 List<customer> getDormitory_Staff(long staff_id,long page,int pageSize);
		 
		 /*
			 * 根据员工编号查找其所负责的寝室里的员工
			 * */
			@Select("select count(*) from customer WHERE customer.dormitory_id IN (SELECT dormitory_id from dormitory WHERE staff_id = #{0})")
			 long getCountDormitory_Staff(long staff_id);
			
			/*
			 * 根据员工编号查找其所负责的寝室里的员工
			 * */
			@Select("select count(*) from customer WHERE customer.dormitory_id IN (SELECT dormitory_id from dormitory WHERE staff_id = #{0}) and "
					+ "customer_name LIKE concat('%',#{1}, '%')")
			 long getCountDormitory_StaffByKey(long staff_id,String value);
			
		@Select("select dormitory_id,dormitory_name from dormitory WHERE building_id = #{building_id} and dormitory_status = 10")
		List<dormitory> getDormitoryByBuilding_id(long building_id);
		
		@Select("select dormitory.building_id,building_name,building_code from dormitory inner join building on "
				+ "dormitory.building_id = building.building_id WHERE dormitory_id=#{0}")
		building getBuildingByDormitory(long dormitoryId);
}
