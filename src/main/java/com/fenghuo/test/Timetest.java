package com.fenghuo.test;

import com.alibaba.fastjson.JSONObject;

public class Timetest {
	public static void main(String[] args) {
		JSONObject tempstr=new JSONObject();
		int time=100;
		long pre=System.currentTimeMillis();
		for(int i=0 ;i<100;i++){
			tempstr.put("{order_id:",1);
			tempstr.put("order_query_id:",1);
			//学校、楼栋、寝室的查询
			tempstr.put("dormitory_name:",1);
			tempstr.put("building_name:",1);
			tempstr.put("school_name:",1);
			tempstr.put("staff_name:",1);
			tempstr.put("order_create_time:",1);
			tempstr.put("order_status:",1);
		}
		long post=System.currentTimeMillis();
		System.out.println(pre+"---------"+post+tempstr);
		
		
		
		
		
		
		
	}
}
