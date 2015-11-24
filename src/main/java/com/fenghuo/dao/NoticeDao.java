package com.fenghuo.dao;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Component;

import com.fenghuo.domain.notice;

@Component
public interface NoticeDao {

	
	@InsertProvider(type=NoticeManagerDao.class, method="add_notice")
	public String insertnotice(@Param("school_id") Long school_id,@Param("notice_note")String notice_note);
	
	@UpdateProvider(type=NoticeManagerDao.class, method="update_notice")
	public String updatenotice(@Param("notice_id") Long notice_id,@Param("notice_note")String notice_note);
	/**
	 * 取出学校及公告信息
	 */
	@Select("select  notice_id,school.school_id,region_id,school_name,school_code,school_status,notice.notice_note from school left join notice on notice.school_id=school.school_id where region_id=#{region_id}")
	List<notice> Listnoticebyregion(@Param("region_id")Long region_id);
	/**
	 * 取出学校及公告信息
	 */
	@Select("select  notice_id,school.school_id,region_id,school_name,school_code,school_status,notice.notice_note from school left join notice on notice.school_id=school.school_id ")
	List<notice> Listnotice();
}
