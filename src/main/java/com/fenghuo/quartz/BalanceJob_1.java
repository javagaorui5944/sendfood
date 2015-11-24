package com.fenghuo.quartz;

import java.io.Serializable;

public class BalanceJob_1 implements Serializable{

	/**
	 * 配送任务的数据
	 * oldJobId 结算订单编号
	 * newJobId 自动下单的订单，为null时表示没有下单
	 * address 寝室地址
	 */
	private static final long serialVersionUID = 1L;
	private final String JOB_GROUP = "BalanceJob_1";
//	private final String CRONEXPRESSION = "0 1/1 * * * ?";
//	private final String CRONEXPRESSION = "5/5 * * * * ?";
	private final String CRONEXPRESSION = "0 0 8/8 * * ?";
	private long oldJobId;
	private long newJobId;
	private String staffTel;
	private String address;
	
	
	public BalanceJob_1(long oldJobId, long newJobId, String staffTel,
			String address) {
		super();
		this.oldJobId = oldJobId;
		this.newJobId = newJobId;
		this.staffTel = staffTel;
		this.address = address;
	}

	public String getStaffTel() {
		return staffTel;
	}

	public void setStaffTel(String staffTel) {
		this.staffTel = staffTel;
	}

	public long getOldJobId() {
		return oldJobId;
	}
	public void setOldJobId(long oldJobId) {
		this.oldJobId = oldJobId;
	}
	public long getNewJobId() {
		return newJobId;
	}
	public void setNewJobId(long newJobId) {
		this.newJobId = newJobId;
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
	
	public String getJOB_GROUP() {
		return JOB_GROUP;
	}
	@Override
	public String toString() {
		return "BalanceJob_1 [oldJobId=" + oldJobId + ", newJobId=" + newJobId
				+ ", address=" + address + "]";
	}
	
}
