package com.fenghuo.filter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class ExceptionHandler implements HandlerExceptionResolver{

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exception) {
		// TODO Auto-generated method stub
		
//		Map<String, Object> model = new HashMap<String, Object>();  
//		
//		if(exception instanceof Exception) {  
//			model.put("errorMessage", "错误");
//            return new ModelAndView("error-business", model);  
//        }else if(exception instanceof Exception) {  
//        	model.put("errorMessage", "错误");
//            return new ModelAndView("error-parameter", model);  
//        } else {  
//        	model.put("errorMessage", "错误");
//            return new ModelAndView("error", model);  
//        }
		System.out.println(handler);
		
		return null;
	}

}
