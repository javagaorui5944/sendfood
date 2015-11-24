package com.fenghuo.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fenghuo.domain.Limits;
import com.fenghuo.domain.Role;
import com.fenghuo.service.limitsService;
import com.fenghuo.service.roleService;
import com.fenghuo.util.CommonUtil;
import com.fenghuo.util.MybatisUtil;

@Controller
@RequestMapping("/limitsManage")
public class limitController {
	
	@Autowired
	private limitsService limitsService;
	
	@Autowired
	private roleService roleService;
	
	/**
	 *   通过mybatis XML配置   删除权限及相关关系
	 *  @justin
	 * 
	@RequestMapping(value="/deleteLimitsAllOfXml",method=RequestMethod.GET)
	@ResponseBody*/
	public String deleteLimitsAllOfXml(@RequestParam int limits_id){
		SqlSession session = null;
		int n = 0;
		try {
			session = MybatisUtil.createSession();
			System.out.println(Limits.class.getName());
			n = session.delete(Limits.class.getName()+".delete", limits_id);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		}finally{
			session.close();
		}
		if(n==1)
			return "true";
		return "false";
	}
	
	
	/*
	 * 增加父亲栏目。+ 增加权限
	 * justin
	 */
	@RequestMapping(value="/addLimitsAndParent",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject addOrReviseStaff(int limits_pid,String limits_pname,int limits_id,String limits_url,String limits_note,String limits_name){
		int msgFlag1 = 0;
		int msgFlag2 = 0;
		int flag;
		msgFlag1 = limitsService.addLimitsParent(limits_pid,limits_pname,limits_id);
		msgFlag2 = limitsService.addLimits(limits_pid,limits_pname,limits_url,limits_note,limits_name);
		String msg = "";
		if(msgFlag1 == 1 && msgFlag2==1){
			flag=1;
			msg="success";
		}else{
			flag=2;
			msg="false";
		}
		return CommonUtil.constructHtmlResponse(flag, msg, null);
	}
	
	/**
	 * 删除权限及相关关系
	 *  @justin
	 * */
	@RequestMapping(value="/deleteLimitsAll",method=RequestMethod.GET)
	@ResponseBody
	public String deleteLimitsAll(@RequestParam int limits_id){
		int n=limitsService.deleteLimitsAll(limits_id);
		if(n==1)
			return "true";
		return "false";
	}
	
	/**
	 * 取得所有权限 + 分页
	 * justin
	 * */
	@RequestMapping(value="/getlimitsList",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject getStaffList(Model model,@RequestParam(value="page",required=false)long page){
		JSONObject jo = new JSONObject();
		List<Limits> limits = new ArrayList<Limits>();
		long total = limitsService.getCountLimitsList();

		if(total > 0){
			limits=limitsService.getAllLimits(page,10);
		}
		jo.put("total", total);
		jo.put("roles", limits);
		return CommonUtil.constructResponse(1, "success", jo);
	}
	
	/*
	 * 管理权限，只修改部分（以下参数）
	 *@param  limits_name,limits_note,limits_status
	 *justin
	 * */
	@RequestMapping(value="/updateLimits",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject updateLimits(String limits_name,String limits_note,int limits_id){
		int msgFlag = 0;
		msgFlag = limitsService.updateLimits(limits_name, limits_note, limits_id);
		String msg = "";
		if(msgFlag == 1){
			msg="success";
		}else{
			msg="false";
		}
		return CommonUtil.constructHtmlResponse(msgFlag, msg, null);
	}
	
	
	/**
	 * 列出所有权限
	 *  @param
	 * */

	@RequestMapping(value="/getAllLimits",method=RequestMethod.GET)
	public String listLimits(Model model){
		List<Limits> Limits = limitsService.listAllLimits();
		model.addAttribute("limits", Limits);
		return "limits/manageLimits";
	}
	/**
	 * 列出所有父级页面
	 *  @param
	 * */
	@RequestMapping(value="/getAllLimitsParent",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject getAllLimitsParent(){
		List<Limits> Limits=limitsService.getAllLimitsParent();
		
		
		return CommonUtil.constructResponse(1, "success", Limits);
		
	}

	/**
	 * 列出角色权限
	 *  @param 角色id
	 * */
	@RequestMapping(value="/getRoleOfLimits",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject getRoleOfLimits(@RequestParam("roleId") String roleId){
		List<Limits> roleoflimit=limitsService.getRoleOfLimits(Integer.parseInt(roleId));
		return CommonUtil.constructResponse(1, "success", roleoflimit);
	}
	
	
	
	/**
	 * 添加权限
	 *  @param limits_name,limits_note,limits_status,limits_url,limits_pid
	 * 
	@RequestMapping(value="/addlimits",method=RequestMethod.GET)
	@ResponseBody
	public String addLimits(@RequestParam("text") String text){
		List<Limits> adddatement=JSON.parseArray(text,Limits.class);		
		int n=0;
		for(Limits t : adddatement){
			n=limitsService.addLimits(t);
		}
		System.out.println(n);
		if(n==1)
			return "true";
		return "false";
	}
	*/
	
	/**
	 * 修改权限
	 *  @param limits_name,limits_note,limits_status,limits_url,limits_pid
	 * */
	
	@RequestMapping(value="/updateLimitsByid",method=RequestMethod.GET)
	@ResponseBody
	public String updateLimitsByid(@RequestParam String text){
		List<Limits> adddatement=JSON.parseArray(text,Limits.class);
		int n=0;
		for(Limits t : adddatement){
			n=limitsService.updateLimitsByid(t);}
		if(n==1)
			return "true";
		return "false";
	}
	
	/**
	 * 删除权限
	 *  @param limits_id
	 * */
	@RequestMapping(value="/deleteLimitsByid",method=RequestMethod.GET)
	@ResponseBody
	public String deleteLimitsByid(@RequestParam int id){
		int n=limitsService.deleteLimitsByid(id);
		if(n==1)
			return "true";
		return "false";
	}
	
	
	@RequestMapping(value="/manageRoleLimits",method=RequestMethod.GET)
	public String manageRoleLimits(Model model){
		List<Limits> Limits = limitsService.listAllLimits();
		model.addAttribute("limits", Limits);
		List<Role> roleofuser=roleService.getAllRoles1();
		model.addAttribute("roles",roleofuser);
		return "limits/manageRoleLimit";
	}
	/**
	 * 保存角色权限
	 * */
	@RequestMapping(value="/saveRoleLimits",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject saveRoleLimits(String roleId,String limitId){
		String[] limitIds = limitId.split(",");
		int length = limitIds.length;
		//先把所有的删除
		for(int i = 0; i < limitIds.length; i++){
		   int flag = limitsService.deleteRoleLimits(roleId);
		   length -= flag;
		}
//		if(length != 0 ){
//			return CommonUtil.constructResponse(0, "保存失败", null);
//		}
	    length = limitIds.length;
		//再重新加入
		for(int k = 0; k < limitIds.length;k++){
			int flag = limitsService.saveRoleLimits(roleId,limitIds[k]);
			length -= flag;
		}
		if(length == 0){
			return CommonUtil.constructResponse(1, "保存成功", null);
		}else{
			return CommonUtil.constructResponse(0, "未完全保存成功", null);
		}
	}
	
}
