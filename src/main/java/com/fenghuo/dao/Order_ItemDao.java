package com.fenghuo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.fenghuo.domain.Default_order;
import com.fenghuo.domain.Snacks;
import com.fenghuo.domain.order_item;
@Component
public interface Order_ItemDao {
	@Select("select * from order_item where default_order_id=#{0}")
	public List<order_item> getOrderitemByDefaultOrderId_1(long default_order_id);
	
	@Select("select * from order_item where default_order_id=#{default_order_id}")
	public List<order_item> getOrderitemByDefaultOrderId(long default_order_id);
	
	@Select("select snacks_id,snacks_number from order_item where default_order_id=#{default_order_id}")
	public List<order_item> getOrderitemByDefaultOrderId_2(long default_order_id);
	
	@Select("select order_item.snacks_id,snacks_number,storage_id,snacks_name,snacks_bar_code,snacks_pic,snacks_cost_price,"
			+ "snacks_sell_price,snacks_stock_number,snacks_status from order_item"
			+ " inner join snacks on order_item.snacks_id=snacks.snacks_id where default_order_id=#{default_order_id}")
	public List<Snacks> getOrderitemByDefaultOrderId_3(long default_order_id);
	
	@Select("select snacks_id,snacks_number from order_item where default_order_id=#{default_order_id}")
	public List<order_item> getOrderitemByDefaultOrderId_5(long default_order_id);
	
	@Select("select order_item.snacks_id,snacks_number,snacks_name,snacks_cost_price,snacks_sell_price,snacks_bar_code from order_item"
			+ " inner join snacks on order_item.snacks_id=snacks.snacks_id where default_order_id in "
			+ "(select default_order_id from orders where order_id = #{order_id})")
	public List<Snacks> getOrderitemByDefaultOrderId_4(long order_id);
	
	@Select("select sell_item.snacks_id,snacks_number,storage_id,snacks_name,snacks_bar_code,snacks_pic,snacks_cost_price,"
			+ "snacks_sell_price,snacks_stock_number,snacks_status from sell_item"
			+ " inner join snacks on sell_item.snacks_id=snacks.snacks_id where order_id=#{orderId}")
	public List<Snacks> getSellitemByOrderId(long orderId);
	
	@Select("select sell_item.snacks_id,snacks_number from sell_item"
			+ " inner join snacks on sell_item.snacks_id=snacks.snacks_id where order_id=#{orderId}")
	public List<Snacks> getSellitemByOrderId_1(long orderId);
	
//	@Select("select * from snacks where snacks_id in (select snacks_id from order_item where default_order_id = #{default_order_id})")
//	public List<Snacks> getOrderitemByDefaultOrderId(long default_order_id);
	
	@Select("select * from snacks where snacks_id=#{snacks_id}")
	public Snacks getsnacksById(long snacks_id);
	
	@Delete("delete from order_item where default_order_id=#{default_order_id} and snacks_id=#{snacks_id}")
	public int delSnacksfromDefaultOrderById(long default_order_id,long snacks_id);
	
	@Select("select * from snacks where storage_id in (select storage_id from storage where "
			+ "school_id = #{3}) and snacks_status=10 and snacks_name like #{0} limit #{1},#{2}")
	public List<Snacks> getSnacksByName(String snacks_name,int pageNo,int pageSize,long school_id);
	
	@Delete("delete from order_item where default_order_id=#{default_order_id}")
	public int delAllSnacksByDefaultOrderId(long default_order_id);


	@Insert("insert into order_item (default_order_id,snacks_id,snacks_number) values (#{0},"
			+ "#{1},#{2}) ")
	public int saveOrderItem(long default_order_id,long snacks_id,int snacks_number);
	
	@Update("update order_item set snacks_id = #{1} where snacks_id = #{0} and default_order_id in (select default_order_id from "
			+ "default_order where default_order_status=10)")
	public int updateOrderItem(long oldSnack,long newSnack);
	
	@Select("select school_id from storage where storage_id in (select storage_id from snacks where snacks_id = #{0})")
	public long getSchoolIdBysnack(long snack_id);
	
	@Select("select default_order_id,school_id,default_order_name,default_order_status from default_order where default_order_id in "
			+ "(select default_order_id from order_item where snacks_id = #{0})")
	public List<Default_order> getDefaultOrderIdBySnack(long snack_id);
	
	@Update("UPDATE dormitory SET default_order_id=${default_order_id} WHERE dormitory_id IN (${dormitory_id})")
	public int  addDefault_orderTodormitory(@Param("default_order_id")long default_order_id,@Param("dormitory_id") String dormitory_id);

}
