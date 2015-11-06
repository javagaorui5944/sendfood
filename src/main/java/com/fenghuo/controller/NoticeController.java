package com.fenghuo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fenghuo.domain.staff;
import com.fenghuo.service.NoticeService;
import com.fenghuo.util.CommonUtil;

@Controller
@RequestMapping("/noticeManage")
public class NoticeController {
	@Autowired
	private NoticeService noticeserver;
	
	@RequestMapping("/index")
	public String manageStandardOrder(HttpSession httpSession){
		return "/notice/noticeindex";
	}
	
	/**
	 * 当school_id 或者 notice_note不为空时表示要进行新增或者修改操作。
	 * 有notice_id就是修改。没有就是新增
	 * 当什么都不传时显示全部的学校信息。
	 * 当只传region_id时。返回对应区县的学校信息
	 * 当
	 * @param httpSession
	 * @param region_id
	 * @param notice_id
	 * @param school_id
	 * @param notice_note
	 * @return
	 */
	@RequestMapping(value="/getlist",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject getAllOrder(HttpSession httpSession,
			@RequestParam(required=false,value="region_id")Long region_id,
			@RequestParam(required=false,value="notice_id")Long notice_id ,
			@RequestParam(required=false,value="school_id")Long school_id,
			@RequestParam(required=false,value="notice_note")String notice_note){
		staff staff = (staff)httpSession.getAttribute("staff");  //获取员工
		if(staff == null){
			return CommonUtil.constructResponse(0, "员工不存在，或非法操作", null);
		}
		if(school_id==null||notice_note==null){
		if(region_id==null)
			return CommonUtil.constructResponse(0, "列出全部学校", noticeserver.listnotice());
		else
			return  CommonUtil.constructResponse(0, "根据区域列出学校", noticeserver.listnotice(region_id));
		}else
			return  CommonUtil.constructResponse(0, "操作成功", noticeserver.operation(notice_id, school_id, notice_note));

	}
}
