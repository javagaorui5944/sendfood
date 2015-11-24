package com.fenghuo.domain;


public class school {
	private long school_id;
	private long region_id;
	private String school_name;
	private String school_code;
	private int school_status;
	private long defaultorder_id;
	
	private Long storage_id = Long.valueOf(0);

	public Long getStorage_id() {
		return storage_id;
	}
	public void setStorage_id(Long storage_id) {
		this.storage_id = storage_id;
	}
	public long getDefaultorder_id() {
		return defaultorder_id;
	}
	public void setDefaultorder_id(long defaultorder_id) {
		this.defaultorder_id = defaultorder_id;
	}
	public long getSchool_id() {
		return school_id;
	}
	public void setSchool_id(long school_id) {
		this.school_id = school_id;
	}
	public long getRegion_id() {
		return region_id;
	}
	public void setRegion_id(long region_id) {
		this.region_id = region_id;
	}
	public String getSchool_name() {
		return school_name;
	}
	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}
	public String getSchool_code() {
		return school_code;
	}
	public void setSchool_code(String school_code) {
		this.school_code = school_code;
	}
	public int getSchool_status() {
		return school_status;
	}
	public void setSchool_status(int school_status) {
		this.school_status = school_status;
	}
	
}
