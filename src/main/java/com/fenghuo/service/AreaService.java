package com.fenghuo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenghuo.dao.BuildingDao;
import com.fenghuo.dao.DormitoryDao;
import com.fenghuo.dao.RegionDao;
import com.fenghuo.dao.SchoolDao;
import com.fenghuo.domain.building;
import com.fenghuo.domain.dormitory;
import com.fenghuo.domain.school;

@Service
public class AreaService {

	
	@Autowired
	private RegionDao regionDao;
	
	@Autowired
	private SchoolDao schoolDao;
	
	@Autowired
	private BuildingDao buildingDao;
	
	@Autowired
	private DormitoryDao dormitoryDao;
	
	public List<school> getSchool(){
		return schoolDao.getSchool();
	}
	
	public List<building> getBuildingBySchool_id(long school_id){
		return buildingDao.getBuildingBySchool_id(school_id);
	}
	
	public List<dormitory> getDormitoryByBuilding_id(long building_id){
		return dormitoryDao.getDormitoryByBuilding_id(building_id);
	}
	
	/*
	 * 获取我所能管辖的学校集合
	 * 学校负责人
	 */
	public List<school> getMySchool(long staff_id){
		return schoolDao.getMySchool(staff_id);
	}
	
	/*
	 * 获取我所能管辖的学校集合
	 * 区负责人
	 */
	public List<school> getMySchool_1(long staff_id){
		return regionDao.getMySchool(staff_id);
	}
	
	public building getBuildingByDormitory(long dormitoryId){
		return dormitoryDao.getBuildingByDormitory(dormitoryId);
	}
	
	public school getSchoolByBuilding(long buildingId){
		return buildingDao.getSchoolByBuilding(buildingId);
	}
}
