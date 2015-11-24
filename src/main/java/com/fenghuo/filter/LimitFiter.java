package com.fenghuo.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LimitFiter implements HandlerInterceptor {
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		System.out.println(" afterCompletion ");

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		System.out.println(" void postHandle");

	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2) throws Exception {
		boolean b = false;
		System.out.println("被拦截了");
		
	/*	System.out.println(" boolean preHandle");
		Object UserName =  arg0.getSession().getAttribute("UserName");
		System.out.println(UserName+"preHandle");
	
		if(UserName == null){
			System.out.println("false");
			arg0.getRequestDispatcher("/WEB-INF/index.jsp").forward(arg0, arg1);
			b= true;
		}
		else{
			arg0.getRequestDispatcher("/WEB-INF/jsp/Welcome.jsp").forward(arg0, arg1);
			b=true;
		}*/
		return b;

	}
}
