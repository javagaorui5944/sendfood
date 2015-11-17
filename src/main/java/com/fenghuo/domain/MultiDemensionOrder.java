package com.fenghuo.domain;

public class MultiDemensionOrder {
	
	// 序号 订单号 订单创建时间    销售价格   配送人员 订单状态
	//寝室id   负责人名  寝室名  楼栋ID 楼栋名  学校ID 学校名 负责人名
	//销售零食数量 零食名字
	private long order_id;
	private String order_query_id;
	private long dormitory_id;
	private long building_id;
	private long school_id;
	private long staff_id;
	private long default_order_id;
	private String order_create_time;
	private float order_cost_money;
	private float order_sell_money;
	private String order_note;
	private int order_status;
	
	private String staff_name;
	private String school_name;
	private String building_name;
	private String dormitory_name;
	private int snacks_number;
	private String snacks_name;
	public long getOrder_id() {
		return order_id;
	}
	public void setOrder_id(long order_id) {
		this.order_id = order_id;
	}
	public String getOrder_query_id() {
		return order_query_id;
	}
	public void setOrder_query_id(String order_query_id) {
		this.order_query_id = order_query_id;
	}
	public long getDormitory_id() {
		return dormitory_id;
	}
	public void setDormitory_id(long dormitory_id) {
		this.dormitory_id = dormitory_id;
	}
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
	public long getStaff_id() {
		return staff_id;
	}
	public void setStaff_id(long staff_id) {
		this.staff_id = staff_id;
	}
	public long getDefault_order_id() {
		return default_order_id;
	}
	public void setDefault_order_id(long default_order_id) {
		this.default_order_id = default_order_id;
	}
	public String getOrder_create_time() {
		return order_create_time;
	}
	public void setOrder_create_time(String order_create_time) {
		this.order_create_time = order_create_time;
	}
	public float getOrder_cost_money() {
		return order_cost_money;
	}
	public void setOrder_cost_money(float order_cost_money) {
		this.order_cost_money = order_cost_money;
	}
	public float getOrder_sell_money() {
		return order_sell_money;
	}
	public void setOrder_sell_money(float order_sell_money) {
		this.order_sell_money = order_sell_money;
	}
	public String getOrder_note() {
		return order_note;
	}
	public void setOrder_note(String order_note) {
		this.order_note = order_note;
	}
	public int getOrder_status() {
		return order_status;
	}
	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}
	public String getStaff_name() {
		return staff_name;
	}
	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}
	public String getSchool_name() {
		return school_name;
	}
	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}
	public String getBuilding_name() {
		return building_name;
	}
	public void setBuilding_name(String building_name) {
		this.building_name = building_name;
	}
	public String getDormitory_name() {
		return dormitory_name;
	}
	public void setDormitory_name(String dormitory_name) {
		this.dormitory_name = dormitory_name;
	}
	public int getSnacks_number() {
		return snacks_number;
	}
	public void setSnacks_number(int snacks_number) {
		this.snacks_number = snacks_number;
	}
	public String getSnacks_name() {
		return snacks_name;
	}
	public void setSnacks_name(String snacks_name) {
		this.snacks_name = snacks_name;
	}
	
}
