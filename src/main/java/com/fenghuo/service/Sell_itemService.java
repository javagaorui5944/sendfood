package com.fenghuo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenghuo.dao.Sell_itemDao;

@Service
public class Sell_itemService {
	@Autowired
	private Sell_itemDao sell_itemdao;

	public void insertsell_items(long snacks_id,int snacks_number,long order_id){
		sell_itemdao.insertsell_items(snacks_id, snacks_number, order_id);
	}
}
