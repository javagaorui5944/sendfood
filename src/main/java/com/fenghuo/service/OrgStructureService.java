package com.fenghuo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fenghuo.dao.OrgStructureDao;
@Service
public class OrgStructureService {
	@Autowired
	private OrgStructureDao orgStructureDao;
	
	
	public List<Object> getOrgById(int level,int orgId){
		return orgStructureDao.getOrgById(level,orgId);
	}
	
	
	
}
