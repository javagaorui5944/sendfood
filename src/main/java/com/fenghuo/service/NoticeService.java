package com.fenghuo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenghuo.dao.NoticeDao;
import com.fenghuo.domain.notice;

@Service
public class NoticeService {

	@Autowired
	private NoticeDao noticedao;
	
	/**
	 * 取得所有的学校公告信息
	 */
	public List<notice> listnotice(){
		return noticedao.Listnotice();
	}
	/**
	 * 取得所有的学校公告信息
	 */
	public List<notice> listnotice(Long region_id){
		return noticedao.Listnoticebyregion(region_id);
	}
	/**
	 * 添加修改公告
	 * @param notice_id
	 * @param school_id
	 * @param notice_note
	 * @return
	 */
	public String operation(Long notice_id ,Long school_id,String notice_note){
		if(notice_id==null)
			return noticedao.insertnotice(school_id, notice_note);
		else
			return noticedao.updatenotice(notice_id, notice_note);
		
	}
	
}
