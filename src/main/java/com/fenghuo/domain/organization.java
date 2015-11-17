package com.fenghuo.domain;

import java.util.ArrayList;
import java.util.List;
public class organization {
	private long id;
	private long pid;
	private String name;
	private String code;
	private int type;
	private String date;
	private List<organization> children = new ArrayList<organization>();
	private List<staffs> staff;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public long getId() {
		return id;
	}
	public long getPid() {
		return pid;
	}
	public void setPid(long pid) {
		this.pid = pid;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<organization> getChildren() {
		return children;
	}
	public void setChildren(List<organization> children) {
		this.children = children;
	}
	public List<staffs> getStaff() {
		return staff;
	}
	public void setStaff(List<staffs> staff) {
		this.staff = staff;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
}
