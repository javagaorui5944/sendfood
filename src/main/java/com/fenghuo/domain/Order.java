package com.fenghuo.domain;

import java.util.List;




public class Order {

	private long order_id;
	private String order_query_id;
	private long dormitory_id;
	private long staff_id;
	private long default_order_id;
	private String order_create_time;
	private float order_cost_money;
	private float order_sell_money;
	private String order_note;
	private int order_status;
	private int order_push_count;
	
	private String staff_name;
	private String school_name;
	private String building_name;
	private String dormitory_name;
	
	
	private List<String> phones;
	private List<Snacks> items; //订单详情
	private List<Snacks> sell_items; //销售订单详情

	
	
	public int getOrder_push_count() {
		return order_push_count;
	}

	public void setOrder_push_count(int order_push_count) {
		this.order_push_count = order_push_count;
	}

	public String getStaff_name() {
		return staff_name;
	}
	
	public void setStaff_name(String staff_name) {
		this.staff_name = staff_name;
	}
	public List<Snacks> getSell_items() {
		return sell_items;
	}

	public void setSell_items(List<Snacks> sell_items) {
		this.sell_items = sell_items;
	}

	public List<Snacks> getItems() {
		return items;
	}

	public void setItems(List<Snacks> items) {
		this.items = items;
	}

	public List<String> getPhones() {
		return phones;
	}

	public void setPhones(List<String> phones) {
		this.phones = phones;
	}

	//订单状态，10表示未配送，20表示配送中，30表示配送完成，40表示结算完成，50表示返回入库，60表示已返回入库
	public long getOrder_id() {
		return order_id;
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
	
}
