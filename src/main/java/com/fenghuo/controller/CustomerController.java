package com.fenghuo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;






import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fenghuo.domain.Order1;
import com.fenghuo.domain.OrderDetails1;
import com.fenghuo.domain.OrderDetails2;
import com.fenghuo.domain.customer;
import com.fenghuo.domain.orders;
import com.fenghuo.service.CustomerService;
import com.fenghuo.service.StorageService;
import com.fenghuo.util.CommonUtil;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerservice;

	@Autowired
	private StorageService storageService;




	/**
	 * 取得所有学校信息
	 * */

	@RequestMapping(value = "/getAllSchool", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getAllSchool() {
		return CommonUtil.constructResponse(1, "success",
				storageService.getAllSchool());
	}

	@RequestMapping(value = "/getcustomer", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getcustomer(@RequestParam long id) {
		List<customer> customer = customerservice.getcustomer(id);
		return CommonUtil.constructResponse(1, "success", customer);
	}

	@RequestMapping(value = "/updatecustomer", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updatecustomer(@RequestParam("text") String text) {
		List<customer> updatecustomer = JSON.parseArray(text, customer.class);
		if (updatecustomer.get(0).getCustomer_name() == null
				|| "".equals(updatecustomer.get(0).getCustomer_name())) {
			return CommonUtil.constructResponse(0, "名字为空", updatecustomer);
		}
		if (updatecustomer.get(0).getCustomer_tel() == null
				|| "".equals(updatecustomer.get(0).getCustomer_tel())) {
			return CommonUtil.constructResponse(0, "电话为空", updatecustomer);
		}
		if (updatecustomer.get(0).getCustomer_email() == null
				|| "".equals(updatecustomer.get(0).getCustomer_email())) {
			return CommonUtil.constructResponse(0, "邮箱为空", updatecustomer);
		}
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(updatecustomer.get(0).getCustomer_email());
		if (m.matches() != true) {
			return CommonUtil.constructResponse(0, "邮箱格式不正确", updatecustomer);
		}
		int n = 0;
		for (customer t : updatecustomer) {
			n = customerservice.updatecustomer(t);
		}
		if (n == 1) {
			return CommonUtil.constructResponse(1, "修改成功", updatecustomer);
		} else {
			return CommonUtil.constructResponse(0, "修改失败", updatecustomer);
		}
	}

	@RequestMapping(value = "/updatepassword", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updatepassword(@RequestParam long id,
			@RequestParam String dormitory_password,
			@RequestParam String Newdormitory_password1,
			@RequestParam String Newdormitory_password2) {
		String dormitory_pass = customerservice.selectpassword(id);
		if (dormitory_password == null || dormitory_password == "") {
			return CommonUtil.constructResponse(0, "原密码为空", null);
		}
		if (dormitory_password != dormitory_pass) {
			return CommonUtil.constructResponse(0, "原密码不正确", null);
		}
		if (Newdormitory_password1 == null || Newdormitory_password1.equals("")) {
			return CommonUtil.constructResponse(0, "新密码1为空", null);
		}
		if (Newdormitory_password2 == null || Newdormitory_password2.equals("")) {
			return CommonUtil.constructResponse(0, "新密码2为空", null);
		}

		if (Newdormitory_password1 != Newdormitory_password2) {
			return CommonUtil.constructResponse(0, "新密码不一致", null);
		} else {
			int n = 0;
			n = customerservice.updatepassword(id, Newdormitory_password1);
			if (n == 1) {
				return CommonUtil.constructResponse(1, "success", null);
			} else {
				return CommonUtil.constructResponse(0, "修改失败", null);
			}
		}

	}

	/**
	 * 顾客查看订单历史
	 */
	@RequestMapping(value = "/selectOrdersHistory", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject selectOrdersHistory(HttpSession httpSession,HttpServletResponse response) {
		customer c = (customer) httpSession.getAttribute("customer");

		if (c != null) {
			long customer_id = c.getCustomer_id();
			Order1 o1 = new Order1();
			List<Order1> order1 = new ArrayList<Order1>();
			List<Order1> order = customerservice
					.selectOrdersByDormitory_id(customer_id);
			for(int i =0 ; i < order.size() ; i++){
				o1 = order.get(i);
				
				o1.setCustomer_name(c.getCustomer_name());
				o1.setCustomer_tel(c.getCustomer_tel());
				order1.add(o1);
			}
			
			order = order1 ; 
			return CommonUtil.constructResponse(1, "orders", order);
		}
		else{
			try {
				response.sendRedirect("/index");
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			return null;
		}

	}
	
	
	/**
	 * 顾客查看单个订单详情
	 */
	@RequestMapping(value = "/selectOrderDetails", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject selectOrderDetails(@RequestParam long order_id1,@RequestParam int order_status) {

	/*	orders o = customerservice.getOrderByorder_Id(order_id1);
		long default_order_id = o.getDefault_order_id();*/
		
	
		if(order_status>=40){
			List<OrderDetails2> od2	= customerservice.selectOrderDetails2(order_id1);
			return CommonUtil.constructResponse(1, "od2", od2);
		}else{
			List<OrderDetails1> od1 = customerservice.selectOrderDetails1(order_id1);
			return CommonUtil.constructResponse(1, "od1", od1);}
		}
	

}
