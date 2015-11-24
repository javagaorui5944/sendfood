package com.fenghuo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenghuo.dao.BuildingDao;
import com.fenghuo.dao.DormitoryDao;
import com.fenghuo.dao.RegionDao;
import com.fenghuo.dao.SchoolDao;
import com.fenghuo.domain.customer;

@Service("staffService")
public class StaffService {
	
	@Autowired
	private DormitoryDao dormitoryDao;
	
	@Autowired
	private BuildingDao buildingDao;
	
	@Autowired
	private SchoolDao schoolDao;
	
	@Autowired
	private RegionDao regionDao;
	
	/**
	 * 根据员工编号查找其所负责的寝室内的员工 
	 * @param staff_id
	 * @return
	 */
	public List<customer> getDormitory_Staff(long staff_id,long page,int pageSize,String value){
		if(value == null){
			return dormitoryDao.getDormitory_Staff(staff_id,(page-1)*pageSize,pageSize);
		}
		return dormitoryDao.getDormitory_StaffByKey(staff_id,(page-1)*pageSize,pageSize,value);
	}
	
	public long getCountDormitory_Staff(long staff_id,String value){
		if(value == null){
			return dormitoryDao.getCountDormitory_Staff(staff_id);
		}
		return dormitoryDao.getCountDormitory_StaffByKey(staff_id,value);
	}
	
	/**
	 * 根据员工编号查找其所负责的楼栋内的用户 
	 * @param staff_id
	 * @return
	 */
	public List<customer> getBuilding_Staff(long staff_id,long page,int pageSize,String value){
		if(value == null){
			return buildingDao.getBuilding_Staff(staff_id,(page-1)*pageSize,pageSize);
		}
		return buildingDao.getBuilding_StaffByKey(staff_id,(page-1)*pageSize,pageSize,value);
	}
	
	public long getCountBuilding_Staff(long staff_id,String value){
		if(value == null){
			return buildingDao.getCountBuilding_Staff(staff_id);
		}
		return buildingDao.getCountBuilding_StaffByKey(staff_id,value);
	}
	
	/**
	 * 根据员工编号查找其所负责的学校内的用户 
	 * @param staff_id
	 * @return
	 */
	public List<customer> getSchool_Staff(long staff_id,long page,int pageSize,String value){
		if(value == null){
			return schoolDao.getSchool_Staff(staff_id,(page-1)*pageSize,pageSize);
		}
		return schoolDao.getSchool_StaffByKey(staff_id,(page-1)*pageSize,pageSize,value);
	}
	
	public long getCountSchool_Staff(long staff_id,String value){
		if(value == null){
			return schoolDao.getCountSchool_Staff(staff_id);
		}
		return schoolDao.getCountSchool_StaffByKey(staff_id,value);
	}
	
	/**
	 * 根据员工编号查找其所负责的区内的用户 
	 * @param staff_id
	 * @return
	 */
	public List<customer> getRegion_Staff(long staff_id,long page,int pageSize,String value){
		if(value == null){
			return regionDao.getRegion_Staff(staff_id,(page-1)*pageSize,pageSize);
		}
		return regionDao.getRegion_StaffByKey(staff_id,(page-1)*pageSize,pageSize,value);
	}
	
	public long getCountRegion_Staff(long staff_id,String value){
		if(value == null){
			return regionDao.getCountRegion_Staff(staff_id);
		}
		return regionDao.getCountRegion_StaffByKey(staff_id,value);
	}
}
