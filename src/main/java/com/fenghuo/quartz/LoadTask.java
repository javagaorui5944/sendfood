package com.fenghuo.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class LoadTask {

	@Autowired
	private SchedulerFactoryBean quartzScheduler;
	
	/**
	 * 添加一个配送推送
	 * 立即执行，然后每个8个小时执行一次，一共执行3次后取消该任务
	 */
	public void addDistributionTask(DistributionJob distributionJob) throws SchedulerException{
		Scheduler scheduler = quartzScheduler.getScheduler();
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(TriggerKey.triggerKey(distributionJob.getJobId()+"", distributionJob.getJOB_GROUP()));
		if(trigger != null){
			JobKey jobKey = JobKey.jobKey(distributionJob.getJobId()+"", distributionJob.getJOB_GROUP());
			scheduler.deleteJob(jobKey);
		}
		JobDetail jobDetail = JobBuilder.newJob(DistributionJobRun.class)
				.withIdentity(distributionJob.getJobId()+"", distributionJob.getJOB_GROUP()).build();
		jobDetail.getJobDataMap().put("distributionJob", distributionJob);
		//表达式调度构建器
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(distributionJob.getCRONEXPRESSION());
		//按新的cronExpression表达式构建一个新的trigger
		trigger = TriggerBuilder.newTrigger().withIdentity(distributionJob.getJobId()+"", distributionJob.getJOB_GROUP())
				.withSchedule(scheduleBuilder).build();
		scheduler.scheduleJob(jobDetail, trigger);
	}
	
	/**
	 * 添加一个结算推送
	 * 7天后执行，自动下单，结算和配送推送一起发送
	 * 取消该任务，另外新建一个重复推送的任务
	 */
	public void addBalanceTask(BalanceJob balanceJob) throws SchedulerException{
		Scheduler scheduler = quartzScheduler.getScheduler();
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(TriggerKey.triggerKey(balanceJob.getJobId()+"", balanceJob.getJOB_GROUP()));
		if(trigger != null){
			JobKey jobKey = JobKey.jobKey(balanceJob.getJobId()+"", balanceJob.getJOB_GROUP());
			scheduler.deleteJob(jobKey);
		}
		JobDetail jobDetail = JobBuilder.newJob(BalanceJobRun.class)
				.withIdentity(balanceJob.getJobId()+"", balanceJob.getJOB_GROUP()).build();
		jobDetail.getJobDataMap().put("balanceJob", balanceJob);
		//表达式调度构建器
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(balanceJob.getCRONEXPRESSION());
		//按新的cronExpression表达式构建一个新的trigger
		trigger = TriggerBuilder.newTrigger().withIdentity(balanceJob.getJobId()+"", balanceJob.getJOB_GROUP())
				.withSchedule(scheduleBuilder).build();
		scheduler.scheduleJob(jobDetail, trigger);
	}
	
	/**
	 * 添加一个重复推送的结算任务
	 * 7天后执行，自动下单，结算和配送推送一起发送
	 * 取消该任务，另外新建一个重复推送的任务
	 */
	public void addBalanceTask_1(BalanceJob_1 balanceJob) throws SchedulerException{
		Scheduler scheduler = quartzScheduler.getScheduler();
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(TriggerKey.triggerKey(balanceJob.getOldJobId()+"", balanceJob.getJOB_GROUP()));
		if(trigger != null){
			JobKey jobKey = JobKey.jobKey(balanceJob.getOldJobId()+"", balanceJob.getJOB_GROUP());
			scheduler.deleteJob(jobKey);
		}
//		TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobId(), scheduleJob.getJOB_GROUP());
//		CronTrigger trigger = (CronTrigger)scheduler.getTrigger(triggerKey);
		JobDetail jobDetail = JobBuilder.newJob(BalanceJobRun_1.class)
				.withIdentity(balanceJob.getNewJobId()+"", balanceJob.getJOB_GROUP()).build();
		jobDetail.getJobDataMap().put("balanceJob", balanceJob);
		//表达式调度构建器
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(balanceJob.getCRONEXPRESSION());
		//按新的cronExpression表达式构建一个新的trigger
		trigger = TriggerBuilder.newTrigger().withIdentity(balanceJob.getNewJobId()+"", balanceJob.getJOB_GROUP())
				.withSchedule(scheduleBuilder).build();
		scheduler.scheduleJob(jobDetail, trigger);
	}
	
	public void delTask(String orderId,String job_group){
		Scheduler scheduler = quartzScheduler.getScheduler();
		try {
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(TriggerKey.triggerKey(orderId, job_group));
			if(trigger != null){
				JobKey jobKey = JobKey.jobKey(orderId, job_group);
				scheduler.deleteJob(jobKey);
			}
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
