package com.fenghuo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fenghuo.service.AreaService;
import com.fenghuo.util.CommonUtil;

@Controller
@RequestMapping("/area")
public class AreaController {

	@Autowired
	private AreaService areaService;
	
	@RequestMapping("/getSchool")
	@ResponseBody
	public JSONObject getSchool(){
		JSONObject jo = new JSONObject();
		jo.put("schools", areaService.getSchool());
		return CommonUtil.constructResponse(1, "success", jo);
	}
	
	@RequestMapping("/getBuilding")
	@ResponseBody
	public JSONObject getBuilding(long school_id){
		JSONObject jo = new JSONObject();
		jo.put("buildings", areaService.getBuildingBySchool_id(school_id));
		return CommonUtil.constructResponse(1, "success", jo);
	}
	
	@RequestMapping("/getDormitory")
	@ResponseBody
	public JSONObject getDormitory(long building_id){
		JSONObject jo = new JSONObject();
		jo.put("dormitories", areaService.getDormitoryByBuilding_id(building_id));
		return CommonUtil.constructResponse(1, "success", jo);
	}
}
