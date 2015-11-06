package com.fenghuo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fenghuo.dao.StaffDao_common;
import com.fenghuo.domain.Order;
import com.fenghuo.domain.Snacks;
import com.fenghuo.domain.staff;
import com.fenghuo.quartz.BalanceJob;
import com.fenghuo.quartz.LoadTask;
import com.fenghuo.service.CustomerService;
import com.fenghuo.service.OrderService;
import com.fenghuo.service.Order_ItemService;
import com.fenghuo.service.Sell_itemService;
import com.fenghuo.util.CommonUtil;
/**
 * 订单管理
 * */
@Controller
@RequestMapping("/orderManage")
public class OrderManageController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private StaffDao_common staffDao_common;
	@Autowired
	private CustomerService customerSerice;
	@Autowired
	private Order_ItemService orderItemService;
	@Autowired
	private LoadTask loadTask;
	@Autowired
	private Sell_itemService sellItemService;
	
	
	@RequestMapping("/manageStandardOrder")
	public String manageStandardOrder(HttpSession httpSession){
		return "/orderManage/manageStandardOrder";
	}
	@RequestMapping("/addStandardOrder")
	public String addStandardOrder(HttpSession httpSession,Model model){
		return "/orderManage/addStandardOrder";
	}
	/**
	 * 订单管理页面载入
	 * @param page 页码，默认为1
	 * @param order_status 订单状态，为空时查找所有状态(0未分配状态，10表示未配送，20表示配送中，30表示配送完成，40表示结算完成，50表示返回入库，60表示已返回入库)
	 * @param key 按关键字查找，为空时不按关键字查找（order_id-订单号、staff_id-配送人员编号）
	 * @param value 查找关键字、key不为空时起作用
	 */
	@RequestMapping(value="/getAllOrder",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject getAllOrder(HttpSession httpSession,
			@RequestParam(required=false,value="page")Long page,
			@RequestParam(required=false,value="order_status")Integer order_status,
			@RequestParam(required=false,value="key")String key,
			@RequestParam(required=false,value="value")String value){
		staff staff = (staff)httpSession.getAttribute("staff");  //获取员工
		if(staff == null){
			return CommonUtil.constructResponse(0, "员工不存在", null);
		}
		JSONObject jo = new JSONObject();
		List<Order> orders = new ArrayList<Order>();
		if(page == null || page <=0){
			page = Long.valueOf(1);
		}
		long total = orderService.getCountOrderManage(staff.getStaff_id(), staff.getStaff_rank(), order_status, key, value);
		if(total > 0){
			orders = orderService.getOrderManage(staff.getStaff_id(), staff.getStaff_rank(), order_status, page, 10, key, value);
		}
		jo.put("total", total);
		jo.put("nowPage", page);
		jo.put("orders", orders);
		return CommonUtil.constructResponse(1,"success",jo);
	}

	
	/**
	 * 订单管理页面手机端载入
	 * @param page 页码，默认为1
	 * @param pageSize 页面大小，默认为10
	 * @param order_status 订单状态，为空时查找所有状态(0未分配状态，10表示未配送，20表示配送中，30表示配送完成，40表示结算完成，50表示返回入库，60表示已返回入库)
	 */
	@RequestMapping(value="/getAllOrderByPhone",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject getAllOrderByPhone(HttpSession httpSession,
			@RequestParam(required=false,value="page")Long page,
			@RequestParam(required=false,value="pageSize")Integer pageSize,
			@RequestParam(required=false,value="order_status")Integer order_status){

		staff staff = (staff)httpSession.getAttribute("staff");  //获取员工
		if(staff == null){
			return CommonUtil.constructResponse(0, "员工不存在", null);
		}
		JSONObject jo = new JSONObject();
		List<Order> orders = new ArrayList<Order>();
		if((Long)page == null || page <=0){   //默认page为1
			page = Long.valueOf(1);
		}
		if((Integer)pageSize == null || pageSize <=0){  // 默认pageSize为10
			pageSize = 10;
		}
		long total = orderService.getCountOrderManageByPhone(staff.getStaff_id(),order_status);
		if(total > 0){
			orders = orderService.getOrderManageByPhone(staff.getStaff_id(), order_status, page, pageSize);
			for(Order o:orders){
				o.setPhones(customerSerice.getPhones(o.getDormitory_id()));
				o.setItems(orderItemService.getOrderitemByDefaultOrderId_3(o.getDefault_order_id()));
				if(o.getOrder_status()>= 40){
					o.setSell_items(orderItemService.getSellitemByOrderId(o.getOrder_id()));
				}else{
					o.setSell_items(new ArrayList<Snacks>());
				}
			}
		}
		jo.put("total", total);
		jo.put("nowPage", page);
		jo.put("orders", orders);
		return CommonUtil.constructResponse(1,"success",jo);
	}
	
	
	/**
	 * 修改订单状态
	 * @param orderId 要修改的订单编号
	 * @param order_status 修改后的订单状态
	 * @throws SchedulerException 
	 */
	@RequestMapping(value="/updateOrderByPhone",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject updateOrderByPhone(HttpSession httpSession,
			@RequestParam("order_id")Long order_id,
			@RequestParam(required=false,value="order_note")String order_note,
			@RequestParam("order_status")Integer order_status) throws SchedulerException{
		staff staff = (staff)httpSession.getAttribute("staff");  //获取员工
		if(staff == null){
			return CommonUtil.constructResponse(0, "员工不存在", null);
		}
		int oldStatus = order_status - 10;
		if(orderService.updateOrder(order_id, order_status,oldStatus,order_note)){
			if(order_status == 30){
				loadTask.delTask(order_id+"", "DistributionJob");
				loadTask.addBalanceTask(new BalanceJob(order_id));
			}
			return CommonUtil.constructResponse(1,"修改成功",null);
		}
		return CommonUtil.constructResponse(0,"修改失败",0);
	}
	
	


			/**
			 * 用户预定下周美食
			 * 通过用户编号获取标准订单再添加订单
			 */
			@RequestMapping(value="/getOrderByStaff",method=RequestMethod.GET)
			@ResponseBody
			public JSONObject getOrderByStaff(@RequestParam("staff_id") long staff_id,@RequestParam("order_status") int order_status){
				return CommonUtil.constructResponse(1,"success",orderService.getOrderByStaff(staff_id, order_status));
			}
			
			
		
		
		
			//状态2：对订单进行配送（提供配送人员id和订单id）
		
			/**
			*@param    int order_id,int staff_id
			*@need 修改配送人员的id
			*/
		@RequestMapping(value="/staff_update",method=RequestMethod.GET)
		@ResponseBody
			public Object  order_staff_update( int orderid, int staff_id ){
				boolean result= false;
				result=orderService.order_staff_update(orderid,staff_id);
				System.out.println("orderid"+orderid);
				return  CommonUtil.constructResponse(1,"success",result);
			}
		
		
		//状态3：配送完成（仅需要订单id）
			/**
			*@throws SchedulerException 
			 * @param需要提供订单id
			*@need 将订单的状态修改为以送达，将状态修改30
			*/
		@RequestMapping(value="/order_sended_update",method=RequestMethod.GET)
		@ResponseBody
			public Object  order_sended_update(int orderid) throws SchedulerException{
				boolean result=false;
				result=orderService.order_sended_update(orderid);
				if(result){
					loadTask.delTask(orderid+"", "DistributionJob");
					loadTask.addBalanceTask(new BalanceJob(orderid));
				}
				return CommonUtil.constructResponse(1,"success",result);
			}
		
		/**
		*@param需要提供订单id，物品的消耗情况
		*@need 订单进行结算，将状态修改为40，插入一条销售记录，并计算出对应订单的销售金*额的并修改（信息来源配送人员）
		*/
		@RequestMapping(value="/order_balance_update",method=RequestMethod.POST)
		@ResponseBody
			public JSONObject  order_balance_update(HttpSession httpSession,long order_id,String sell_items){
			staff staff = (staff)httpSession.getAttribute("staff");  //获取员工
			if(staff == null){
				return CommonUtil.constructResponse(0, "员工不存在", null);
			}
			JSONObject jsonObject = JSONObject.parseObject(sell_items);
			JSONArray goods = (JSONArray)jsonObject.get("foods");
			List<Snacks> snacks = orderItemService.getOrderitemByDefaultOrderId_4(order_id);
			Float order_cost_money = Float.valueOf(0);
			Float order_sell_money = Float.valueOf(0);
			for(Object j:goods){
				JSONObject jo = JSONObject.parseObject(j.toString());
				for(Snacks s:snacks){
					long snacks_id = Long.valueOf(jo.get("snacks_id").toString());
					if(snacks_id == s.getSnacks_id()){
						Integer nu = jo.getInteger("snacks_num");
						if(nu == null || nu < 0 || nu > s.getSnacks_number()){
							return CommonUtil.constructResponse(0, "食品数量返回错误", null);
						}else{
							order_cost_money += nu*s.getSnacks_cost_price();
							order_sell_money += nu*s.getSnacks_sell_price();
						}
					}
				}
			}
			for(Object j:goods){
				JSONObject jo = JSONObject.parseObject(j.toString());
				long snacks_id = Long.valueOf(jo.get("snacks_id").toString());
				Integer snacks_number = jo.getInteger("snacks_num");
				sellItemService.insertsell_items(snacks_id, snacks_number, order_id);
			}
			orderService.update_order_price_1(order_id, order_cost_money, order_sell_money);
			loadTask.delTask(order_id+"", "BalanceJob");
			loadTask.delTask(order_id+"", "BalanceJob_1");
			return CommonUtil.constructResponse(1,"结算完成",0);
			}
		
		
		//状态5：返货入库
		/**
		*@param需要提供订单id，物品的消耗情况  当operation 字段为空的时候，将状态修改为50.没有则仅为查询。
		*@need 订单返货入库，将状态修改为50
		*/
		@RequestMapping(value="/order_back_select",method=RequestMethod.GET)
		@ResponseBody
		public Object  order_back_select(Long order_id,@RequestParam(required=false,value="operation")Integer operation){
			Object ob=orderService.order_back_select(order_id,operation);
			return CommonUtil.constructResponse(1,"success",ob);
		}
		
		/**
		 * 根据订单编号查看订单消耗情况
		*/
		@RequestMapping(value="/getOrderBack",method=RequestMethod.GET)
		@ResponseBody
		public Object  getOrderBack(long order_id){
			String order_note = orderService.getOrderNote(order_id);
			List<Snacks> orderItems = orderItemService.getOrderitemByDefaultOrderId_4(order_id);
			List<Snacks> sellItems = orderItemService.getSellitemByOrderId_1(order_id);
			for(Snacks s:sellItems){
				for(Snacks ss:orderItems){
					if(ss.getSnacks_id() == s.getSnacks_id()){
						ss.setEat_number(s.getSnacks_number());
					}
				}
			}
			JSONObject js = new JSONObject();
			js.put("order_note", order_note);
			js.put("orderItems", orderItems);
			return CommonUtil.constructResponse(1,"success",js);
		}
		
		//管理人员对未分配的订单进行人员分配页面的进入
		/**
		*@param需要提供订单id，物品的消耗情况 staff_id 员工id
		*@need 返回订单的视图，分配人员列表（缺省就不返回）
		*/
		@RequestMapping(value="/orderList",method=RequestMethod.GET)
		@ResponseBody
		public Object orderList(@RequestParam(value="order_id")Long order_id,@RequestParam(required=false,value="staff_id") Integer staff_id){
			Object ob=null;
			if(staff_id==null){
				System.out.println(order_id);
				ob = orderService.order_id_select(order_id);
			}
			else{
				ob=orderService.order_fit_select(order_id, staff_id);
			}
			return CommonUtil.constructResponse(1,"success",ob);
		}

		/**
		*test
		*/
		@RequestMapping(value="/multiselect",method=RequestMethod.GET)
		@ResponseBody
		public Object getOrder_multi_demension_select(@RequestParam(required=false,value="time_begin")Long time_begin,@RequestParam(required=false,value="time_end")Long time_end,@RequestParam(required=false,value="dormitory_id")Integer dormitory_id,@RequestParam(required=false,value="building_id")Integer building_id,@RequestParam(required=false,value="school_id")Integer school_id,@RequestParam(required=false,value="order_sell_money_top")Integer order_sell_money_top,@RequestParam(required=false,value="order_sell_money_bottom")Integer order_sell_money_bottom,@RequestParam(required=false,value="snacks_id")Integer  snacks_id,@RequestParam(required=false,value="snacks_number") Integer snacks_number,@RequestParam(required=false,value="staff_id")Integer  staff_id,@RequestParam(required=false,value="order_status")Integer  order_status,@RequestParam(required=false,value="sort_key")String  sort_key,@RequestParam(required=false,value="sort_method")String  sort_method ,@RequestParam(required=false,value="page")Long page, @RequestParam(required=false,value="pageSize")Integer pageSize){
			System.out.println(time_begin);
			
			Object ob=null;
			
				ob=orderService.getOrder_multi_demension_select(time_begin,time_end,dormitory_id,building_id,school_id,order_sell_money_top,order_sell_money_bottom, snacks_id, snacks_number, staff_id, order_status, sort_key, sort_method ,page, pageSize);

			return CommonUtil.constructResponse(1,"success",ob);
		}

		/*
		 *用于绑定订单 
		 */
		@RequestMapping(value="/boundorder",method=RequestMethod.POST)
		@ResponseBody
		public Object boundOrder(Long order_id,Long staff_id,String dormitory_name){
			int n=orderService.boundorder(order_id,staff_id,dormitory_name);
			if(n==1)
				return CommonUtil.constructResponse(1,"success",null);
			else
				return CommonUtil.constructResponse(0,"false",null);
		}
}
