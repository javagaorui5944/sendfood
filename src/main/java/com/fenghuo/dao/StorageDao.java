package com.fenghuo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.fenghuo.domain.Storage;
import com.fenghuo.domain.school;


@Component
public interface StorageDao {
	
	/**
	 * 取得所有学校信息
	 */
	@Select("select school_id,region_id,school_name,school_code from school where school_status = 10")
	public List<school> getAllSchool();
	/*
	 * 根据仓库编号获取仓库信息
	 */
	@Select("SELECT storage_id,storage_name FROM storage WHERE storage_id=#{storage_id}")
	public  List<Storage> getStorageById(long storage_id);
	
	/*
	 * 根据学校编号获取仓库信息
	 */
	@Select("SELECT storage_id,storage_name FROM storage  WHERE school_id IN(SELECT school_id FROM school_staff WHERE staff_id=#{staff_id})")
	public List<Storage> getStorageBySchool(long staff_id);
	
	/*
	 * 根据区编号获取该区内所有仓库信息
	 */
	@Select("SELECT storage_id,storage_name FROM storage WHERE school_id IN(SELECT school_id FROM school WHERE region_id IN(SELECT region_id FROM region_staff WHERE staff_id=#{staff_id} ))")
	public List<Storage> getStorageByRegion(long staff_id);
}
