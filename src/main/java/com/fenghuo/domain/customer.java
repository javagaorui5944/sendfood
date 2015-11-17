package com.fenghuo.domain;

import java.io.Serializable;

public class customer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long customer_id;
	private String customer_name;
	private String customer_tel;// '电话',
	private float customer_money; // '余额',
	private String customer_email;// '邮箱',
	private long school_id;
	private long building_id;
	private long dormitory_id;
	private int customer_status;
	
	private String dormitory_name; //寝室名称
	private String building_name; //漏洞名称
	private String school_name; //学校名称
	
	public int getCustomer_status() {
		return customer_status;
	}

	public void setCustomer_status(int customer_status) {
		this.customer_status = customer_status;
	}

	public String getDormitory_name() {
		return dormitory_name;
	}

	public void setDormitory_name(String dormitory_name) {
		this.dormitory_name = dormitory_name;
	}

	public String getBuilding_name() {
		return building_name;
	}

	public void setBuilding_name(String building_name) {
		this.building_name = building_name;
	}

	public String getSchool_name() {
		return school_name;
	}

	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getCustomer_tel() {
		return customer_tel;
	}

	public void setCustomer_tel(String customer_tel) {
		this.customer_tel = customer_tel;
	}

	public float getCustomer_money() {
		return customer_money;
	}

	public void setCustomer_money(float customer_money) {
		this.customer_money = customer_money;
	}

	public String getCustomer_email() {
		return customer_email;
	}

	public void setCustomer_email(String customer_email) {
		this.customer_email = customer_email;
	}

	public long getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(long customer_id) {
		this.customer_id = customer_id;
	}

	public long getSchool_id() {
		return school_id;
	}

	public void setSchool_id(long school_id) {
		this.school_id = school_id;
	}

	public long getBuilding_id() {
		return building_id;
	}

	public void setBuilding_id(long building_id) {
		this.building_id = building_id;
	}

	public long getDormitory_id() {
		return dormitory_id;
	}

	public void setDormitory_id(long dormitory_id) {
		this.dormitory_id = dormitory_id;
	}
}
