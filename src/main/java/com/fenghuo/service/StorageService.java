package com.fenghuo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenghuo.dao.StorageDao;
import com.fenghuo.domain.Storage;
import com.fenghuo.domain.school;

@Service("storageService")
public class StorageService {

	@Autowired
	private StorageDao storageDao;
	
	/**
	 * 取得所有学校信息
	 * */
	 
	public List<school> getAllSchool(){
		return storageDao.getAllSchool();
	}
	
	
	/*
	 * 根据仓库编号获取仓库信息
	 */
	public List<Storage> getStorageById(long storage_id){
		return storageDao.getStorageById(storage_id);
	}
	/*
	 * 根据学校编号获取仓库信息
	 */
	public List<Storage> getStorageBySchool(long staff_id){
		return storageDao.getStorageBySchool(staff_id);
	}
	/*
	 * 根据区编号获取该区内所有仓库信息
	 */
	public List<Storage> getStorageByRegion(long staff_id){
		return storageDao.getStorageByRegion(staff_id);
	}
}
