package com.fenghuo.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fenghuo.dao.DormitoryDao;
import com.fenghuo.dao.OrderDao;
import com.fenghuo.dao.Order_ItemDao;
import com.fenghuo.dao.Sell_itemDao;
import com.fenghuo.dao.SnackDao;
import com.fenghuo.dao.StaffDao;
import com.fenghuo.dao.StaffDao_common;
import com.fenghuo.domain.MultiDemensionOrder;
import com.fenghuo.domain.Order;
import com.fenghuo.domain.Page;
import com.fenghuo.domain.Snacks;
import com.fenghuo.domain.building;
import com.fenghuo.domain.customer;
import com.fenghuo.domain.dormitory;
import com.fenghuo.domain.order_item;
import com.fenghuo.domain.sell_item;
import com.fenghuo.domain.staff;

@Service
public class OrderService {
	@Autowired
	private OrderDao orderdao;
	@Autowired
	private Order_ItemDao orderitemdao;
	@Autowired
	private SnackDao snackdao;
	@Autowired
	private StaffDao staffdao;
	@Autowired
	private Sell_itemDao sell_itemdao;
	
	@Autowired
	private StaffDao_common staffDao_common;
	
	@Autowired
	private Sell_itemService sell_itemService;
	@Autowired
	private DormitoryDao dormitorydao;
	
	@Autowired
	private organizationService organizationService;
	
	
	public List<Order> getOrderManage(Long staff_id,int staff_rank,Integer status,Long page,int pageSize,String key,String value){
		return  orderdao.getOrderManage(staff_id,staff_rank,status,(page-1)*pageSize,pageSize,key,value);
	}
	
	public String getOrderNote(long order_id){
		return  orderdao.getOrderNote(order_id);
	}
	
	public Long getCountOrderManage(Long staff_id,int staff_rank,Integer status,String key,String value){
		return  orderdao.getCountOrderManage(staff_id,staff_rank,status,key,value);
	}
	
	public List<Order> getOrderManageByPhone(Long staff_id,Integer status,Long page,int pageSize){
		return  orderdao.getOrderManageByPhone(staff_id,status,(page-1)*pageSize,pageSize);
	}
	
	public Long getCountOrderManageByPhone(Long staff_id,Integer status){
		return  orderdao.getCountOrderManageByPhone(staff_id,status);
	}
	//中文描述:订单多维度查询
	/**
	 * @param 时间段(time_begin;time_end),寝室：dormitory_id,楼栋：building_id,学校：school_id,销售价格：(order_sell_money_top;order_sell_money_bottom),某一零食销售量：(snanck_id;snack_number),负责人：staff_id,状态：order_status，排序方式(sort_key;sort_method)}
	 * @description select order datas in a multidemension way
	 */
	public  List<MultiDemensionOrder> getOrder_multi_demension_select(@Param("time_begin")Long time_begin,@Param("time_end")Long time_end,@Param("dormitory_id")Integer dormitory_id,@Param("building_id")Integer building_id,@Param("school_id")Integer school_id,@Param("order_sell_money_top")Integer order_sell_money_top,@Param("order_sell_money_bottom")Integer order_sell_money_bottom,@Param("snacks_id")Integer snacks_id,@Param("snacks_number")Integer snacks_number,@Param("staff_id")Integer staff_id,@Param("order_status")Integer order_status,@Param("sort_key")String sort_key,@Param("sort_method")String sort_method ,@Param("page")Long page,@Param("pageSize")Integer pageSize)
	{
		
		return orderdao.getOrder_multi_demension_select(time_begin,time_end,dormitory_id,building_id,school_id,order_sell_money_top,order_sell_money_bottom, snacks_id, snacks_number, staff_id, order_status, sort_key, sort_method ,(page-1)*pageSize, pageSize);
	}

	
	//状态1：订单生成（user信息）：
	/**
	*@param需要提供用户信息、默认的套餐id、还有备注
	*@need 根据默认套餐的id取出对应的套餐信息和传入的参数一起插入表中
	*/
	public boolean  order_create (customer customer,int default_order_id,String remark){
		boolean result=false;
		//create the time
		java.util.Date dt=new java.util.Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置显示格式
		String nowTime="";
		nowTime= df.format(dt);		
		//select the information of the customer
		int dormitory_id;String order_query_id;//lack the source from the customer
		
		Order order=new Order();
		order.setDefault_order_id(default_order_id);// set the default order
		order.setOrder_create_time(nowTime);//set time
		
		//set the information of the customer
		/*order.setDormitory_id(dormitory_id);
		order.setOrder_query_id(order_query_id);*/
		
		order.setOrder_note(remark);//set the order_note
		
		order.setOrder_status(10);//change the order_ status to 10
		result=orderdao.insert_order(order);
		return result;
	}
	
	
	//状态2：对订单进行配送（提供配送人员id和订单id）

	/**
	*@param    int order_id,int staff_id
	*@need 修改配送人员的id
	*/
	public Boolean  order_staff_update(int order_id,int staff_id){
		boolean result=false;
		result=orderdao.update_order_1(order_id, staff_id,1);
		result&=orderdao.update_order_2(order_id,20,10);
		return  result;
	}
	
		/**
		*@param    int order_id,int staff_id
		*@need 修改配送人员的id
		*/
		public Boolean updateOrder(long order_id,int order_status,int oldStatus,String order_note){
			if(order_note == null){
				return orderdao.update_order_2(order_id, order_status,oldStatus);
			}else{
				return orderdao.update_order_3(order_id, order_status,oldStatus,order_note);
			}
			
		}
	
	

	//状态3：配送完成（仅需要订单id）
	/**
	*@param需要提供订单id
	*@need 将订单的状态修改为以送达，将状态修改30
	*/
	
	public Boolean  order_sended_update(int order_id){
		boolean result=false;
		result=orderdao.update_order_2(order_id,30,20);
		return result;
	}


	public void update_order_price_1(long order_id,float cost_price,float sell_price){
		orderdao.update_order_price_1(order_id, cost_price, sell_price);
	}
	

//状态5：返货入库
/**
*@param需要提供订单id，物品的消耗情况
*@need 订单返货入库，将状态修改为50
*/

public Object  order_back_select(Long order_id,Integer operation){
	int default_order_id=0;
	//将返回的货物入库（返回的货物：订单货物减去销售货物）
	//根据order_id取出订单的信息。
	default_order_id=orderdao.get_default_order_idByid(order_id);
	List<order_item> orderitems= orderitemdao.getOrderitemByDefaultOrderId(default_order_id);
	HashMap<Long,Snacks> restview=new HashMap<Long,Snacks>();
	List<Snacks> orderview=new ArrayList<Snacks>();
	Snacks snacks=null;
	//形成订单视图
	int ordernum=0;//订单数量（会和销售做减法得到剩余）
	float ordercostprice=0;//订单的总成本价（会和销售做减法得到剩余）
	float ordersellprive=0;//订单的总销售价（会和销售做减法得到剩余）
	for (order_item orderitem:orderitems){
		ordernum=orderitem.getSnacks_number();
		
		snacks=snackdao.getSnacksById(orderitem.getSnacks_id());
		ordercostprice=snacks.getSnacks_cost_price()*ordernum;
		ordersellprive=snacks.getSnacks_sell_price()*ordernum;
		snacks.setSnacks_cost_price(ordercostprice);
		snacks.setSnacks_sell_price(ordersellprive);
		snacks.setSnacks_number(ordernum);
		
		restview.put(orderitem.getSnacks_id(), snacks);
	}
	//ps:此处的snack中的库存数量字段被用来保存订单中的数量
	//取出销售订单
	List<Snacks> sellview=new ArrayList<Snacks>();//销售视图
	List<sell_item> sellitems= sell_itemdao.get_sellitemsByorder_id(order_id);
	Snacks restsnack=null;
	long snackid=0;
	for (sell_item sellitem:sellitems){
		ordernum=sellitem.getSnacks_number();
		snackid=sellitem.getSnacks_id();
		//保存销售视图信息
		snacks=snackdao.getSnacksById(sellitem.getSnacks_id());
		ordercostprice=snacks.getSnacks_cost_price()*ordernum;
		ordersellprive=snacks.getSnacks_sell_price()*ordernum;
		
		snacks.setSnacks_cost_price(ordercostprice);
		snacks.setSnacks_sell_price(ordersellprive);
		snacks.setSnacks_number(ordernum);
		
		sellview.add(snacks);
		
		//算出剩下的商品
		restsnack=restview.get(snackid);
		if(restsnack!=null){
		restsnack.setSnacks_stock_number(restsnack.getSnacks_stock_number()-ordernum);
		restsnack.setSnacks_cost_price(restsnack.getSnacks_cost_price()-ordercostprice);
		restsnack.setSnacks_sell_price(restsnack.getSnacks_sell_price()-ordersellprive);
		
		restview.put(snackid, restsnack);}
		restsnack=null;
	}
	//getSnacksById()
	//对订单信息进行减法操作
	Set<Long> co=restview.keySet();
	Iterator<Long> it=co.iterator();
	while(it.hasNext()){
		Long sn=it.next();
		orderview.add(restview.get(sn));
	
	}

	
	HashMap<String,Object> result=new HashMap<String,Object>();
	Order order=orderdao.select_order_info_withoutstatus(order_id);
	dormitory dor=staffDao_common.getDormitoryNameById(order.getDormitory_id());
	building bui =staffDao_common.getBuildingNameById(dor.getBuilding_id());
	result.put("dormitory_name", dor.getDormitory_name());
	result.put("building_name", bui.getBuilding_name());
	result.put("school_name", staffDao_common.getSchoolNameById(bui.getBuilding_id()));
	result.put("order_query_id", order.getOrder_query_id());
	result.put("order_create_time", order.getOrder_create_time());
	result.put("order_cost_money", order.getOrder_sell_money());
	result.put("order_note", order.getOrder_note());


	result.put("restview", orderview);
	result.put("sellview", sellview);
	//将状态进行修改
	if(operation!=null)
	orderdao.update_order_2(order_id, 50,40);
	return result;
}
	
	

	//负责人员点击管理未配送订单查询信息：
	public Object order_fit_select(long order_id,int staff_id){
		
		
		Object staffs=null;
		
		
		HashMap<String,Object> result=new HashMap<String,Object>(); 
		//根据staff_id取得对应的staff_rank
		staff staff=staffdao.getstaffById(staff_id);
		int staff_rank=staff.getStaff_rank();
		//查询出对应的配送人员信息
		//未配送订单应该根据staff_id查询出相关人员列表并与订单信息合并返回
		//暂时不考虑DIY，只考虑默认订单的问题
		int default_order_id=orderdao.get_default_order_idByid(order_id);
		List<order_item> orderitems= orderitemdao.getOrderitemByDefaultOrderId(default_order_id);
		Order order=orderdao.select_order_info(order_id);
		
		if(order!=null){
			//status=10 or =20
			staffs= organizationService.listAllstaff(staff_id,staff_rank);
		}else{
			order=orderdao.select_order_info_withoutstatus(order_id);
		}
		JSONObject orderinfo=new JSONObject();
		Snacks snack=null;
		List<order_item> orderitems2=new ArrayList<order_item>();
		for(order_item item:orderitems){
			
			snack=snackdao.getSnacksById(item.getSnacks_id());
			item.setSnacks_bar_code(snack.getSnacks_bar_code());
			item.setSnacks_name(snack.getSnacks_name());
			item.setSnacks_sell_price(snack.getSnacks_sell_price());
			orderitems2.add(item);
			
		}
		//所在寝室、楼栋、学校
		dormitory dor=staffDao_common.getDormitoryNameById(order.getDormitory_id());
		building bui =staffDao_common.getBuildingNameById(dor.getBuilding_id());
		orderinfo.put("dormitory_name", dor.getDormitory_name());
		orderinfo.put("building_name", bui.getBuilding_name());
		orderinfo.put("school_name", staffDao_common.getSchoolNameById(bui.getBuilding_id()));
		orderinfo.put("order_query_id", order.getOrder_query_id());
		orderinfo.put("order_create_time", order.getOrder_create_time());
		orderinfo.put("order_cost_money", order.getOrder_cost_money());
		orderinfo.put("order_note", order.getOrder_note());
		result.put("orderinfo", orderinfo);
		result.put("stafflist", staffs);
		result.put("orderitemlist", orderitems2);
		return result;
	}
	
	//负责人员点击管理未配送订单查询信息：
		public Object order_id_select(long order_id){
			HashMap<String,Object> result=new HashMap<String,Object>(); 
			//未配送订单应该根据staff_id查询出相关人员列表并与订单信息合并返回
			//暂时不考虑DIY，只考虑默认订单的问题
			int default_order_id=orderdao.get_default_order_idByid(order_id);
			List<order_item> orderitems= orderitemdao.getOrderitemByDefaultOrderId(default_order_id);
			Order order=orderdao.select_order_info(order_id);
			if(order==null){
				//status=10 or =20
				order=orderdao.select_order_info_withoutstatus(order_id);
			}
			JSONObject orderinfo=new JSONObject();
			Snacks snack=null;
			List<order_item> orderitems2=new ArrayList<order_item>();
			for(order_item item:orderitems){
				
				snack=snackdao.getSnacksById(item.getSnacks_id());
				item.setSnacks_bar_code(snack.getSnacks_bar_code());
				item.setSnacks_name(snack.getSnacks_name());
				item.setSnacks_sell_price(snack.getSnacks_sell_price());
				orderitems2.add(item);
				
			}
			//所在寝室、楼栋、学校
			dormitory dor=staffDao_common.getDormitoryNameById(order.getDormitory_id());
			building bui =staffDao_common.getBuildingNameById(dor.getBuilding_id());
			orderinfo.put("dormitory_name", dor.getDormitory_name());
			orderinfo.put("building_name", bui.getBuilding_name());
			orderinfo.put("school_name", staffDao_common.getSchoolNameById(bui.getBuilding_id()));
			orderinfo.put("order_query_id", order.getOrder_query_id());
			orderinfo.put("order_create_time", order.getOrder_create_time());
			orderinfo.put("order_cost_money", order.getOrder_cost_money());
			orderinfo.put("order_note", order.getOrder_note());
			result.put("orderinfo", orderinfo);
			result.put("orderitemlist", orderitems2);
			return result;
		}
	public Long addOrder(Order order){
		if(orderdao.insertOrder(order)){
			return orderdao.getNewOrder(order.getDormitory_id());
		}else{
			return Long.valueOf(0);
		}
	}
	
	public List<Order> getOrderByStaff(long staff_id,int order_status){
		return orderdao.getOrderByStaff(staff_id,order_status);
	}
	public List<Order> getAllOrder() {
		return orderdao.getAllOrder();
	}
	
	public Integer getPushCount(long orderId) {
		return orderdao.get_order_push_count(orderId);
	}
	
	public dormitory getDormitoryId(long orderId) {
		return orderdao.get_dormitory_id(orderId);
	}
	
	public Integer getNotCompleteOrderCount(long dormitoryId) {
		return orderdao.getNotCompleteOrderCount(dormitoryId);
	}
	
	public void setPushCount(long orderId,int count) {
		orderdao.update_order_push_count(orderId, count);
	}
	
	public List<Order> select_order(int page,int pageSize){
		int  count = (page-1)*pageSize;
		return  orderdao.select_order_1(count,pageSize);
		
	}
/**
 * 计算表在  某size下的页数
 * @param tablenam   表名
 * @param pageSize
 * @return返回page对象
 */
	public Page countpage(int pageSize,int order_status){
		long count=0;
		if(order_status==0)
		 count=orderdao.countpage();
		else
		 count=orderdao.countpage1(order_status);
		//Double pagecount=Math.ceil(count/pageSize);
		Page page=new Page();
		page.setPagecount(count);
		page.setPagesize(pageSize);
		return page;
		
	}

public int boundorder(Long order_id, Long staff_id, Long dormitory_id,String order_query_id) {
	
	int n=orderdao.updateOrder(order_id,dormitory_id,staff_id,order_query_id);
	return n;
}
}
