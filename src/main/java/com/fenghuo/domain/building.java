package com.fenghuo.domain;


public class building {
	private long building_id;
	private long school_id;
	private String building_name;
	private String building_code;
	private int building_status;
	public long getBuilding_id() {
		return building_id;
	}
	public void setBuilding_id(long building_id) {
		this.building_id = building_id;
	}
	public long getSchool_id() {
		return school_id;
	}
	public void setSchool_id(long school_id) {
		this.school_id = school_id;
	}
	public String getBuilding_name() {
		return building_name;
	}
	public void setBuilding_name(String building_name) {
		this.building_name = building_name;
	}
	public String getBuilding_code() {
		return building_code;
	}
	public void setBuilding_code(String building_code) {
		this.building_code = building_code;
	}
	public int getBuilding_status() {
		return building_status;
	}
	public void setBuilding_status(int building_status) {
		this.building_status = building_status;
	}
}
