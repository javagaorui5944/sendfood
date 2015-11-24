package com.fenghuo.domain;

import java.util.List;

public class Dormitory_cus {
	private long dormitory_id;
	private long building_id;
	private long staff_id;
	private String dormitory_name;
	private String dormitory_code;
	private int dormitory_status;
	private String date;
	private String dormitory_password;
	private List<customer> cus;
	public long getDormitory_id() {
		return dormitory_id;
	}
	public void setDormitory_id(long dormitory_id) {
		this.dormitory_id = dormitory_id;
	}
	public long getBuilding_id() {
		return building_id;
	}
	public void setBuilding_id(long building_id) {
		this.building_id = building_id;
	}
	public long getStaff_id() {
		return staff_id;
	}
	public void setStaff_id(long staff_id) {
		this.staff_id = staff_id;
	}
	public String getDormitory_name() {
		return dormitory_name;
	}
	public void setDormitory_name(String dormitory_name) {
		this.dormitory_name = dormitory_name;
	}
	public String getDormitory_code() {
		return dormitory_code;
	}
	public void setDormitory_code(String dormitory_code) {
		this.dormitory_code = dormitory_code;
	}
	public int getDormitory_status() {
		return dormitory_status;
	}
	public void setDormitory_status(int dormitory_status) {
		this.dormitory_status = dormitory_status;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDormitory_password() {
		return dormitory_password;
	}
	public void setDormitory_password(String dormitory_password) {
		this.dormitory_password = dormitory_password;
	}
	public List<customer> getCus() {
		return cus;
	}
	public void setCus(List<customer> cus) {
		this.cus = cus;
	}
	
}
