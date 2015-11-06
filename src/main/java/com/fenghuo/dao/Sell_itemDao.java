package com.fenghuo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.fenghuo.domain.sell_item;




@Component
public interface Sell_itemDao {

	/**
	 * @descrription insert sell_item
	 */
	//sell_items={snacks_id:1,snacks_number:2}
	
	@Insert("insert into sell_item values(null,#{0},#{1},#{2});")
	public void insertsell_items(long snacks_id,int snacks_number,long order_id);
	@Select("select @@IDENTITY")
	public int IDENTITY();
	
	/*
	@Insert("insert into sell_detail values(#{order_id},#{sell_item_id})")
	public boolean insertsell_detail(@Param("sell_item_id") int sell_item_id,@Param("order_id") int order_id);
	*/
	@Select("select *from sell_item where order_id=#{0}")
	public List<sell_item> get_sellitemsByorder_id(Long order_id);
}
