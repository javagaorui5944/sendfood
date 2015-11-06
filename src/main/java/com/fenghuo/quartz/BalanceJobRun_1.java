package com.fenghuo.quartz;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.fenghuo.service.OrderService;
import com.fenghuo.util.MsgPush;

public class BalanceJobRun_1 implements Job{

	private static OrderService orderService;
	private static LoadTask loadTask;
	
	public static void setOrderService(OrderService orderService) {
		BalanceJobRun_1.orderService = orderService;
	}

	public static void setLoadTask(LoadTask loadTask) {
		BalanceJobRun_1.loadTask = loadTask;
	}

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		BalanceJob_1 balanceJob = (BalanceJob_1)context.getMergedJobDataMap().get("balanceJob");
		long orderId = balanceJob.getNewJobId();
		Integer pushCount = orderService.getPushCount(orderId);
		if(pushCount == null || pushCount<1){
			orderService.setPushCount(orderId, pushCount+1);  //如果推送没有三次就加1
		}else{
			loadTask.delTask(orderId+"",balanceJob.getJOB_GROUP()); //否则删除该任务
		}
		new MsgPush().balanceMsg(balanceJob.getStaffTel(), balanceJob.getOldJobId(), balanceJob.getNewJobId(),balanceJob.getAddress());
	}
}
