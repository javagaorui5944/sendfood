package com.fenghuo.dao;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.INNER_JOIN;
import static org.apache.ibatis.jdbc.SqlBuilder.ORDER_BY;
import static org.apache.ibatis.jdbc.SqlBuilder.SELECT;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import java.util.Map;

public class OrderManageDao {
	
	public OrderManageDao(){}
	
		public static String getOrderManage_1(Map<String,Object> params){
			String SELECT_KEYS = "order_id,order_query_id,order_create_time,orders.staff_id,order_status,orders.dormitory_id,"
+ "staff_name,dormitory_name,dormitory.building_id,building_name,building.school_id,school_name,staff_name ";
			BEGIN();
            SELECT(SELECT_KEYS);
            FROM("orders");
            INNER_JOIN("staff on orders.staff_id=staff.staff_id");
            INNER_JOIN("dormitory on orders.dormitory_id=dormitory.dormitory_id");
            INNER_JOIN("building on dormitory.building_id=building.building_id");
            INNER_JOIN("school on building.school_id=school.school_id");
            Integer status = (Integer)params.get("status");
            if(status != null){
            	WHERE("order_status=#{status}");
            }
            Integer staff_rank = (Integer)params.get("staff_rank");
            if(staff_rank == 20){
            	WHERE("orders.dormitory_id in (select dormitory_id from dormitory where building_id in"
            			+ "(select building_id from building_staff where staff_id = #{staff_id}))");
            }else if(staff_rank == 30){
            	WHERE("orders.dormitory_id in (select dormitory_id from dormitory where building_id in"
            			+ "(select building_id from building where school_id in"
            			+ "(select school_id from school_staff where staff_id = #{staff_id})))");
            }else if(staff_rank == 40){
            	WHERE("orders.dormitory_id in (select dormitory_id from dormitory where building_id in"
            			+ "(select building_id from building where school_id in"
            			+ "(select school_id from school where region_id in"
            			+ "(select region_id from region_staff where staff_id = #{staff_id}))))");
            }
            String key = (String)params.get("key");
            if("staff_name".equals(key)){
            	if(staff_rank == 10){
            		WHERE("orders.staff_id = #{staff_id}");
            	}
        		WHERE("orders.staff_id IN (select staff_id from staff where staff_name like concat('%',#{value}, '%'))");
        	}else if("order_id".equals(key)){
        		if(staff_rank == 10){
            		WHERE("orders.staff_id = #{staff_id}");
            	}
//        		WHERE("order_id LIKE concat('%',#{value}, '%')");
        		WHERE("order_id IN (select order_id from orders where order_query_id like concat('%',#{value}, '%'))");
        	}else{
        		if(staff_rank == 10){
            		WHERE("orders.staff_id = #{staff_id}");
            	}
        	}
            ORDER_BY("order_id desc limit #{page},#{pageSize}");
			String sql_prefix = SQL();
			return sql_prefix;
		}
		
		public static String getCountOrderManage_1(Map<String,Object> params){
			String SELECT_KEYS = "count(*)";
			BEGIN();
            SELECT(SELECT_KEYS);
            FROM("orders");
            Integer status = (Integer)params.get("status");
            if(status != null){
            	WHERE("order_status=#{status}");
            }
            Integer staff_rank = (Integer)params.get("staff_rank");
            if(staff_rank == 20){
            	WHERE("orders.dormitory_id in (select dormitory_id from dormitory where building_id in"
            			+ "(select building_id from building_staff where staff_id = #{staff_id}))");
            }else if(staff_rank == 30){
            	WHERE("orders.dormitory_id in (select dormitory_id from dormitory where building_id in"
            			+ "(select building_id from building where school_id in"
            			+ "(select school_id from school_staff where staff_id = #{staff_id})))");
            }else if(staff_rank == 40){
            	WHERE("orders.dormitory_id in (select dormitory_id from dormitory where building_id in"
            			+ "(select building_id from building where school_id in"
            			+ "(select school_id from school where region_id in"
            			+ "(select region_id from region_staff where staff_id = #{staff_id}))))");
            }
            String key = (String)params.get("key");
            if("staff_name".equals(key)){
            	if(staff_rank == 10){
            		WHERE("orders.staff_id = #{staff_id}");
            	}
        		WHERE("orders.staff_id IN (select staff_id from staff where staff_name like concat('%',#{value}, '%'))");
        	}else if("order_id".equals(key)){
        		if(staff_rank == 10){
            		WHERE("orders.staff_id = #{staff_id}");
            	}
//        		WHERE("order_id LIKE concat('%',#{value}, '%')");
        		WHERE("order_id IN (select order_id from orders where order_query_id like concat('%',#{value}, '%'))");
        	}else{
        		if(staff_rank == 10){
            		WHERE("orders.staff_id = #{staff_id}");
            	}
        	}
			String sql_prefix = SQL();
			return sql_prefix;
		}
		
		public static String getOrderManageByPhone_1(Map<String,Object> params){
			String SELECT_KEYS = "order_id,order_query_id,default_order_id,order_create_time,orders.staff_id,order_note,"
					+ "order_create_time,order_cost_money,order_status,orders.dormitory_id,"
					+ "dormitory_name,dormitory.building_id,building_name,building.school_id,school_name ";
			BEGIN();
            SELECT(SELECT_KEYS);
            FROM("orders");
            INNER_JOIN("dormitory on orders.dormitory_id=dormitory.dormitory_id");
            INNER_JOIN("building on dormitory.building_id=building.building_id");
            INNER_JOIN("school on building.school_id=school.school_id");
            Integer status = (Integer)params.get("status");
            if(status != null){
            	WHERE("order_status=#{status}");
            }
            WHERE("orders.staff_id = #{staff_id}");
            ORDER_BY("order_id desc limit #{page},#{pageSize}");
            
			String sql_prefix = SQL();
			return sql_prefix;
		}
		
		public static String getCountOrderManageByPhone_1(Map<String,Object> params){
			String SELECT_KEYS = "count(*)";
			BEGIN();
            SELECT(SELECT_KEYS);
            FROM("orders");
            Integer status = (Integer)params.get("status");
            if(status != null){
            	WHERE("order_status=#{status}");
            }
            WHERE("orders.staff_id = #{staff_id}");
			String sql_prefix = SQL();
			return sql_prefix;
		}
}
