package com.fenghuo.domain;

public class notice {
	private long school_id;
	private long region_id;
	private String school_name;
	private String school_code;
	private int school_status;
	private String notice_note;
	private int notice_id;
	
	public int getNotice_id() {
		return notice_id;
	}
	public void setNotice_id(int notice_id) {
		this.notice_id = notice_id;
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
	public String getNotice_note() {
		return notice_note;
	}
	public void setNotice_note(String notice_note) {
		this.notice_note = notice_note;
	}
	
}
