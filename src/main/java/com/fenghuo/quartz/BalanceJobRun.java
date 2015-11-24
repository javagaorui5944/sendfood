package com.fenghuo.quartz;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;

import com.fenghuo.domain.Order;
import com.fenghuo.domain.building;
import com.fenghuo.domain.dormitory;
import com.fenghuo.domain.school;
import com.fenghuo.domain.staff;
import com.fenghuo.service.AreaService;
import com.fenghuo.service.Default_OrderService;
import com.fenghuo.service.OrderService;
import com.fenghuo.service.UserService;
import com.fenghuo.util.MsgPush;

public class BalanceJobRun implements Job{

	private static OrderService orderService;
	private static LoadTask loadTask;
	private static Default_OrderService default_OrderService;
	private static UserService userService;
	private static AreaService areaService;
	

	public static void setOrderService(OrderService orderService) {
		BalanceJobRun.orderService = orderService;
	}

	public static void setLoadTask(LoadTask loadTask) {
		BalanceJobRun.loadTask = loadTask;
	}

	public static void setDefault_OrderService(
			Default_OrderService default_OrderService) {
		BalanceJobRun.default_OrderService = default_OrderService;
	}

	public static void setUserService(UserService userService) {
		BalanceJobRun.userService = userService;
	}

	public static void setAreaService(AreaService areaService) {
		BalanceJobRun.areaService = areaService;
	}

	@Override
	public void execute(JobExecutionContext context){
		// TODO Auto-generated method stub
		BalanceJob balanceJob = (BalanceJob)context.getMergedJobDataMap().get("balanceJob");
		long orderId = balanceJob.getJobId();
		staff staff = userService.getStaffByOrderId(orderId);
		dormitory dormitory = orderService.getDormitoryId(orderId);
		building building = areaService.getBuildingByDormitory(dormitory.getDormitory_id());
		school school = areaService.getSchoolByBuilding(building.getBuilding_id());
		BalanceJob_1 balanceJob_1 = new BalanceJob_1(orderId,orderId,staff.getStaff_tel(),school.getSchool_name()+"-"+building.getBuilding_name()
				+"-"+dormitory.getDormitory_name());
		Integer count = orderService.getNotCompleteOrderCount(dormitory.getDormitory_id());
		if(count < 2){
			Long ls=default_OrderService.getDefaultOrderBydormitoryId(dormitory.getDormitory_id());
			if(ls != null && ls != 0){
				Order order = new Order();
				//orderItemService.setOrderPrice(ls.get(0).getDefault_order_id(),order);  //添加订单的成本和售价
				java.util.Date dt=new Date();
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置显示格式
				String nowTime="";
				nowTime= df.format(dt);
				order.setOrder_create_time(nowTime);
				order.setDormitory_id(dormitory.getDormitory_id());
				order.setDefault_order_id(ls);
				order.setOrder_status(10);
				order.setOrder_query_id(school.getSchool_code()+"-"+building.getBuilding_code()+"-"+dormitory.getDormitory_code()); //设置订单code
				order.setStaff_id(staff.getStaff_id());
				Long newOrderId = orderService.addOrder(order);
				balanceJob_1.setNewJobId(newOrderId);
			}
		}
		new MsgPush().balanceMsg(balanceJob_1.getStaffTel(), balanceJob_1.getOldJobId(), balanceJob_1.getNewJobId(),balanceJob_1.getAddress());//消息推送
		loadTask.delTask(orderId+"",balanceJob.getJOB_GROUP());//删除原任务
		try {
			loadTask.addBalanceTask_1(balanceJob_1); //设置新推送
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
