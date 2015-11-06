package com.fenghuo.dao;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.INNER_JOIN;
import static org.apache.ibatis.jdbc.SqlBuilder.ORDER_BY;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import java.util.Map;


public class Order_multi_demension_selectManagerDao {
	public Order_multi_demension_selectManagerDao() {
	}

	public static String getOrder_multi_demension_select(Map<String, Object> params) {
		//params{时间段：(time_begin;time_end),寝室：dormitory_id,楼栋：building_id,学校：school_id,销售价格：(order_sell_money_top;order_sell_money_bottom),某一零食销售量：(snacks_id;snack_number),负责人：staff_id,状态：order_status，排序方式(sort_key;sort_method)}
		
		// 序号 订单号 订单创建时间    销售价格   配送人员 订单状态
		//寝室id   负责人名  寝室名  楼栋ID 楼栋名  学校ID 学校名 
		//销售零食数量 零食名字
		
		String SELECT_KEYS = "orders.order_id,order_query_id,order_create_time,order_sell_money,orders.staff_id,order_status,default_order_id,order_note,"
				+ "staff_name,dormitory_name,dormitory.building_id,building_name,building.school_id,school_name, "
				+"sell_item.snacks_number,snacks.snacks_name";
		BEGIN();
		SELECT(SELECT_KEYS);
		FROM("orders");
		//内联表
		INNER_JOIN("staff on orders.staff_id=staff.staff_id");
		INNER_JOIN("dormitory on orders.dormitory_id=dormitory.dormitory_id");
		INNER_JOIN("building on dormitory.building_id=building.building_id");
		INNER_JOIN("school on building.school_id=school.school_id");
		INNER_JOIN("sell_detail on orders.order_id=sell_detail.order_id");
		INNER_JOIN("sell_item on sell_detail.sell_item_id=sell_item.sell_item_id");
		INNER_JOIN("snacks on sell_item.snacks_id=snacks.snacks_id");
		
		//查询条件：时间段
		//ps:时间戳：20150810<20150811   检查时间戳生成格式化（问题，怎么样解决之前的以星期进行的时间戳问题？）
		
		
		 Long time_begin = (Long)params.get("time_begin");
		 Long time_end = (Long)params.get("time_end");
		 if(time_begin != null&&time_end!=null){
         	WHERE("order_create_time>='#{time_begin}' and order_create_time<='#{time_end}'");
         }
		
		 //查询条件：寝室
		 Integer dormitory_id = (Integer)params.get("dormitory_id");
		 if(dormitory_id != null){
         	WHERE("orders.dormitory_id=#{dormitory_id}");
         }	
		 
		 
		 //查询条件：楼栋
		 Integer building_id = (Integer)params.get("building_id");
		 if(building_id != null){
         	WHERE("dormitory.building_id=#{building_id}");
         }	
		 
		 
		 //查询条件：学校
		 Integer school_id = (Integer)params.get("school_id");
		 if(school_id != null){
         	WHERE("building.school_id=#{school_id}");
         }	
		 
		 
		 //查询条件：价格范围（bottom--top）
		 Integer order_sell_money_top = (Integer)params.get("order_sell_money_top");
		 Integer order_sell_money_bottom = (Integer)params.get("order_sell_money_bottom");
		 if(order_sell_money_top != null&&order_sell_money_bottom!=null){
         	WHERE("order_sell_money<=#{order_sell_money_top} and order_sell_money>=#{order_sell_money_bottom}");
         }	
		 
		 
		 //查询条件：某一零食销售量：(snacks_id;snack_number
		 Integer snacks_id = (Integer)params.get("snacks_id");
		 Integer snacks_number = (Integer)params.get("snacks_number");
		 if(snacks_id != null&&snacks_number!=null){
         	WHERE("sell_item.snacks_number>=#{snacks_number} and sell_item.snacks_id<=#{snacks_id}");
         }
		 
		 
		 //查询条件：负责人：staff_id
		 Integer staff_id = (Integer)params.get("staff_id");
		 if(staff_id != null){
         	WHERE("orders.staff_id=#{staff_id}");
         }	
		 
		 
		 //查询条件：订单状态       order_status
		 Integer order_status = (Integer)params.get("order_status");
		 if(order_status != null){
         	WHERE("order_status=#{order_status}");
         }
		 
		 
		 //排序方式：字段名;正序还是倒序  (sort_key;sort_method)
		 String sort_key = (String)params.get("sort_key");
		 String sort_method = (String)params.get("sort_method");
		 Long page = (Long)params.get("page");
		 Integer pageSize = (Integer)params.get("pageSize");
		 if(page!=null&&pageSize!=null){
			 page=(long) 0;
			 pageSize=1;
			 
		 }
		 if(sort_key != null&&sort_method!=null){
			 ORDER_BY("#{sort_key} #{sort_method} limit #{page},#{pageSize}");
         }else{
        	 ORDER_BY("orders.order_id desc limit #{page},#{pageSize}");
         }
		 
		 
		String sql_prefix = SQL();
		return sql_prefix;

	}
}
