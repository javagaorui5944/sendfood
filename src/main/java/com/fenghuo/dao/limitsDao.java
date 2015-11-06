package com.fenghuo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.fenghuo.domain.Limits;
import com.fenghuo.domain.Role;
import com.fenghuo.domain.staff_role;

@Component
public interface limitsDao {
	
	/*
	 * 增加父亲栏目。
	 * justin
	 */
	@Insert("insert into limits_parent(limits_pid,limits_pname,limits_id) values(#{limits_pid},#{limits_pname},#{limits_id})")
	public int addLimitsParent(int limits_pid,String limits_pname,int limits_id);
	@Insert("insert into limits (limits_name,limits_note"
			+ ",limits_url,limits_pname,limits_pid)"
			+ "values (#{limits_name},#{limits_note},#{limits_url},#{limits_pname},#{limits_pid})")
	public int addLimits(int limits_pid,String limits_pname,String limits_url,String limits_note,String limits_name);
	
	/*
	 * 根据limits_id删除权限 并且同时删去角色对应的权限关系
	 * justin
	 */
	@Select("delete a,b from limits a,roleoflimit b where a.limits_id=#{limits_id} and b.limits_id=#{limits_id}")
	public int deleteLimitsAll(int limits_id);
	
	/*
	/*
	 * 查詢所有權限信息并且 分页
	 * 
	 * justin
	 */
	@Select("select limits_id,limits_name,limits_note,limits_url,limits_status from limits where limits_status = 10 limit #{0},#{1}")
	List<Limits> getAllLimits(long page,int pageSize);
	
	/*
	 * 查询数量
	 * justin
	 */
	@Select("select count(*) from limits")
	long getCountLimitsList();
	
	
	/*
	 * 管理权限，只修改部分（以下参数）
	 *@param  limits_name,limits_note,limits_status
	 *justin
	 * */
	@Update("update limits set limits_name=#{limits_name}"
			+ ",limits_note=#{limits_note}"
			+ " where limits_id=#{limits_id}")
	public int updateLimits(String limits_name,String limits_note,int limits_id);
	
	/*
	 * 列出所有权限信息
	 *
	 * */		
	@Select("select limits_id,limits_name,limits_note,limits_url from limits")
	List<Limits> listAllLimits();
	
	@Select("select distinct limits_pid,limits_pname from limits where limits_status = 10")
	List<Limits> getAllLimitsParent();
	/*
	 * 查询角色对应的权限
	 *
	 * */
	
	@Select("select limits_id,limits_name,limits_note,limits_status,limits_url,limits_pname,limits_pid from limits where limits_id in (select limits_id from roleoflimit "
			+ "where role_id=#{0})")
	List<Limits> getRoleOfLimits(int id);
	
	/*
	 * 查询用户对应的权限
	 *
	 * */
	
	@Select("select distinct limits_id,limits_name,limits_note,limits_status,limits_url,limits_pname,limits_pid from limits where limits_id in (select limits_id from roleoflimit "
			+ "where role_id in (select role_id from staff_role where staff_id=#{user_id}))")
	List<Limits> getStuffLimitPageByStuffId(Long user_id);
	
	/*
	 * 添加权限
	 *@param  limits_name,limits_note,limits_status,limits_url,limits_pid
	 * 
	
	@Insert("insert into limits (limits_name,limits_note"
			+ ",limits_status,limits_url,limits_pname,limits_pid)"
			+ "values (#{limits_name},#{limits_note},#{limits_status},#{limits_url},#{limits_pname},#{limits_pid})")
	public int addLimits(Limits limits);
	*/

	/*
	 * 修改权限
	 *@param  limits_name,limits_note,limits_status,limits_url,limits_pid
	 * */
	
	@Update("update limits set limits_name=#{limits_name}"
			+ ",limits_note=#{limits_note},limits_status=#{limits_status}"
			+ ",limits_url=#{limits_url},limits_pname=#{limits_pname},limits_pid=#{limits_pid}"
			+ " where limits_id=#{limits_id}")
	public int updateLimitsByid(Limits limits);
	
	/*
	 * 根据id删除权限
	 *
	 * */
	
	@Delete("delete from limits,limitsofRole where limits_id=#{id}")
	public int deleteLimitsByid(int id);
	/*
	 * 删除角色对应的权限关系
	 *
	 * */
	
	@Delete("delete from roleoflimit where role_id=#{role_id}")
	public int deleteRoleLimits(String role_id);
	
	/*
	 * 根据管理员编号获取管理员的角色集合
	 * */
	
	@Select("select role_id from staff_role where staff_id=#{staff_id}")
	public List<Role> getStaffRoles(long staff_id);
	
	/*
	 * 根据url获取limit编号
	 * */
	@Select("select limits_id from limits where limits_url=#{limits_url} and limits_status=10")
	public Limits getLimitsByUrl(String limits_url);
	
	/*
	 * 根据角色编号和limit_id，
	 * 验证该角色是否拥有该limit
	 * */
	@Select("select roleoflimit_id from roleoflimit where limits_id=#{limits_id} and role_id=#{role_id}")
	public int checkByRoleAndLimit(int limits_id,int role_id);
	
	@Insert("insert into roleoflimit(role_id,limits_id) values(#{0},#{1})")
	public int saveRoleLimits(String role_id,String limit_id);
	
	/**
	 * gr权限测试dao层sql语句测试
	 */
	@Select("SELECT role_id,staff_id FROM staff_role WHERE  staff_id=#{0} AND role_id IN(SELECT role_id FROM roleoflimit WHERE limits_id =(SELECT limits_id FROM limits WHERE limits_url=#{1}))")
	public staff_role testLimits(long staff_id,String limits_url);
}
