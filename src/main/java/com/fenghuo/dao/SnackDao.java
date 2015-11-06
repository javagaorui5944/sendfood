package com.fenghuo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.fenghuo.domain.Snacks;
import com.fenghuo.domain.order_item;

@Component
public interface SnackDao {
	
	
	/*
	 * 用户下单时，找到当前的DIY补充订单里的食物
	 *
	 * */
	@Select("select * from goods where goodsincid in (select orderincid from addorderitem)")
	 List<Snacks> getDiyGoods();
	
	/**
	 * 对标准套餐的查询
	 */
	@Select("select * from orderitem where defaultincid=#{id}")
		List<order_item> getDefaultFoods(long id);
	/**
	 * 对标准套餐的修改
	 * @param order
	 */
	@Update("update orderitem set goodsincid=#{goodsincid}" +
			",defaultincid=#{defaultincid},goodsnum=#{goodsnum} where itemincid=#{itemincid}")
		public void updateDefaultFoods(order_item order);	
	
	/*
	 * 添加新食品
	 */
		@Insert("insert into snacks (storage_id,snacks_name,snacks_bar_code,snacks_cost_price,snacks_sell_price,snacks_stock_number,snacks_status,snacks_pic) "
				+ "values (#{storage_id},#{snacks_name},#{snacks_bar_code},#{snacks_cost_price},#{snacks_sell_price},#{snacks_stock_number},#{snacks_status},#{snacks_pic})")
		public void addFood(Snacks snack);
		
		/*
		 * 添加新食品
		 */
		@Select("select max(snacks_id) from snacks where storage_id=#{storage_id} and snacks_name=#{snacks_name} and snacks_bar_code=#{snacks_bar_code} and snacks_cost_price=#{snacks_cost_price} and snacks_sell_price=#{snacks_sell_price}"
				+ " and snacks_stock_number=#{snacks_stock_number} and snacks_status=#{snacks_status}")
		public Long getFoodId(Snacks snack);
		
		/*
		 * 修改食品状态
		 */
		@Update("update snacks set snacks_status = #{0} where snacks_id=#{1}")
		public int changeFood(int snacks_status,long snacks_id);
		/*
		 * 通过id获取食品的价格
		 */
		@Select("select snacks_cost_price,snacks_sell_price from snacks where snacks_id=#{snacks_id}")
		public Snacks getFood(long snacks_id);
		
		/*
		 * 通过id获取食品的价格
		 */
		@Select("select snacks_id,storage_id,snacks_cost_price,snacks_sell_price,snacks_status,snacks_pic from snacks where snacks_id=#{snacks_id}")
		public Snacks getFoodPriceAndStatus(long snacks_id);
		
		/*
		 * 通过id获取食品的信息
		 */
		@Select("select * from snacks where snacks_id=#{0}")
		public Snacks getSnacksById(long snacks_id);
		/*
		 * 修改食品数据，不包括价格
		 */
		@Select("update snacks set snacks_bar_code = #{snacks_bar_code},snacks_name=#{snacks_name},snacks_stock_number=#{snacks_stock_number} where snacks_id=#{snacks_id}")
		public Integer updateFood(Snacks snack);
		
		/*
		 * 通过仓库id获取食品的信息
		 */
		@Select("select * from snacks where storage_id=#{0} and snacks_status=10 order by snacks_id asc limit #{1},#{2}")
		public List<Snacks> getSnacksByStorage(long storage_id,long firstIndex,int pageSize);
		/*
		 * 通过仓库id获取食品数量
		 */
		@Select("select count(*) from snacks where storage_id=#{storage_id} and snacks_status=10")
		public long getSnacksCountByStorage(long storage_id);
		/*
		 * 通过仓库id以及模糊查询获取食品的信息
		 */
		@Select("select * from snacks where storage_id=#{0} and snacks_status=10 and snacks_bar_code like concat('%',#{3}, '%') order by snacks_id asc limit #{1},#{2}")
		public List<Snacks> getSnacksByStorageAndCode(long storage_id,long firstIndex,int pageSize,String value);
		
		/*
		 * 通过仓库id以及模糊查询获取食品的信息
		 */
		@Select("select * from snacks where storage_id=#{0} and snacks_status=10 and snacks_name like concat('%',#{3}, '%') order by snacks_id asc limit #{1},#{2}")
		public List<Snacks> getSnacksByStorageAndName(long storage_id,long firstIndex,int pageSize,String value);
		
		/*
		 * 通过仓库id以及模糊查询获取食品数量
		 */
		@Select("select count(*) from snacks where storage_id=#{0} and snacks_status=10 and snacks_bar_code like concat('%',#{1}, '%')")
		public long getSnacksCountByStorageAndCode(long storage_id,String value);
		
		/*
		 * 删除食物
		 */
		@Delete("delete from snacks where snacks_id=#{0}")
		public void deleteSnacks(long snacks_id);
		
		/*
		 * 通过仓库id以及模糊查询获取食品数量
		 */
		@Select("select count(*) from snacks where storage_id=#{0} and snacks_status=10 and snacks_name like concat('%',#{1}, '%')")
		public long getSnacksCountByStorageAndName(long storage_id,String value);
		
		@Update("UPDATE snacks SET snacks_pic=#{snacks_pic}  WHERE snacks_id=#{snacks_id}")
		public int foodPicUploadfile(@Param("snacks_pic")String snacks_pic,@Param("snacks_id")long snacks_id);
}
