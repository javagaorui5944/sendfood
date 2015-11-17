package com.fenghuo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenghuo.dao.SnackDao;
import com.fenghuo.domain.order_item;

@Service
public class FoodOrderService {
	
private Logger log = LoggerFactory.getLogger(FoodOrderService.class);
	
	@Autowired
	private SnackDao fooddao;
	
	
	
	public void updateDefaultFoods(order_item good){
			fooddao.updateDefaultFoods(good);
	}
	public List<order_item> getDefaultFoods(long id){
		return fooddao.getDefaultFoods(id);
	}

}

