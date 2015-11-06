package com.fenghuo.dao;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import java.util.Map;


public class NoticeManagerDao {
	public NoticeManagerDao(){}
	public static String add_notice(Map<String,Object> params){
		String TABLE_NAME="notice";

		BEGIN();
		INSERT_INTO(TABLE_NAME);
		VALUES("school_id",  "#{school_id}");  
        VALUES("notice_note",    "#{notice_note}");  
        return SQL();  
		
	}
	public static String update_notice(Map<String,Object> params){
		String TABLE_NAME="notice";
		BEGIN();
		UPDATE(TABLE_NAME);
		SET("notice_note=#{notice_note}");
		WHERE("notice_id=#{notice_id}");
		return SQL();
	}
}
