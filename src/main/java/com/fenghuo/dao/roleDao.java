package com.fenghuo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.fenghuo.domain.Role;

@Component
public interface roleDao {
	/*
	 * 查询用户对应的角色
	 *
	 * */
	public static String USER_OF_ROLE = "role_id,staff_id";
	
	/*
	 * 单独保存某个角色功能的名字和备注功能
	 * justin
	 */
	@Update("update role set role_name=#{role_name},role_note=#{role_note} where role_id=#{role_id}")
	int UpdateRole(@Param("role_name")String role_name, @Param("role_note")String role_note,  @Param("role_id")long role_id);
	
	
	/*
	 * 查询角色并且分页
	 * justin
	 */
	@Select("select role_id,role_name,role_note,role_status from role where role_status = 10 limit #{0},#{1}")
	List<Role> getAllRoles(long page,int pageSize);
	
	 /**
	  * 根据用户ID取得用户的角色
	  * */
	@Select("select role_id,role_name,role_note,role_status from role where role_status = 10 and role_id in (select role_id from staff_role where staff_id=#{staff_id})")
	List<Role> getRolesByUserId(String staff_id);
	
	
	@Select("select role_id,role_name,role_note,role_status from role where role_status = 10")
	List<Role> getAllRoles1();
	
	@Insert("insert into staff_role(role_id,staff_id) values(#{1},#{0})")
	int saveUserRole(String staff_id,String role_id);
	
	@Insert("insert into role(role_name,role_note,role_status) values(#{0},#{1},10)")
	int addRole(String role_name, String role_note);

	/**
	 * 更新用户角色
	 * @param role_id
	 * @param role_name
	 * @param role_note
	 * @param role_status
	 * @return
	 */
	@Update("update role set role_name=#{role_name},role_note=#{role_note},role_status=#{role_status}"
			+ "where role_id=#{role_id}")
	int ReviseRole(long role_id, String role_name, String role_note, int role_status);
	
	@Delete("delete from staff_role where staff_id=#{0}")
	int deleteStaffRole(String staff_id);	
	
	/**
	 * 删除用户角色，并且删除staff_role中相关的数据
	 * @justin
	 * @return
	 * 下面2个都可以用，一个删除一个表，一个删除关联的staff_role表
	 */
//	@Delete("delete a,b from role as a left join staff_role as b on a.role_id=b.role_id where a.role_id=#{role_id}")
//	int deleteRole(long role_id);
	@Delete("delete a from role as a where a.role_id=#{role_id}")
	int deleteRole(long role_id);
	
	/*
	 * 列出所有用户数量
	 *justin
	 * */	
	
	@Select("select count(*) from role")
	long getCountRoleList();
	 
	
}
