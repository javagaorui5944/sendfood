package com.fenghuo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenghuo.dao.StaffDao;
import com.fenghuo.dao.UserDao;
import com.fenghuo.domain.customer;
import com.fenghuo.domain.staff;
/**
 * 处理所有用户的公共服务类
 * */
@Service
public class UserService {
private Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserDao userdao;
	@Autowired
	private StaffDao staffdao;
	
	/*
	 * 删除用户 及关联
	 * justin
	 */
	 public int deleteUserAll(int staff_id){
			return userdao.deleteUserAll(staff_id);
		 }
	
	/**
	 * 判断用户是否登录
	 * @param 用户ID
	 * */
	
	 public boolean isLogin(int userId){
		 return true;
	 }
	 
	 	/**
		 * 得到所有用户列表
		 * @param
		 * */
	 
	 public List<staff> getStaffList(long page,int pageSize){
		 if((Long)page == null || page <=0){
				page = 1;
			}
		return userdao.getStaffList((page-1)*pageSize,pageSize);
	 }
	 
		/**
		 * 得到所有数量
		 * @param
		 * */
	 
	 public long getCountStaffList(){
		return userdao.getCountStaffList();
	 }
	 

	 public List<staff> getStuffsBySearchName(String staff_name){
		 
		 return userdao.getStuffsBySearchName(staff_name);
	 }
	 /**
	 * 删除用户
	 * @param	用户id
	 * */
	  
	 public int deleteUser(int user_id){
		return userdao.deleteUser(user_id);
	 }
		 
    public staff getStaffByStffTel(String telphone){
    	return userdao.getStaffByStffTel(telphone);
    }
    
    public staff getStaffByOrderId(long OrderId){
    	return userdao.getStaffByOrderId(OrderId);
    }
    
    public String getPasswordByCustomerId(Long id){
    	return userdao.getPasswordByCustomerId(id);
    }
    
    
    
    public customer getCustomerByTel(String telphone){
    	return userdao.getCustomerByTel(telphone);
    }
    
    public customer getCustomerById(long customer_id){
    	return userdao.getCustomerById(customer_id);
    }
    
    /*
     * 根据宿舍获取负责人
     */
    public staff getStaffBydormitory(long dormitory_id){
    	return staffdao.getStaffBydormitory(dormitory_id);
    }
    /*
     * 根据楼栋获取负责人
     */
    public List<staff> getStaffByBuilding(long building_id){
    	return staffdao.getStaffByBuilding(building_id);
    }
    /*
     * 根据学校获取负责人
     */
    public List<staff> getStaffBySchool(long school_id){
    	return staffdao.getStaffBySchool(school_id);
    }
    /*
     * 根据员工编号获取员工负责范围
     */
    public staff getStaffById(long staff_id){
    	return staffdao.getstaffById(staff_id);
    }
    /**
	 * 添加修改员工功能
	 * */
    public int addStaff(long id,String staff_name,String staff_tel,String staff_email,int staff_status){
    	if(id==-1){
    		return staffdao.addStaff(staff_name,staff_tel,staff_email);
    	}else{
    		return staffdao.changeStaff(id,staff_name,staff_tel,staff_email,staff_status);
    	}
    }
    public List<staff> judgeUserRepeat(String tel,long staff_id){
    	if(staff_id == -1){
    		return staffdao.judgeUserRepeatTel(tel);
    	}
    	return staffdao.judgeUserRepeat(tel,staff_id);
    	
    }
    
 
    
}
