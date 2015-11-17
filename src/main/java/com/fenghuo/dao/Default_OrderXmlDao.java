package com.fenghuo.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.fenghuo.domain.Default_order;
import com.fenghuo.util.MybatisUtil;
@Component
public class Default_OrderXmlDao {
	
	public long add(Default_order order)
	{
		long n = 0;
		SqlSession session = null;
		
		try{
			session = MybatisUtil.createSession();
			session.insert(Default_order.class.getName()+".add" , order);
			n = order.getDefault_order_id();
			session.commit();
		}catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		}
		finally{
			if(session!=null)
				session.close();
		}
		
		return n;
		
	}
	
	
	

}
