package com.fenghuo.domain;


public class building_staff {
	private long building_id;
	private long staff_id;
	private String building_start_time;
	private int building_staff_status;
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
	public String getBuilding_start_time() {
		return building_start_time;
	}
	public void setBuilding_start_time(String building_start_time) {
		this.building_start_time = building_start_time;
	}
	public int getBuilding_staff_status() {
		return building_staff_status;
	}
	public void setBuilding_staff_status(int building_staff_status) {
		this.building_staff_status = building_staff_status;
	}
	
}
