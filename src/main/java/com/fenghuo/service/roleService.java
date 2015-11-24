package com.fenghuo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenghuo.dao.roleDao;
import com.fenghuo.domain.Role;

@Service
public class roleService {
private Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private roleDao roledao;
	
	/*
	 * 删除用户角色
	 * justin
	 */
	public int deleteRole(long roleId){
		return roledao.deleteRole(roleId);
	}
	
	/*
	 * 更新某个角色功能的名字和备注功能
	 * justin
	 */
	public int updateRole(String role_name,String role_note,long role_id){
		return roledao.UpdateRole(role_name, role_note, role_id);
	}
	
	 /**
		 * 添加修改员工功能
		 * */
    public int addOrReviseRole(long role_id,String role_name,String role_note,int role_status){
    	if(role_id == -1){
    		return roledao.addRole(role_name,role_note);
    	}else{
    		return roledao.ReviseRole(role_id,role_name,role_note,role_status);
    	}
    }
    
	/**
	 * 得到所有数量
	 * @justin
	 * */
	 public long getCountRoleList(){
		return roledao.getCountRoleList();
	 }
    
	 /**
	 * 取得所有角色 分页
	 * justin
	 * */
	 public  List<Role> getAllRoles(long page,int pageSize){
		 if((Long)page == null || page <=0){
				page = 1;
			}
		 return roledao.getAllRoles((page-1)*pageSize,pageSize);
	 }
	 
	 /**
	 * 取得所有角色
	 * */
	 public  List<Role> getAllRoles1(){
		 return roledao.getAllRoles1();
	 }
	 
	 /**
	  * 保存用户角色
	  **/
	 public int saveUserRole(String userId,String roleId){
		return roledao.saveUserRole(userId,roleId);
	 }
	 public int deleteStaffRole(String userId){
			return roledao.deleteStaffRole(userId);
    }
	 
	 /**
	  * 根据用户ID取得用户的角色
	  * */
	 public  List<Role> getRolesByUserId(String staff_id){
		 return roledao.getRolesByUserId(staff_id);
	 }
	 
	 
}
