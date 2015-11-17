package com.fenghuo.domain;

public class Storage {

	/*
	 * 仓库
	 */
	private long storage_id; //仓库编号
	private long school_id; //学校编号
	private String storage_name; //仓库名称
	public long getStorage_id() {
		return storage_id;
	}
	public void setStorage_id(long storage_id) {
		this.storage_id = storage_id;
	}
	public long getSchool_id() {
		return school_id;
	}
	public void setSchool_id(long school_id) {
		this.school_id = school_id;
	}
	public String getStorage_name() {
		return storage_name;
	}
	public void setStorage_name(String storage_name) {
		this.storage_name = storage_name;
	}
	
	
}
