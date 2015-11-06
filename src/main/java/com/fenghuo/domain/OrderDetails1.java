package com.fenghuo.domain;

public class OrderDetails1 {
	  private String snacks_name;
	  private Float snacks_sell_price;
	  private int snacks_number; // '零食数量',
	public String getSnacks_name() {
		return snacks_name;
	}
	public void setSnacks_name(String snacks_name) {
		this.snacks_name = snacks_name;
	}
	public Float getSnacks_sell_price() {
		return snacks_sell_price;
	}
	public void setSnacks_sell_price(Float snacks_sell_price) {
		this.snacks_sell_price = snacks_sell_price;
	}
	public int getSnacks_number() {
		return snacks_number;
	}
	public void setSnacks_number(int snacks_number) {
		this.snacks_number = snacks_number;
	}
}
