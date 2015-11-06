package com.fenghuo.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.fenghuo.domain.building;
import com.fenghuo.domain.dormitory;

@Component
public interface StaffDao_common {
	@Select("select staff_name from staff where staff_id=#{staff_id} and staff_status=10")
	public String getStaffbyId(@Param("staff_id")long staff_id);
	
	@Select("select dormitory_id,building_id,dormitory_name from dormitory where dormitory_id=#{id} and dormitory_status=10")
	public dormitory getDormitoryNameById(@Param("id") long id);
	@Select("select building_name,school_id from building where building_id=#{id} and building_status=10")
	public building getBuildingNameById(@Param("id") long id);
	@Select("select school_name from school where school_id=#{id} and school_status=10")
	public String getSchoolNameById(@Param("id")long id);
	
}
