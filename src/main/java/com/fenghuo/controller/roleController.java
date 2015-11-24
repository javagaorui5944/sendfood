package com.fenghuo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fenghuo.domain.Role;
import com.fenghuo.service.roleService;
import com.fenghuo.util.CommonUtil;

@Controller
@RequestMapping("/roleManage")
public class roleController {
	@Autowired
	private roleService roleService;
	
	/*
	 * 删除用户角色
	 * @justin
	 */
	@RequestMapping(value="/deleteRoleByid",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject deleteRoleByid(@RequestParam long id){
		int flag=roleService.deleteRole(id);
		if(flag == 1){
			return CommonUtil.constructHtmlResponse(1, "success", null);
		}
		return CommonUtil.constructHtmlResponse(0, "failed", null);
		
	}
	
	/*
	 * 更新某个角色功能的名字和备注功能
	 * justin
	 */
	@RequestMapping(value="/updateRole",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject updateRole(long role_id,String role_name,String role_note){
		System.out.println("role_name///"+role_name);
		int msgFlag = 0;
		msgFlag = roleService.updateRole(role_name,role_note,role_id);
		String msg = "";
		if(msgFlag == 1){
			msg="success";
		}else{
			msg="false";
		}
		return CommonUtil.constructHtmlResponse(msgFlag, msg, null);
	}
	
	/**
	 * 添加修改员工功能
	 * 添加员工id == -1
	 * */
	@RequestMapping(value="/addOrReviseRole",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject addOrReviseRole(long role_id,String role_name,String role_note,int role_status){
		int msgFlag = 0;
		msgFlag = roleService.addOrReviseRole(role_id,role_name,role_note,role_status);
		String msg = "";
		if(msgFlag == 1){
			msg="success";
		}else{
			msg="false";
		}
		return CommonUtil.constructHtmlResponse(msgFlag, msg, null);
	}
	/**
	 * 列出用户角色
	 *  @param 用户id
	 * */
	@RequestMapping(value="/getUserRolesById",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject listRolebyUserid(String staff_id){
		List<Role> roles = roleService.getRolesByUserId(staff_id);
		return CommonUtil.constructHtmlResponse(1, "success", roles);
	}

	/**
	 * 取得所有角色 + 分页
	 * justin
	 * */
	@RequestMapping(value="/getRoleList",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject getStaffList(Model model,@RequestParam(value="page",required=false)long page){
		JSONObject jo = new JSONObject();
		List<Role> roles = new ArrayList<Role>();
		long total = roleService.getCountRoleList();

		if(total > 0){
			roles=roleService.getAllRoles(page,10);
		}
		jo.put("total", total);
		jo.put("roles", roles);
		return CommonUtil.constructResponse(1, "success", jo);
	}
	
	
	/**
	 * 取得所有角色
	 * */
	@RequestMapping(value="/getAllRoles",method=RequestMethod.GET)
	public String getAllRoles(){
		return "role/manage";
	}
		/**
  	 * 取得所有角色
  	 * */
  	@RequestMapping(value="/getAllRolesNoPage",method=RequestMethod.POST)
  	@ResponseBody
  	public JSONObject getAllRolesNoPage(){
  	List<Role> 	roles=roleService.getAllRoles1();
  		return CommonUtil.constructResponse(1, "success", roles);
  	}
	/**
	 * 管理用户角色
	 * */
	@RequestMapping(value="/manageStaffRole",method=RequestMethod.GET)
	public String manageStaffRole(){
		return "role/manageStaffRole";
	}
	
	/**
	 * 保存用户角色
	 * */
	@RequestMapping(value="/saveUserRoles",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject saveUserRoles(String userId,String roleId){
		String[] roleIds = roleId.split(",");
		int length = roleIds.length;
		//先把所有的删除
		for(int i = 0; i < roleIds.length; i++){
		
		   int flag = roleService.deleteStaffRole(userId);
		   
		   length -= flag;
		}
//		if(length != 0 ){
//			return CommonUtil.constructResponse(0, "保存失败", null);
//		}
	    length = roleIds.length;
		//再重新加入
		for(int k = 0; k < roleIds.length;k++){
			System.out.println("roleIds[k]"+roleIds[k]);
			int flag = roleService.saveUserRole(userId,roleIds[k]);
		
			length -= flag;
		}
		if(length == 0){
			return CommonUtil.constructResponse(1, "保存成功", null);
		}else{
			return CommonUtil.constructResponse(0, "未完全保存成功", null);
		}
	}
	
	
}
