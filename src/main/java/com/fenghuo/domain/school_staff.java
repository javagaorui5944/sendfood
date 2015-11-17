package com.fenghuo.domain;

public class school_staff {
	private long staff_id;
	private long school_id;
	private String school_start_time;
	private int school_staff_status;
	public long getStaff_id() {
		return staff_id;
	}
	public void setStaff_id(long staff_id) {
		this.staff_id = staff_id;
	}
	public long getSchool_id() {
		return school_id;
	}
	public void setSchool_id(long school_id) {
		this.school_id = school_id;
	}
	public String getSchool_start_time() {
		return school_start_time;
	}
	public void setSchool_start_time(String school_start_time) {
		this.school_start_time = school_start_time;
	}
	public int getSchool_staff_status() {
		return school_staff_status;
	}
	public void setSchool_staff_status(int school_staff_status) {
		this.school_staff_status = school_staff_status;
	}
}
