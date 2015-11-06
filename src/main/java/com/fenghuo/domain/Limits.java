package com.fenghuo.domain;
/**
 *	limits_id		int(11)	
 	limits_name		varchar(20)
 	limits_note		text
 	limits_status	int(11)
 	limits_url		text
 	limits_pname	varchar(20)
 	limits_pid		int(11)
 */
public class Limits {
	private	int limits_id;
	private	String limits_name;
	private	String limits_note;
	private	int limits_status;
	private	String limits_url;
	private	int limits_pid;
	private String limits_pname;
	public String getLimits_pname() {
		return limits_pname;
	}
	public void setLimits_pname(String limits_pname) {
		this.limits_pname = limits_pname;
	}
	public int getLimits_id() {
		return limits_id;
	}
	public void setLimits_id(int limits_id) {
		this.limits_id = limits_id;
	}
	public String getLimits_name() {
		return limits_name;
	}
	public void setLimits_name(String limits_name) {
		this.limits_name = limits_name;
	}
	public String getLimits_note() {
		return limits_note;
	}
	public void setLimits_note(String limits_note) {
		this.limits_note = limits_note;
	}
	public int getLimits_status() {
		return limits_status;
	}
	public void setLimits_status(int limits_status) {
		this.limits_status = limits_status;
	}
	public String getLimits_url() {
		return limits_url;
	}
	public void setLimits_url(String limits_url) {
		this.limits_url = limits_url;
	}
	public int getLimits_pid() {
		return limits_pid;
	}
	public void setLimits_pid(int limits_pid) {
		this.limits_pid = limits_pid;
	}
	@Override
	public String toString() {
		return "Limits [limits_id=" + limits_id + ", limits_name="
				+ limits_name + ", limits_note=" + limits_note
				+ ", limits_status=" + limits_status + ", limits_url="
				+ limits_url + ", limits_pid=" + limits_pid + ", limits_pname="
				+ limits_pname + "]";
	}
	
}
