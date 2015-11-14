package com.fenghuo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fenghuo.domain.Dormitory_cus;
import com.fenghuo.domain.organization;
import com.fenghuo.domain.staff;
import com.fenghuo.service.organizationService;
import com.fenghuo.util.CommonUtil;

@Controller
@RequestMapping("/organizationManage")
public class organizationController {
	@Autowired
	private organizationService organizationService;
	
	/**
	 * 管理组织架构页面
	 * 
	 * */
	@RequestMapping(value="/addOrg",method=RequestMethod.GET)
	public String addOrg(HttpSession request,Model model,@RequestParam(required=false)Long id,
	@RequestParam(required=false)Integer type,@RequestParam(required=false)Long pid,@RequestParam(required=false)Integer ptype){
		staff sta =  (staff)request.getAttribute("staff");
		List<organization> or=new ArrayList<organization>();
		List<organization> por=new ArrayList<organization>();
		if(type!=null){
			if(sta.getStaff_rank()>=type){
				if(id!=null){
					or=organizationService.listOrganizationbyId(id,type);
					System.out.println(type);
					if(type==40){
						por=organizationService.getCountry();
						model.addAttribute("por",por);	
					}
					if(type<40){
						por=organizationService.listOrganizationbyId(or.get(0).getPid(),type+10);
						por.get(0).setType(type+10);
						model.addAttribute("por",por);
					}
					or.get(0).setType(type);
					model.addAttribute("or",or);		
				}
			}
		}
		if(ptype!=null){
			if(sta.getStaff_rank()>=ptype){
				if(pid!=null){
					if(ptype==50){
						por=organizationService.getCountry();
						model.addAttribute("por",por);
					}else{
						por=organizationService.listOrganizationbyId(pid, ptype);
						por.get(0).setType(ptype);
						System.out.println(por.get(0).getId());
						model.addAttribute("por",por);
					}
				}
			}
		}
		return "orgStructure/addOrg";
	} 
	/**
	 * 查看组织架构页面
	 * 
	 * */
	@RequestMapping(value="/ViewOrg",method=RequestMethod.GET)
	public String ViewOrg(Model model){
		return "orgStructure/viewOrg";
	} 
	
	/**
	 * 列出组织架构
	 * 
	 * */
	
	@RequestMapping(value="/listOrganization",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject listOrganization(HttpSession request){
		staff sta =  (staff)request.getAttribute("staff");
		long staff_id=sta.getStaff_id();
		int type=sta.getStaff_rank();
		List<organization>organization=organizationService.listOrganization(type,staff_id);
		return CommonUtil.constructHtmlResponse(1, "success", organization);
	} 
	
	
	/**
	 * 列出所管理的寝室
	 * 
	 * */
	
	@RequestMapping(value="/listdormitory",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject listdormitory(HttpSession request){
		staff sta =  (staff)request.getAttribute("staff");
		long staff_id=sta.getStaff_id();
		int type=sta.getStaff_rank();
		List<Dormitory_cus> organization=organizationService.listdormitory(staff_id);
		return CommonUtil.constructHtmlResponse(1, "success", organization);
	} 
	
	/**
	 * 新增与修改组织架构
	 * id:修改时当前组织架构id
	 * pid:上层组织id
	 * type:上层组织级别
	 * Organization_name:组织名称
	 * code:组织状态码
	 * staff_id[] 负责人id
	 * date修改时日期
	 * */
	@RequestMapping(value="/addOrganization",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject addOrganization(Long id,long pid,int type,String Organization_name,@RequestParam(required=false)String code,String staff_id,@RequestParam(required=false)String date){
		int msgFlag=0;
		String sta[]=staff_id.split(",");
		long[] sta_id=new long[sta.length];
		for(int i=0;i<sta.length;i++){
			sta_id[i]=Long.valueOf(sta[i]);
		}
		if(id==-1){
			msgFlag=organizationService.checkOrganization(Organization_name,type-10,pid);
			if(msgFlag==1){
				return CommonUtil.constructHtmlResponse(0, "组织已存在", msgFlag);
			}
		}
		msgFlag=organizationService.addOrganization(id,pid,type,Organization_name,code,sta_id,date);
		String msg="";
		if(msgFlag==1)
			msg="success";
		else
			msg="false";
		return CommonUtil.constructHtmlResponse(msgFlag, msg, null);
	}

	/**
	 * 删除组织架构
	 * id:当前组织id
	 * type:当前组织级别
	 * */
	@RequestMapping(value="/deleteOrganization",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteOrganization(long id){
		int msgFlag=organizationService.deleteOrganization(id);
		String msg="";
		if(msgFlag==1)
			msg="success";
		else
			msg="false";
		return CommonUtil.constructHtmlResponse(msgFlag, msg, null);
	} 
	
	/**
	 * 列出手下员工
	 * 
	 * */
	@RequestMapping(value="/listAllstaff",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject ListStaff(HttpSession request){
		staff sta =  (staff)request.getAttribute("staff");
		long staff_id=sta.getStaff_id();
		int type=sta.getStaff_rank();
		JSONObject staff=organizationService.listAllstaff(staff_id,type);
		return CommonUtil.constructHtmlResponse(1, "success", staff);
	}
	
}