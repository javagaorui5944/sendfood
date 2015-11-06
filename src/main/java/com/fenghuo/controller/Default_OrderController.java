package com.fenghuo.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fenghuo.domain.Default_order;
import com.fenghuo.domain.Order;
import com.fenghuo.domain.customer;
import com.fenghuo.domain.staff;
import com.fenghuo.model.codeAndAdderss;
import com.fenghuo.quartz.DistributionJob;
import com.fenghuo.quartz.LoadTask;
import com.fenghuo.service.Default_OrderService;
import com.fenghuo.service.OrderService;
import com.fenghuo.service.Order_ItemService;
import com.fenghuo.service.UserService;
import com.fenghuo.util.CommonUtil;

@Controller
@RequestMapping("/order")
public class Default_OrderController {
	@Autowired
	private Default_OrderService default_OrderService;
	@Autowired
	private Order_ItemService orderItemService;
	@Autowired
	private UserService userService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private LoadTask loadTask;
	@Autowired
	private Order_ItemService orderitemService;

	/*
	 * 新添加一个标准订单
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public String insertDefaultOrder(String default_order_name,
			int default_order_status) {
		Default_order df_odr = new Default_order();
		df_odr.setDefault_order_name(default_order_name);
		df_odr.setDefault_order_status(default_order_status);
		int n = default_OrderService.InsertDefaultOrder(df_odr);
		if (n == 1) {
			return "true";
		}
		return "false";
	}

	/*
	 * 通过学校的id，得到这个学校的标准订单,status =10,是有效订单(gr已修改) 通过标准订单id得到所有学校的标准订单
	 */
	@RequestMapping(value = "/getDefaultOrderBySchId", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getDefaultOrderBySchId(long school_id) {

		List<Default_order> ls = default_OrderService.getDefaultOrderBySchId(
				school_id, 10);
		JSONArray js = new JSONArray();
		if (ls != null) {
			for(int i=0;i<ls.size();i++){
				JSONArray jsonArray = orderitemService
						.getAllSnacksByDefaultOrderId_1(ls.get(i).getDefault_order_id());
				JSONObject jsb=new JSONObject(); 
				jsb.put("default_order_name", ls.get(i).getDefault_order_name());
				jsb.put("default_order_id", ls.get(i).getDefault_order_id());
				jsb.put("orderItems", jsonArray);
				js.add(jsb);
			}
		}
		return CommonUtil.constructResponse(1, "success", js);
	}

	/*
	 * 通过学校的id，得到这个学校的标准订单的具体内容 获取学校标准套餐集合，取第一个为默认标准套餐 根据套餐编号获取具体内容
	 */
	@RequestMapping(value = "/getNextWeekOrder", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getNextWeekOrder(
			@RequestParam("school_id") long school_id, HttpSession httpSession) {
		customer c = (customer) httpSession.getAttribute("customer");
		if (c == null) {
			Object o = default_OrderService.getDefaultOrder_1(school_id);
			return CommonUtil.constructResponse(1, "success", o);
		} else {
			long dormitory_id = c.getDormitory_id();
			Object o1 = default_OrderService
					.getDefaultOrderByDormitory_Id(dormitory_id);
			if (o1 == null) {
				return CommonUtil.constructResponse(0, "还没有定制标准套餐", null);
			} else {
				JSONObject jo = new JSONObject();
				Object o = default_OrderService.getDefaultOrder_1(school_id);
				jo.put("default_order_id", (Long) o1);

				jo.put("default_orders", o);
				return CommonUtil.constructResponse(1, "success", jo);
			}
		}

		/*
		 * if (o == null) { return CommonUtil.constructResponse(0, "还没有定制标准套餐",
		 * null); }
		 */

	}

	/*
	 * 用户预定下周美食 通过用户编号获取标准订单再添加订单
	 */
	@RequestMapping(value = "/scheduleOrder", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject scheduleOrder(HttpSession request, boolean sure)
			throws SchedulerException {
		customer cus = (customer) request.getAttribute("customer");
		if (cus == null) {
			return CommonUtil.constructResponse(0, "用户未登录", null);
		}
		if (!sure) {
			int noSend = default_OrderService.getNoSendOrder(cus
					.getDormitory_id());
			if (noSend > 0) {
				return CommonUtil.constructResponse(2,
						"已经有" + noSend + "订单未配送", noSend);
			}
		}
		Long ls = default_OrderService.getDefaultOrder(cus.getSchool_id());
		if (ls == null || ls == 0) {
			return CommonUtil.constructResponse(0, "还没有定制标准套餐", null);
		}
		staff staff = userService.getStaffBydormitory(cus.getDormitory_id());
		if (staff == null) {
			List<staff> staffs = userService.getStaffByBuilding(cus
					.getBuilding_id());
			if (staffs == null || staffs.size() == 0) {
				staffs = userService.getStaffBySchool(cus.getSchool_id());
				if (staffs == null || staffs.size() == 0) {
					return CommonUtil.constructResponse(0, "负责人获取失败", null);
				} else {
					staff = staffs.get(0);
				}
			} else {
				staff = staffs.get(0);
			}
		}
		Order order = new Order();
		// orderItemService.setOrderPrice(ls.get(0).getDefault_order_id(),order);
		// //添加订单的成本和售价
		java.util.Date dt = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置显示格式
		String nowTime = "";
		codeAndAdderss caa = default_OrderService
				.getOrderCode(cus.getSchool_id(), cus.getBuilding_id(),
						cus.getDormitory_id());
		nowTime = df.format(dt);
		order.setOrder_create_time(nowTime);
		order.setDormitory_id(cus.getDormitory_id());
		order.setDefault_order_id(ls);
		order.setOrder_status(10);
		order.setOrder_query_id(caa.getCode()); // 设置订单code
		order.setStaff_id(staff.getStaff_id());
		Long newOrderId = orderService.addOrder(order);
		if (newOrderId != null && newOrderId > 0) {
			// new MsgPush().sendMsg(staff.getStaff_tel()+"", newOrderId,
			// caa.getAddress());
			loadTask.addDistributionTask(new DistributionJob(newOrderId, staff
					.getStaff_tel(), caa.getAddress()));
			return CommonUtil.constructResponse(1, "success", null);
		}
		return CommonUtil.constructResponse(0, "添加失败", null);
	}

	/**
	 * gr 通过学校id获取所有状态为10的标准订单， return default_order_id,default_order_name
	 */
	@RequestMapping(value = "/getDefaultOrderBySchool_id", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getDefaultOrder_IdNameBySchool_id(long school_id) {

		JSONArray json = default_OrderService
				.getDefaultOrder_IdNameBySchool_id(school_id);
		if (json == null || json.equals("")) {
			return CommonUtil.constructResponse(0, "还没有定制标准套餐", null);
		}
		return CommonUtil.constructResponse(1, "success", json);
	}

}
