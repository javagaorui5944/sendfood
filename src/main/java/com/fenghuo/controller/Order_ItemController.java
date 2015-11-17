package com.fenghuo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fenghuo.dao.Default_OrderXmlDao;
import com.fenghuo.domain.Default_order;
import com.fenghuo.domain.school;
import com.fenghuo.domain.staff;
import com.fenghuo.service.AreaService;
import com.fenghuo.service.Default_OrderService;
import com.fenghuo.service.Order_ItemService;
import com.fenghuo.util.CommonUtil;
import com.fenghuo.util.MybatisUtil;

@Controller
@RequestMapping("/OrderItem")
public class Order_ItemController {
	@Autowired
	private Order_ItemService orderitemService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private Default_OrderService default_OrderService;
	@Autowired
	private Default_OrderXmlDao default_OrderXmlDao;

	/**
	 * 通过员工获取其所能修改标准订单学校的集合
	 */

	@RequestMapping(value = "/getAllSchool", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getAllSchool(HttpSession httpSession) {
		staff staff = (staff) httpSession.getAttribute("staff"); // 获取员工
		if (staff == null) {
			return CommonUtil.constructResponse(0, "员工不存在", null);
		}
		List<school> schools = new ArrayList<school>();
		if (staff.getStaff_rank() == 30) { // 学校负责人
			schools = areaService.getMySchool(staff.getStaff_id());
		} else if (staff.getStaff_rank() == 40) {
			schools = areaService.getMySchool_1(staff.getStaff_id());
		}
		return CommonUtil.constructResponse(1, "success", schools);
	}

	/**
	 * 通过标准订单得到订单下面的所有零食，并且分页
	 */

	@RequestMapping(value = "/getAllSnacks", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getAllSnacks(long default_order_id) {
		JSONArray jsonArray = orderitemService
				.getAllSnacksByDefaultOrderId_1(default_order_id);
		return CommonUtil.constructResponse(1, "success", jsonArray);
	}

	/*
	 * 删除标准订单下面的零食通过default_order_id、snacks_id
	 */

	@RequestMapping(value = "/delSnacksfromDefaultOrderById", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject delSnacksfromDefaultOrderById(long default_order_id,
			long snacks_id) {
		String states = null;
		int n = orderitemService.delSnacksfromDefaultOrderById(
				default_order_id, snacks_id);
		if (n > 0) {
			return CommonUtil.constructResponse(1, "true", "");
		} else {
			return CommonUtil.constructResponse(0, "false", "");
		}
	}

	/*
	 * 通过零食名字模糊查询搜索商品，并且分页显示
	 */
	@RequestMapping(value = "/getAllSnacksByName", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getAllSnacksByName(String snacks_name, long school_id,
			@RequestParam(value = "page", required = false) Integer page) {
		if (page == null || page < 1) {
			page = 1;
		}
		JSONArray jsonArray = orderitemService.getSnacksByName("%"
				+ snacks_name + "%", page, school_id);
		return CommonUtil.constructResponse(1, "success", jsonArray);
	}

	/**
	 * (gr修改)保存改变后的标准订单 ,功能为增加货物到该标准订单,修改redis中的标准订单缓存 保存改变后的标准订单
	 * 将原标准订单设置为不可用，修改redis中的标准订单缓存
	 * 
	 * @param default_order_id
	 *            原标准订单编号
	 * @param newOrderItem
	 *            包含新标准套餐中的项目内容以及新标准订单的名字，json格式，
	 */
	@RequestMapping(value = "/saveNewDefaultOrder", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject saveNewDefaultOrder(String newOrderItem) {

		if (newOrderItem == null) {
			return CommonUtil.constructResponse(0, "套餐无内容", null);
		}
		JSONObject jo = JSONObject.parseObject(newOrderItem);
		// default_OrderService.updateStatus(jo.getLongValue("school_id"), 1);
		// //(gr测试新版本上行代码作废)将旧订单设置为不可用
		long school_id = jo.getLongValue("school_id"); // 通过原标准套餐获取学校
		String default_order_name = jo.getString("default_order_name"); // 获取新套餐的名字
		long newDefault_order_id = jo.getLongValue("Default_order_id");
		JSONArray array = jo.getJSONArray("OrderItem"); // 获取新套餐内容
		/*
		 * long newDefault_order_id =
		 * default_OrderService.addDefaultOrder(school_id, default_order_name);
		 * //添加新订单并返回新订单编号
		 */

		// (gr测试新版本)下段代码作废,此@RequestMapping方法为点击添加功能。
		/*
		 * order.setSchool_id(school_id);
		 * order.setDefault_order_name(default_order_name);
		 * order.setDefault_order_status(10); long newDefault_order_id = 0;
		 * newDefault_order_id = default_OrderXmlDao.add(order);
		 */
		
		if(newDefault_order_id!=0){
			deleteDefaultOrder(newDefault_order_id);
		}
		
		Default_order order = new Default_order();
		order.setSchool_id(school_id);
		order.setDefault_order_name(default_order_name);
		order.setDefault_order_status(10);
		newDefault_order_id = default_OrderXmlDao.add(order);
		 		 
		for (Object o : array) {
			JSONObject j = JSONObject.parseObject(o.toString());
			orderitemService
					.saveOrderItem(newDefault_order_id,
							j.getLongValue("snacks_id"),
							j.getIntValue("snacks_number")); // 添加订单子项目
		}
		default_OrderService.updateRedisOrder(school_id); // 更新redis数据
		return CommonUtil.constructResponse(1, "success", null);
	}

	/**
	 * 为寝室添加保准订单(gr) param List<Long>,default_order_id
	 */
	@RequestMapping(value = "/addDefault_orderTodormitory", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject addDefault_orderTodormitory(
			@RequestParam(required = false, value = "dormitory_ids") List<Long> dormitory_ids,
			@RequestParam(required = false, value = "default_order_id") Long default_order_id) {

		if (orderitemService.addDefault_orderTodormitory(dormitory_ids,
				default_order_id) > 0) {
			return CommonUtil.constructResponse(1, "success", null);
		} else if (dormitory_ids == null || dormitory_ids.equals("")
				|| default_order_id == 0) {
			return CommonUtil.constructResponse(0, "false", null);
		}

		return CommonUtil.constructResponse(0, "false", null);

	}

	/**
	 * 增加标准订单,只增加default_order表中的数据(gr)
	 */
	@RequestMapping(value = "/addDefaultOrder", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject addDefaultOrder(String default_order_name, long school_id) {

		Default_order order = new Default_order();
		order.setSchool_id(school_id);
		order.setDefault_order_name(default_order_name);
		order.setDefault_order_status(10);
		long newDefault_order_id = 0;
		newDefault_order_id = default_OrderXmlDao.add(order);
		if (newDefault_order_id == 0) {
			return CommonUtil.constructResponse(0, "添加失败", null);
		}
		return CommonUtil.constructResponse(1, "添加成功", null);

	}

	/**
	 * 删除套餐,将DefaultOrder_status变为1(gr)
	 */
	@RequestMapping(value = "/deleteDefaultOrder", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteDefaultOrder(long default_order_id) {
		if (default_OrderService.updateStatus(default_order_id, 1) > 0) {
			return CommonUtil.constructResponse(1, "success", null);
		} else {
			return CommonUtil.constructResponse(0, "false", null);
		} 													

	}

}
