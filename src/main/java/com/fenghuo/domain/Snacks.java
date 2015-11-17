package com.fenghuo.domain;
public class Snacks {
    private Long snacks_id;

    private Long storage_id;

    private String snacks_name;

    private String snacks_bar_code;

    private String snacks_pic;

    private Float snacks_cost_price;

    private Float snacks_sell_price;

    private Integer snacks_stock_number;
    
    private int snacks_number; // '零食数量',

    private Integer eat_number=0;
    
    private Integer snacks_status;

	public Long getSnacks_id() {
		return snacks_id;
	}

	public int getSnacks_number() {
		return snacks_number;
	}

	public int getEat_number() {
		return eat_number;
	}

	public void setEat_number(int eat_number) {
		this.eat_number = eat_number;
	}

	public void setSnacks_number(int snacks_number) {
		this.snacks_number = snacks_number;
	}

	public void setSnacks_id(Long snacks_id) {
		this.snacks_id = snacks_id;
	}

	public Long getStorage_id() {
		return storage_id;
	}

	public void setStorage_id(Long storage_id) {
		this.storage_id = storage_id;
	}

	public String getSnacks_name() {
		return snacks_name;
	}

	public void setSnacks_name(String snacks_name) {
		this.snacks_name = snacks_name;
	}

	public String getSnacks_bar_code() {
		return snacks_bar_code;
	}

	public void setSnacks_bar_code(String snacks_bar_code) {
		this.snacks_bar_code = snacks_bar_code;
	}

	public String getSnacks_pic() {
		return snacks_pic;
	}

	public void setSnacks_pic(String snacks_pic) {
		this.snacks_pic = snacks_pic;
	}

	public Float getSnacks_cost_price() {
		return snacks_cost_price;
	}

	public void setSnacks_cost_price(Float snacks_cost_price) {
		this.snacks_cost_price = snacks_cost_price;
	}

	public Float getSnacks_sell_price() {
		return snacks_sell_price;
	}

	public void setSnacks_sell_price(Float snacks_sell_price) {
		this.snacks_sell_price = snacks_sell_price;
	}

	public Integer getSnacks_stock_number() {
		return snacks_stock_number;
	}

	public void setSnacks_stock_number(Integer snacks_stock_number) {
		this.snacks_stock_number = snacks_stock_number;
	}

	public Integer getSnacks_status() {
		return snacks_status;
	}

	public void setSnacks_status(Integer snacks_status) {
		this.snacks_status = snacks_status;
	}

}
