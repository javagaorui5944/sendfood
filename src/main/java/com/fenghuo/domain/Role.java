package com.fenghuo.domain;
/**
 * 	role_id			int(11)
   	role_name		varchar(20)
	role_note		text
	role_status		int(11)
 */
public class Role {
	private int role_id;
	private String role_name;
	private String role_note;
	private int role_status;
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public String getRole_note() {
		return role_note;
	}
	public void setRole_note(String role_note) {
		this.role_note = role_note;
	}
	public int getRole_status() {
		return role_status;
	}
	public void setRole_status(int role_status) {
		this.role_status = role_status;
	}
}
