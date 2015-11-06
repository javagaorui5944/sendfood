package com.fenghuo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 物流系统，
 * 订单操作
 *
 * */
@Controller
@RequestMapping("/admin")
public class businessController {
	

    //物流列表
	 @RequestMapping(value="/business/businessList")
	 public String businessList(){
		 
		 return "/admin/business/businessList";
	 }
	 

}
