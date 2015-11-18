
package com.fenghuo.dao;



import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import com.fenghuo.domain.MultiDemensionOrder;
import com.fenghuo.domain.Order;
import com.fenghuo.domain.dormitory;

@Component
public interface OrderDao {
	
	
	
	/**
	 * @param 
	 * @description select all order datas from the order table 
	 */
	@Select("select * from orders ")
	public List<Order> getAllOrder();
	
	@SelectProvider(type=OrderManageDao.class, method="getOrderManage_1")
	public List<Order> getOrderManage(@Param("staff_id")Long staff_id,@Param("staff_rank")int staff_rank,@Param("status")Integer status,
			@Param("page")Long page,@Param("pageSize")int pageSize,@Param("key")String key,@Param("value")String value);
	
	@SelectProvider(type=OrderManageDao.class, method="getCountOrderManage_1")
	public Long getCountOrderManage(@Param("staff_id")Long staff_id,@Param("staff_rank")int staff_rank,@Param("status")Integer status,
			@Param("key")String key,@Param("value")String value);
	
	@SelectProvider(type=OrderManageDao.class, method="getOrderManageByPhone_1")
	public List<Order> getOrderManageByPhone(@Param("staff_id")Long staff_id,@Param("status")Integer status,
			@Param("page")Long page,@Param("pageSize")int pageSize);
	
	@SelectProvider(type=OrderManageDao.class, method="getCountOrderManageByPhone_1")
	public Long getCountOrderManageByPhone(@Param("staff_id")Long staff_id,@Param("status")Integer status);
	
	@Update("update orders set order_cost_money=#{1},order_sell_money=#{2},order_status = 40 where order_id=#{0} and order_status=30")
	public boolean update_order_price_1(long order_id,float cost_price,float sell_price);
	
	/**
	 * @param order 

	 * @description insert one data into the order table
	 * 
	 */
	@Insert("insert into orders values (#{order_id},#{order_query_id},#{dormitory_id},#{staff_id},#{default_order_id},#{order_create_time},#{order_cost_money},#{order_sell_money},#{order_note},#{order_status})")
	public boolean insert_order(Order order);
	
	/**
	 * @param order
	 * @description insert one data into the order table
	 * 
	 */
	@Insert("insert into orders (order_query_id,dormitory_id,staff_id,default_order_id,order_create_time,order_status) values (#{order_query_id},#{dormitory_id},#{staff_id},#{default_order_id},#{order_create_time},#{order_status})")
	public boolean insertOrder(Order order);
	
	/**
	 * 查找订单编号给配送人员进行配送
	 * 根据寝室编号获取刚刚添加的订单，因为注解的mybatis实现插入返回主键比较难
	 * 这种方法在同一个寝室同时生产两个订单的时候有可能会出现两次查到同一个订单编号
	 * 造成的后果不是很严重，配送人员将收到两条同样订单编号的配送消息
	 * @return
	 */
	@Select("select max(order_id) from orders where dormitory_id=#{0} and order_status=10")
	public Long getNewOrder(long dormitory_id);
	
	
	@Select("select order_note from orders where order_id=#{0}")
	public String getOrderNote(long order_id);
	/**
	 * @param order 
	 * @description update one data in the order table where order_id=?
	 */
	@Update("update orders set order_query_id=#{order_query_id},dormitory_id=#{dormitory_id},staff_id=#{staff_id},default_order_id=#{default_order_id},order_create_time=#{order_create_time},order_cost_money=#{order_cost_money},order_sell_money=#{order_sell_money},order_note=#{order_note},order_status=#{order_status} where order_id=#{order_id} and order_status=#{order_status}-10")
	public boolean update_order(Order order);
	

	/**
	 * @param order_id,staff_id,a(not in used) 
	 * @description update staff_id in the order table where order_query_id=?
	 */
	@Update("update orders set staff_id=#{staff_id} where order_id=#{order_query_id}")
	public boolean update_order_1(@Param(value="order_query_id") int order_id,@Param(value="staff_id") int staff_id,int a);
	
	
	/**
	 * @param order_id, status 
	 * @description update the status in the order table where order_id=?
	 */
	@Update("update orders set order_status=#{1},order_push_count=0 where order_id=#{0} and order_status=#{2}")
	public boolean update_order_2(long order_id,int order_status,int oldStatus);
	
	@Update("update orders set order_status=#{1},order_push_count=0,order_note=#{3} where order_id=#{0} and order_status=#{2}")
	public boolean update_order_3(long order_id,int order_status,int oldStatus,String order_note);
	
	@Select("select order_push_count from orders where order_id=#{0}")
	public Integer get_order_push_count(long order_id);
	
	@Select("select count(*) from orders where dormitory_id=#{0} and order_status<40")
	public Integer getNotCompleteOrderCount(long dormitory_id);
	
	@Select("select orders.dormitory_id,dormitory_code,dormitory_name from orders inner join dormitory "
			+ "on orders.dormitory_id = dormitory.dormitory_id where order_id=#{0}")
	public dormitory get_dormitory_id(long order_id);
	
	@Update("update orders set order_push_count=#{1} where order_id=#{0}")
	public boolean update_order_push_count(long order_id,int order_push_count);
	
	/**
	 * @param orders pagesize,startindex=（pagenum-1）*pagesize
	 * @description select several datas from the order table by page
	 * @ps:startindex need a requestparam name
	 */
	
	@Select("select order_id,order_query_id,dormitory_id,staff_id,order_create_time,order_status from orders limit #{startindex},#{pageSize}")
	public List<Order> select_order_1(@Param(value="startindex") int startindex,@Param(value="pageSize")int pageSize);
   
	
	/**
	 * @param orders pagesize,startindex=（pagenum-1）*pagesize,order_status
	 * @description select several datas in some status from the order table by page
	 * @ps:startindex need a requestparam name
	 */
	
	@Select("select order_id,order_query_id,dormitory_id,staff_id,order_create_time,order_status from orders where order_status=#{order_status} limit #{startindex},#{pageSize}")
	public List<Order> select_order(@Param(value="startindex") int startindex,@Param(value="pageSize")int pageSize,@Param(value="order_status")int order_status);
    
	
	/**
	 * @param orders staff_id,order_status
	 * @description select several order datas from the order table by staff_id and order_status
	 */
	@Select("select * orders from where staff_id=#{0} and order_status=#{1}")
	public List<Order> getOrderByStaff(long staff_id,int order_status);

	/**
	 * @param order_id, status 
	 * @description update the status in the order table where order_id=?
	 */
	@Update("update orders set order_cost_money=#{cost_price} , order_sell_money=#{sell_price} where order_id=#{order_id}")
	public boolean update_order_price(@Param("order_id") long order_id,@Param("cost_price") float cost_price,@Param("sell_price") float sell_price);
	
	@Select("select default_order_id from orders where order_id=#{order_id}")
	public int get_default_order_idByid(@Param("order_id") Long order_id);
	
	@Select("select count(order_id) from orders")
	public int countpage();

	
	@Select("select count(order_id) from orders where order_status=#{order_status} ")
	public int countpage1(@Param(value="order_status")int order_status);
	
	@Select("select order_query_id,order_create_time,order_cost_money,order_note,dormitory_id from orders where order_id=#{order_id} and (order_status=10 or order_status=20)")
	public Order select_order_info(@Param(value="order_id")long order_id);
	
	@Select("select order_query_id,order_create_time,order_sell_money,order_note,dormitory_id from orders where order_id=#{order_id}")
	public Order select_order_info_withoutstatus(@Param(value="order_id")long order_id);
	
	
	
	//中文描述:订单多维度查询
	/**
	 * @param 时间段(time_begin;time_end),寝室：dormitory_id,楼栋：building_id,学校：school_id,销售价格：(order_sell_money_top;order_sell_money_bottom),某一零食销售量：(snanck_id;snack_number),负责人：staff_id,状态：order_status，排序方式(sort_key;sort_method)}
	 * @description select order datas in a multidemension way
	 */
	@SelectProvider(type=Order_multi_demension_selectManagerDao.class, method="getOrder_multi_demension_select")
	public List<MultiDemensionOrder> getOrder_multi_demension_select(@Param("time_begin")Long time_begin,@Param("time_end")Long time_end,@Param("dormitory_id")Integer dormitory_id,@Param("building_id")Integer building_id,@Param("school_id")Integer school_id,@Param("order_sell_money_top")Integer order_sell_money_top,@Param("order_sell_money_bottom")Integer order_sell_money_bottom,@Param("snacks_id")Integer snacks_id,@Param("snacks_number")Integer snacks_number,@Param("staff_id")Integer staff_id,@Param("order_status")Integer order_status,@Param("sort_key")String sort_key,@Param("sort_method")String sort_method ,@Param("page")Long page,@Param("pageSize")Integer pageSize);

	@Select("select dormitory_id from dormitory where staff_id=#{0} and dormitory_name=#{1}")
	public Long getDormitory_id(Long staff_id, String dormitory_name);

	@Update("update orders set  dormitory_id=#{1},staff_id=#{2},order_query_id=#{3} where order_id=#{0}")
	public int updateOrder(Long order_id, Long dormitory_id, Long staff_id,String order_query_id);


	
	
}
//package com.fenghuo.dao;
//
//import javax.annotation.Generated;
//
//import org.apache.ibatis.annotations.Insert;
//import org.apache.ibatis.annotations.Options;
//import org.springframework.stereotype.Component;
//
//import com.fenghuo.domain.Orders;
//
//@Component
//public interface OrderDao {
//
//	
//	/**
//	 * 用户下单
//	 * 返回值对当前插入的对象自增id
//	 * */
//	@Insert("insert into orders ({#userincid},{#ordercreatetime},{#note})"
//			+ "values ({#userincid},{#ordercreatetime},{#note})")
//	@Options(useGeneratedKeys = true)
//	public void updateGuestPlaceAnOrder(Orders orders);
//	
//}

