package com.fenghuo.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fenghuo.domain.Limits;
import com.fenghuo.domain.customer;
import com.fenghuo.domain.staff;
import com.fenghuo.service.CustomerService;
import com.fenghuo.service.StaffService;
import com.fenghuo.service.UserService;
import com.fenghuo.service.authorityService;
import com.fenghuo.service.limitsService;
import com.fenghuo.util.CommonUtil;



/**
 * 处理用户端所有逻辑
 * 
 ***/


@Controller
@RequestMapping("/userManage")
public class userController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private limitsService limitsService;
	
   @Autowired 
   private authorityService authorityService;
   
   @Autowired
	private StaffService staffService;
   @Autowired
	private CustomerService customerServices;
	
	/**
	 * 员工登录功能
	 * */
	@RequestMapping(value="/staffLogin",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject  staffLogin(String telephone,String password,HttpSession httpSession){
		staff staff = userService.getStaffByStffTel(telephone);
		if(staff == null){
			return CommonUtil.constructResponse(0, "电话号码出错", "[]");
		}

		if(staff.getStaff_password().equals(password)){//登录成功

			staff.setStaff_password("");
			httpSession.setAttribute("staff",staff ); 
			System.out.println("httpSession.setAttribute");
			return CommonUtil.constructResponse(1, "success", staff);
		 }else{
			return CommonUtil.constructResponse(0, "密码错误", "[]");
	     }	
	}	
	
	
	
	
	/**
	 * 消费者登录功能
	 * */
	@RequestMapping(value="/customerLogin",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject  customerLogin(String telephone,String password,HttpSession httpSession){
		 customer cus = userService.getCustomerByTel(telephone);
		if(cus == null){
			return CommonUtil.constructResponse(0, "电话号码出错", "[]");
		}

		else{

			String pwd = userService.getPasswordByCustomerId(cus.getDormitory_id());
			if(pwd.equals(password)){//登录成功
				httpSession.setAttribute("customer",cus);
				return CommonUtil.constructResponse(1, "success", cus);
			}else{
				return CommonUtil.constructResponse(0, "密码错误", "[]");
			}
		}
		
	}	
	
	
	/**
	 * 
	 *  根据输入的用户名模糊查询用户
	 */
	@RequestMapping(value="/getStuffsBySearchName",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject  getStuffsBySearchName(String staff_name){
		List<staff> users = userService.getStuffsBySearchName(staff_name);
		return CommonUtil.constructResponse(1, "success", users);
	}	
	
	/**
	 * 取得用户对应所有的权限页面
	 **/
	@RequestMapping(value="/getStuffLimitPageByStuffId",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject  getStuffLimitPageByStuffId(HttpSession httpSession,HttpServletRequest request){
		staff s = (staff)httpSession.getAttribute("staff");
		if(s == null){
			return CommonUtil.constructResponse(0, "500服务器错误", null);
		}
		List<Limits> lms = limitsService.getStuffLimitPageByStuffId(s.getStaff_id());
		return CommonUtil.constructResponse(1, "success", lms);
	}	
	
	
	//管理消费者主页
	 @RequestMapping("/customerIndex")
	 public String foodIndex(HttpSession httpSession,Model model){
		 return "/customer/customer";
	 }
	/**
	 * 获取用户管理页面的消费者信息
	 * @param page 页码、为空时或小于等于0时为默认值1
	 * @param value 模糊查询内容、关键字(按名字模糊查询)
	 * 然后根据用户查看的具体仓库、分页、模糊查询查出具体内容
	 */	 
@RequestMapping(value="/getAllCustomer", method=RequestMethod.GET)
@ResponseBody
public JSONObject getAllDefaultFood(HttpSession request,@RequestParam(value="page",required=false) Long page,
	@RequestParam(value="value",required=false) String value){
	staff staff = (staff)request.getAttribute("staff");  //获取员工
	if(staff == null){
		return CommonUtil.constructResponse(0, "未登录", null);
	}
	JSONObject jo = new JSONObject();
	List<customer> customer = new ArrayList<customer>(); //添加员工所能管理的仓库集合
	if((Long)page == null || page <=0){
		page = Long.valueOf(1);
	}
	if(staff.getStaff_rank() == 10){ //宿舍负责人
		long count = staffService.getCountDormitory_Staff(staff.getStaff_id(), value);
		if(count > 0){
			customer = staffService.getDormitory_Staff(staff.getStaff_id(), page, 10, value);
		}
		jo.put("total", count);
		jo.put("customers", customer);
	}else if(staff.getStaff_rank() == 20){
		long count = staffService.getCountBuilding_Staff(staff.getStaff_id(), value);
		if(count > 0){
			customer = staffService.getBuilding_Staff(staff.getStaff_id(), page, 10, value);
		}
		jo.put("total", count);
		jo.put("customers", customer);
	}else if(staff.getStaff_rank() == 30){
		long count = staffService.getCountSchool_Staff(staff.getStaff_id(), value);
		if(count > 0){
			customer = staffService.getSchool_Staff(staff.getStaff_id(), page, 10, value);
		}
		jo.put("total", count);
		jo.put("customers", customer);
	}else if(staff.getStaff_rank() == 40){
		long count = staffService.getCountRegion_Staff(staff.getStaff_id(), value);
		if(count > 0){
			customer = staffService.getRegion_Staff(staff.getStaff_id(), page, 10, value);
		}
		jo.put("total", count);
		jo.put("customers", customer);
		
	}
	jo.put("nowPage", page);
	return CommonUtil.constructResponse(1, "success", jo);
}

/**
 * 添加消费者、默认将消费者状态状态设置为10--正常
 */
	@RequestMapping(value="/addCustomer", method=RequestMethod.GET)
	@ResponseBody
public JSONObject addFood(customer customer){
	if(customer.getCustomer_name() == null || "".equals(customer.getCustomer_name())){
		return CommonUtil.constructResponse(0, "用户姓名不能为空", null);
	}
	if(customer.getCustomer_tel() == null || "".equals(customer.getCustomer_tel())){
		return CommonUtil.constructResponse(0, "用户电话不能为空", null);
	}
	if((Long)customer.getSchool_id() == null){
		return CommonUtil.constructResponse(0, "学校不能为空", null);
	}
	if((Long)customer.getBuilding_id() == null){
		return CommonUtil.constructResponse(0, "楼栋不能为空", null);
	}
	if((Long)customer.getDormitory_id() == null){
		return CommonUtil.constructResponse(0, "寝室不能为空", null);
	}
	customer.setCustomer_status(10);
	customerServices.addCustomer(customer);
	return CommonUtil.constructResponse(1, "添加成功", null);
}
	
	
	/**
	 * 修改用户状态
	 * 10为正常状态、0为冻结状态
	 * 需要获取参数：customer_id--食品编号，customer_status--修改后的食品状态
	 */
	@RequestMapping(value="/changeCustomer", method=RequestMethod.POST)
	@ResponseBody
	public JSONObject changeCustomer(customer customer){
		if(customerServices.changeCustomer(customer.getCustomer_status(),customer.getCustomer_id()) == 1){
			return CommonUtil.constructResponse(1, "修改成功", null);
		}
		return CommonUtil.constructResponse(0, "修改失败，系统错误", null);
	}
	
	/**
	 * 修改用户
	 * 用户编号、用户姓名、用户电话、学校、楼栋、寝室
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/updateCustomer", method=RequestMethod.POST)
	@ResponseBody
	public JSONObject updateCustomer(HttpSession request,customer customer) throws UnsupportedEncodingException{
		if((Long)customer.getCustomer_id() == null){
			return CommonUtil.constructResponse(0, "用户编号获取失败为空", null);
		}
		if(customer.getCustomer_name() == null || "".equals(customer.getCustomer_name())){
			return CommonUtil.constructResponse(0, "用户姓名不能为空", null);
		}
		if(customer.getCustomer_tel() == null || "".equals(customer.getCustomer_tel())){
			return CommonUtil.constructResponse(0, "用户电话不能为空", null);
		}
		System.out.println(customer.getCustomer_name());
		customerServices.updateCustomer(customer);
		return CommonUtil.constructResponse(1, "修改成功", null);
	}
	
    /**
     * 网站首页
     * @param 用户id
     * 如果用户登录信息未失效，根据用户id跳转到用户订单页面
     * 如果初次登录或者死去效果
     * */	
	@RequestMapping("/")
	public String welcome(Model model,String userId){
		
		return "guest/orderList";
	}
	
	
	//用户列表首页跳转
	 @RequestMapping("/staffList")
	 public String staffIndex(HttpSession httpSession,Model model){
		 return "/stuff/stuffList";
	 }
	 
	/**
	 * 获取管理用户列表
	 *  @param
	 * */
	
	@RequestMapping(value="/getStaffList",method=RequestMethod.GET)
	@ResponseBody
	public JSONObject getStaffList(Model model,@RequestParam(value="page",required=false)long page){
		JSONObject jo = new JSONObject();
		List<staff> staffs = new ArrayList<staff>();
		long total = userService.getCountStaffList();
		if(total > 0){
			staffs=userService.getStaffList(page,10);
		}
		jo.put("total", total);
		jo.put("staffs", staffs);
		return CommonUtil.constructResponse(1, "success", jo);
	}
	
	/**
	 * 删除用户及关联
	 *  justin
	 *
	 * */
	
	@RequestMapping(value="/deleteUserAll",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteUserAll(@RequestParam int id){
		int flag = userService.deleteUserAll(id);
		if(flag == 1){
			return CommonUtil.constructHtmlResponse(1, "删除成功", null);
		}
		return CommonUtil.constructHtmlResponse(0, "删除失败", null);
	}
	
	/**
	 * 删除用户
	 *  @param
	 * */
	
	@RequestMapping(value="/deleteUser",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteUser(@RequestParam int id){
		int flag = userService.deleteUser(id);
		if(flag == 1){
			return CommonUtil.constructHtmlResponse(1, "删除成功", null);
		}
		return CommonUtil.constructHtmlResponse(0, "删除失败", null);
	}
	
	/**
	 * 根据用户id取得用户信息
	 *  @param
	 * */
	
	@RequestMapping(value="/getStaffById",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject getStaffById(Long staffId){
		staff s = userService.getStaffById(staffId);
		return CommonUtil.constructHtmlResponse(1, "success", s);
	}
	/**
	 * 添加修改员工功能
	 * 添加员工id == -1
	 * */
	@RequestMapping(value="/addOrReviseStaff",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject addOrReviseStaff(long id,String staff_name,String staff_tel,String staff_email,int staff_status){
		System.out.println("tttttttttttttttttttttttt"+staff_name+"ddddd"+ id+"long id");
		int msgFlag = 0;
		msgFlag = userService.addStaff(id,staff_name,staff_tel,staff_email,staff_status);
		String msg = "";
		if(msgFlag == 1){
			msg="success";
		}else{
			msg="false";
		}
		return CommonUtil.constructHtmlResponse(msgFlag, msg, null);
	}
	
    /**
     * 判断用户名是否重复
     * */
	@RequestMapping(value="/judgeUserRepeat",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject judgeUserRepeat(String tel,long staff_id){
		
		if(staff_id == -1){
			List<staff> s = userService.judgeUserRepeat(tel,staff_id);
			if(s.size()  == 0 ){
				return CommonUtil.constructHtmlResponse(1, "success", null);
			}else{
				return CommonUtil.constructHtmlResponse(0, "电话号码已存在", null);
			}
		}else{
			List<staff> s = userService.judgeUserRepeat(tel,staff_id);
			if(s.size() < 1 ){
				return CommonUtil.constructHtmlResponse(1, "success", null);
			}else{
				return CommonUtil.constructHtmlResponse(0, "电话号码已存在", null);
			}
		}
		
		
	}
	
}
