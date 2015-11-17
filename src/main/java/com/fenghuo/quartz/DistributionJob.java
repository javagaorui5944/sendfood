package com.fenghuo.quartz;

import java.io.Serializable;

public class DistributionJob implements Serializable{

	/**
	 * 配送推送任务的数据
	 * jobId = orderId
	 * staffTel 配送人电话，openfire登录账号
	 * address 配送地址
	 */
	private static final long serialVersionUID = 1L;
	private final String JOB_GROUP = "DistributionJob";
//	private final String CRONEXPRESSION = "0/5 * * * * ?"; 
//	private final String CRONEXPRESSION = "0 0/1 * * * ?";
	private final String CRONEXPRESSION = "0 0 0/8 * * ?";
	private long jobId;
	private String staffTel;
	private String address;
	
	public DistributionJob(long jobId, String staffTel, String address) {
		super();
		this.jobId = jobId;
		this.staffTel = staffTel;
		this.address = address;
	}

	public String getJOB_GROUP() {
		return JOB_GROUP;
	}

	public String getStaffTel() {
		return staffTel;
	}

	public void setStaffTel(String staffTel) {
		this.staffTel = staffTel;
	}

	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getCRONEXPRESSION() {
		return CRONEXPRESSION;
	}

	public long getJobId() {
		return jobId;
	}

	public void setJobId(long jobId) {
		this.jobId = jobId;
	}

	@Override
	public String toString() {
		return "DistributionJob [jobId=" + jobId + ", staffTel=" + staffTel
				+ ", address=" + address + "]";
	}
	
}
