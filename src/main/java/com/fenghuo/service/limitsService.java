package com.fenghuo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenghuo.dao.limitsDao;
import com.fenghuo.domain.Limits;
import com.fenghuo.domain.Role;
import com.fenghuo.domain.staff_role;

@Service
public class limitsService {
	private Logger log = LoggerFactory.getLogger(limitsService.class);
	
	@Autowired
	private limitsDao limitsdao;
	
	/*
	 * 增加父亲栏目。
	 * justin
	 */
	public int addLimitsParent(int limits_pid,String limits_pname,int limits_id){
		return limitsdao.addLimitsParent(limits_pid, limits_pname, limits_id);
	}
	public int addLimits(int limits_pid,String limits_pname,String limits_url,String limits_note,String limits_name){
		return limitsdao.addLimits(limits_pid,limits_pname,limits_url,limits_note,limits_name);
	}
	
	/*
	 * 删除权限及相关联的关系
	 * justin
	 */
	 public int deleteLimitsAll(int limits_id){
		 return limitsdao.deleteLimitsAll(limits_id);
	 }
	
	 /*
	  * 查询权限信息并且 分页
	  * justin
	  */
	 public  List<Limits> getAllLimits(long page,int pageSize){
		 if((Long)page == null || page <=0){
				page = 1;
			}
		 return limitsdao.getAllLimits((page-1)*pageSize,pageSize);
	 }
	 
	/*
	 * 得到所有数量
	 * justin
	 */
	public long getCountLimitsList(){
		return limitsdao.getCountLimitsList();
	}
	 
	/*
	 * 管理权限，只修改部分（以下参数）
	 *@param  limits_name,limits_note,limits_status
	 *justin
	 * */
	 public int updateLimits(String limits_name,String limits_note,int limits_id){
		 return limitsdao.updateLimits(limits_name,limits_note,limits_id);
	 }
	
	/**
	 * 列出角色权限
	 * @param  角色id
	 * */
 
	 public List<Limits> listAllLimits(){
		 return limitsdao.listAllLimits();
	 }
	 public List<Limits> getAllLimitsParent(){
		 return limitsdao.getAllLimitsParent();
	 }
	 
	 /**
		 * 列出角色权限
		 * @param  角色id
		 * */
	 
	 public List<Limits> getRoleOfLimits(int id){
		 return limitsdao.getRoleOfLimits(id);
	 }
	 
	 /**
		 * 列出用户权限
		 * @param  角色id
		 * */
	 
	 public List<Limits> getStuffLimitPageByStuffId(Long id){
		 return limitsdao.getStuffLimitPageByStuffId(id);
	 }
	 
	 /**
		 * 添加权限
		 * 
		 * 
	 
	 public int addLimits(Limits limits){
		 return limitsdao.addLimits(limits);
	 }
	 */
	 
	 /**
		 * 修改权限
		 * @param 
		 * */
	 
	 public int updateLimitsByid(Limits limits){
		 return limitsdao.updateLimitsByid(limits);
	 }
	 
	 /**
		 * 删除权限
		 * @param  limits_id
		 * */
	 
	 public int deleteLimitsByid(int id){
		 return limitsdao.deleteLimitsByid(id);
	 }
	 public int deleteRoleLimits(String role_id){
		 return limitsdao.deleteRoleLimits(role_id);
	 }
	 
	 /**
	  * 保存角色权限
	  **/
	 public int saveRoleLimits(String role_id,String limit_id){
		return limitsdao.saveRoleLimits(role_id,limit_id);
	 }
	 /**
	  * 管理员权限比对
	  * 
	  */
	public boolean staff_limit_check(long staff_id,String url){
		 boolean result = false;
		 List<Role> roles = limitsdao.getStaffRoles(staff_id); //根据管理员编号获取管理员的角色集合
		 if(roles != null && roles.size()>0){
			 Limits limit = limitsdao.getLimitsByUrl(url);
			 if(limit != null){
				 for(Role r:roles){
					 Integer id = limitsdao.checkByRoleAndLimit(limit.getLimits_id(), r.getRole_id());
					 if(id != null && id>0){
						 result = true;
						 break;
					 }
				 }
			 }
		 }
		 return result;
	 }
	
	/**
	 * gr权限测试Service层
	 * 
	 */
	public staff_role testLimits(long staff_id,String limits_url){
		
		return limitsdao.testLimits(staff_id, limits_url);
	}
	
}
