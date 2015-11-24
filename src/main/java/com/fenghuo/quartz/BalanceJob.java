package com.fenghuo.quartz;

import java.io.Serializable;

public class BalanceJob implements Serializable{

	/**
	 * 配送任务的数据
	 * jobId = orderId
	 */
	private static final long serialVersionUID = 1L;
	private final String JOB_GROUP = "BalanceJob";
//	private final String CRONEXPRESSION = "5/5 * * * * ?";
//	private final String CRONEXPRESSION = "0 1/1 * * * ?";
	private final String CRONEXPRESSION = "0 0 0 7/1 * ?";
	private long jobId;
	
	public BalanceJob(long jobId){
		this.jobId = jobId;
	}
	
	public String getJOB_GROUP() {
		return JOB_GROUP;
	}


	public long getJobId() {
		return jobId;
	}


	public void setJobId(long jobId) {
		this.jobId = jobId;
	}


	public String getCRONEXPRESSION() {
		return CRONEXPRESSION;
	}
	
}
