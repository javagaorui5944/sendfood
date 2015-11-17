package com.fenghuo.domain;

public class Default_order {

	 private long default_order_id;
	 private long school_id;
	 private String default_order_name;//'套餐名',
	 private int default_order_status;// '套餐状态',
	 
	public long getDefault_order_id() {
		return default_order_id;
	}
	public void setDefault_order_id(long default_order_id) {
		this.default_order_id = default_order_id;
	}
	public long getSchool_id() {
		return school_id;
	}
	public void setSchool_id(long school_id) {
		this.school_id = school_id;
	}
	public String getDefault_order_name() {
		return default_order_name;
	}
	public void setDefault_order_name(String default_order_name) {
		this.default_order_name = default_order_name;
	}
	public int getDefault_order_status() {
		return default_order_status;
	}
	public void setDefault_order_status(int default_order_status) {
		this.default_order_status = default_order_status;
	}
	
}
