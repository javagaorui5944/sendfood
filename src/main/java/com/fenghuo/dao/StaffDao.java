package com.fenghuo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.fenghuo.domain.staff;

@Component
public interface StaffDao {
	
	/*
	 * 根据员工编号获取用户权限范围
	 * */
	@Select("select staff_id,staff_rank,staff_manage_partid from goods where goodsincid in (select orderincid from addorderitem)")
	 List<staff> getDiyGoods();
	 
	 /*
		 * 根据员工编号获取用户权限范围
		 * */
	@Select("select staff_id,staff_name,staff_rank,staff_tel,staff_status,staff_email,staff_manage_partid from staff where staff_id = #{staff_id}")
	staff getstaffById(long staff_id);
	 
	 /*
	  * 根据员工编号获取用户权限范围
	  *
	  * */
	@Select("select dormitory.staff_id,staff_tel  from dormitory inner join staff on dormitory.staff_id=staff.staff_id "
			+ "where dormitory_id = #{dormitory_id}")
	staff getStaffBydormitory(long dormitory_id);
	
	@Select("select staff_id,staff_tel from building_staff where building_id in #{building_id}")
	List<staff> getStaffByBuilding(long building_id);
	
	@Select("select staff_id,staff_tel from school_staff where school_id in #{school_id}")
	List<staff> getStaffBySchool(long school_id);
	
	@Insert("insert into staff(staff_name,staff_tel,staff_password,staff_rank,"
			+ "staff_email,staff_join_time,staff_status) values(#{0},#{1},88888888,0,"
			+ "#{2},(select sysdate()),10)")
	int addStaff(String staff_name, String staff_tel,String staff_email);

	@Update("update staff set staff_name=#{1},staff_tel=#{2},staff_email=#{3},staff_status=#{4} "
			+ "where staff_id=#{0}")
	int changeStaff(long id, String staff_name, String staff_tel,String staff_email, int staff_status);
	
	@Select("select staff_id from staff where staff_tel = #{0} and staff_id != #{1}")
	List<staff> judgeUserRepeat(String staff_tel,long staff_id);
	
	@Select("select staff_id from staff where staff_tel = #{0}")
	List<staff> judgeUserRepeatTel(String staff_tel);
	
	
	
	
}
