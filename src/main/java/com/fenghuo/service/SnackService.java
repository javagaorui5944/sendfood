package com.fenghuo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenghuo.dao.SnackDao;
import com.fenghuo.domain.Snacks;

@Service("snackService")
public class SnackService {

	@Autowired
	private SnackDao snackDao;
	
	public Long addSnack(Snacks snack){
		snackDao.addFood(snack);
		return snackDao.getFoodId(snack);
	}
	
	public void deleteSnacks(long snacks_id){
		snackDao.deleteSnacks(snacks_id);
	}

	public int changeSnack(int snacks_status,long snacks_id){
		return snackDao.changeFood(snacks_status,snacks_id);
	}
	
	public Snacks getSnack(long snacks_id){
		return snackDao.getFoodPriceAndStatus(snacks_id);
	}
	
	public Integer updateSnack(Snacks snack){
		
		return snackDao.updateFood(snack);
	}
	/*
	 * 通过仓库id以及模糊查询获取食品的信息
	 */
	public List<Snacks> getSnacksByStorageAndKey(long storage_id,Long page,int pageSize,String key,String value){
		if(key != null && ("snacks_bar_code".equals(key) || "snacks_name".equals(key))){
			if("snacks_bar_code".equals(key)){
				return snackDao.getSnacksByStorageAndCode(storage_id, (page-1)*pageSize, pageSize, value);
			}else{
				return snackDao.getSnacksByStorageAndName(storage_id, (page-1)*pageSize, pageSize, value);
			}
		}else{
			return snackDao.getSnacksByStorage(storage_id, (page-1)*pageSize, pageSize);
		}
	}
	/*
	 * 通过仓库id以及模糊查询获取食品数量
	 */
	public long getSnacksCountByStorageAndKey(long storage_id,String key,String value){
		if(key != null && ("snacks_bar_code".equals(key) || "snacks_name".equals(key))){
			if("snacks_bar_code".equals(key)){
				return snackDao.getSnacksCountByStorageAndCode(storage_id, value);
			}else{
				return snackDao.getSnacksCountByStorageAndName(storage_id, value);
			}
			
		}else{
			return snackDao.getSnacksCountByStorage(storage_id);
		}
	}
	
	public int foodPicUploadfile(String snacks_pic,long snacks_id){
		return snackDao.foodPicUploadfile(snacks_pic, snacks_id);
	}
	public Snacks getSnacksById(long snacks_id){
		return snackDao.getSnacksById(snacks_id);
	}
}
