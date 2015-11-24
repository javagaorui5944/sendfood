package com.fenghuo.domain;

public class region_staff {
	private long staff_id;
	private long region_id;
	private String region_start_time;
	private int region_staff_status;
	public long getStaff_id() {
		return staff_id;
	}
	public void setStaff_id(long staff_id) {
		this.staff_id = staff_id;
	}
	public long getRegion_id() {
		return region_id;
	}
	public void setRegion_id(long region_id) {
		this.region_id = region_id;
	}
	public String getRegion_start_time() {
		return region_start_time;
	}
	public void setRegion_start_time(String region_start_time) {
		this.region_start_time = region_start_time;
	}
	public int getRegion_staff_status() {
		return region_staff_status;
	}
	public void setRegion_staff_status(int region_staff_status) {
		this.region_staff_status = region_staff_status;
	}
}
