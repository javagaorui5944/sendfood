package com.fenghuo.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.fenghuo.service.OrderService;
import com.fenghuo.util.MsgPush;

public class DistributionJobRun implements Job{

	private static OrderService orderService;
	private static LoadTask loadTask;
	
	public static void setOrderService(OrderService orderService) {
		DistributionJobRun.orderService = orderService;
	}

	public static void setLoadTask(LoadTask loadTask) {
		DistributionJobRun.loadTask = loadTask;
	}

	@Override
	public void execute(JobExecutionContext context){
		// TODO Auto-generated method stub
		DistributionJob distributionJob = (DistributionJob)context.getMergedJobDataMap().get("distributionJob");
		long orderId = distributionJob.getJobId();
		Integer pushCount = orderService.getPushCount(orderId);
		if(pushCount == null || pushCount<2){
			orderService.setPushCount(orderId, pushCount+1);  //如果推送没有三次就加1
		}else{
			loadTask.delTask(orderId+"",distributionJob.getJOB_GROUP()); //否则删除该任务
		}
//		System.out.println(distributionJob);
		new MsgPush().sendMsg(distributionJob.getStaffTel(), distributionJob.getJobId(), distributionJob.getAddress());
	}
}
