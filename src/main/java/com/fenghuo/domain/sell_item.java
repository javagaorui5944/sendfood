package com.fenghuo.domain;

public class sell_item {
	  private long sell_item_id;
	  private long snacks_id;
	  private int snacks_number ;//'零食数量',
	public long getSell_item_id() {
		return sell_item_id;
	}
	public void setSell_item_id(long sell_item_id) {
		this.sell_item_id = sell_item_id;
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
