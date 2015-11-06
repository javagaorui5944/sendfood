package com.fenghuo.domain;

public class Order1 {
	private long  order_id;
	private String order_query_id;//'组成方式为，学校code+楼栋code+寝室code',
	private String order_create_time; //datetime '订单创建时间',
	private int   order_status;// '订单状态，10表示未配送，20表示配送中，30表示配送完成，40表示结算完成，50表示返回入库',
	private String staff_name;
	private String staff_tel;
	private String customer_name;
	private String customer_tel;
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	public String getCustomer_tel() {
		return customer_tel;
	}
	public void setCustomer_tel(String customer_tel) {
		this.customer_tel = customer_tel;
	}
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
	public String getOrder_create_time() {
		return order_create_time;
	}
	public void setOrder_create_time(String order_create_time) {
		this.order_create_time = order_create_time;
	}
	public int getOrder_status() {
		return order_status;
	}
	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}
}
