package com.fenghuo.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class datatest {
	public static void main(String[] args) {
		java.util.Date dt=new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置显示格式
		String nowTime="";
		nowTime= df.format(dt);
	
		System.out.println(nowTime);
		
	}
}
