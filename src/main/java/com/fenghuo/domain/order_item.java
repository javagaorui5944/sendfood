package com.fenghuo.domain;

public class order_item {
	private long  item_id;
	private long default_order_id;
	private long snacks_id;
	private int snacks_number; // '零食数量',
	
	private String snacks_name;
	private String snacks_bar_code;
	private Float snacks_sell_price;

	
	
	public Float getSnacks_sell_price() {
		return snacks_sell_price;
	}
	public void setSnacks_sell_price(Float snacks_sell_price) {
		this.snacks_sell_price = snacks_sell_price;
	}
	public String getSnacks_bar_code() {
		return snacks_bar_code;
	}
	public void setSnacks_bar_code(String snacks_bar_code) {
		this.snacks_bar_code = snacks_bar_code;
	}
	public String getSnacks_name() {
		return snacks_name;
	}
	public void setSnacks_name(String snacks_name) {
		this.snacks_name = snacks_name;
	}
	public long getItem_id() {
		return item_id;
	}
	public void setItem_id(long item_id) {
		this.item_id = item_id;
	}
	public long getDefault_order_id() {
		return default_order_id;
	}
	public void setDefault_order_id(long default_order_id) {
		this.default_order_id = default_order_id;
	}
	public long getSnacks_id() {
		return snacks_id;
	}
	public void setSnacks_id(long snacks_id) {
		this.snacks_id = snacks_id;
	}
	public int getSnacks_number() {
		return snacks_number;
	}
	public void setSnacks_number(int snacks_number) {
		this.snacks_number = snacks_number;
	}
}
