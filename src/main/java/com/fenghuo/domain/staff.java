package com.fenghuo.domain;

import java.io.Serializable;

public class staff implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long staff_id;
	private String staff_name;
	private String staff_tel;
	private String staff_password;
	private int staff_rank;
	//本字段用来区别用户的管理权限，1表示仓库管理员，10表示管理寝室，20表示楼栋，
	//30表示学校，40表示区县，再通过管理部分关联自己负责的部分
	private long staff_manage_partid;
	private String staff_email;
	private String staff_join_time;
	private int staff_status;//员工状态，10表示正常，0表示离职
	public long getStaff_id() {
		return staff_id;
	}
	public void setStaff_id(long staff_id) {
		this.staff_id = staff_id;
	}
	public String getStaff_name() {
		return staff_name;
	}
	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}
	public String getStaff_tel() {
		return staff_tel;
	}
	public void setStaff_tel(String staff_tel) {
		this.staff_tel = staff_tel;
	}
	public String getStaff_password() {
		return staff_password;
	}
	public void setStaff_password(String staff_password) {
		this.staff_password = staff_password;
	}
	public int getStaff_rank() {
		return staff_rank;
	}
	public void setStaff_rank(int staff_rank) {
		this.staff_rank = staff_rank;
	}
	public long getStaff_manage_partid() {
		return staff_manage_partid;
	}
	public void setStaff_manage_partid(long staff_manage_partid) {
		this.staff_manage_partid = staff_manage_partid;
	}
	public String getStaff_email() {
		return staff_email;
	}
	public void setStaff_email(String staff_email) {
		this.staff_email = staff_email;
	}
	public String getStaff_join_time() {
		return staff_join_time;
	}
	public void setStaff_join_time(String staff_join_time) {
		this.staff_join_time = staff_join_time;
	}
	public int getStaff_status() {
		return staff_status;
	}
	public void setStaff_status(int staff_status) {
		this.staff_status = staff_status;
	}
}
