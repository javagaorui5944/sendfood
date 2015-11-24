package com.fenghuo.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.fenghuo.domain.customer;
import com.fenghuo.domain.staff;
import com.fenghuo.service.limitsService;

public class AuthFilter implements Filter {

	@Autowired
	private limitsService limitsService;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		    HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		    HttpServletResponse httpServletResponse = (HttpServletResponse)response;
		    // 请求的uri  
		    String uri = httpServletRequest.getRequestURI();  
		   
	        // 不过滤的uri  
	        String[] notFilter =  
	            new String[] {"/images","/static", "/js", "/css","/adminLogin", "/userManage/customerLogin","/userManage/staffLogin"};
	        String[] customerFilter = new String[] {"/welcome","/customer/getAllSchool","/customer/getcustomer","/customer/updatecustomer",
					"/customer/selectOrdersHistory","/customer/selectOrderDetails","/customer/updatepassword",
					"/order/scheduleOrder","/order/getNextWeekOrder",
					"SettledOnCampus",
					"joinUs",
					"contact",
					"aboutUs",
					"/order/getDefaultOrderBySchId"}; //消费者url
	        // 是否过滤  
	    
	        /**
	         * 权限管理测试
	         * @author gr
	         * 
	         */
	        if(check(uri)){
	        	httpServletRequest.setAttribute("uri",uri);
	        	httpServletRequest.getRequestDispatcher("/limitManageController/limitManage").forward(httpServletRequest, httpServletResponse);
	        //	filterChain.doFilter(request, response);  
	        	return ;
	     
	        }
	        
	        boolean doFilter = false;  
	        
	        for (String s : notFilter)  
	        {  
	            if (uri.indexOf(s) != -1)  
	            {  
	                // 如果uri中包含不过滤的uri，则不进行过滤  
	                doFilter = true;  
	                break;
	            }	            
	        }  	        
	        if(uri.equals("/")){
	        	 doFilter = true;  
	        }
	        
	        if(doFilter){
	        	
	        	filterChain.doFilter(request, response);  
	        }else{
	        	// 执行过滤  
	        	boolean isCustomer=false;
	        	HttpSession httpSession = httpServletRequest.getSession();
	        	//过滤用户请求
	        	for (String s : customerFilter)  
		        {  
		            if (uri.indexOf(s) != -1)  
		            {
		            	isCustomer=true;
		            	customer cus = (customer)httpSession.getAttribute("customer");
		            	if(cus != null){
		            		//消费者url请求通过
			            	httpServletRequest.setAttribute("customer", cus);
		            	}
			  			filterChain.doFilter(request, response);
		            }
		        }
	        	  
	        	if(!isCustomer){
	            	staff staff = (staff)httpSession.getAttribute("staff");
	   
					if(staff  == null){
						boolean isAjaxRequest = isAjaxRequest(httpServletRequest);
					    if(isAjaxRequest){  
					    	httpServletResponse.setCharacterEncoding("UTF-8");  
					    	httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "request unauthorized or outdated");  
					    	return;  
					    } 
				
					    httpServletResponse.sendRedirect("/adminLogin"); 
					}else{
						//管理人员权限验证
						/*if(limitsService.staff_limit_check(staff.getStaff_id(), uri)){*/
							//权限验证通过
						    staff.setStaff_password(uri);
							httpServletRequest.setAttribute("staff", staff);
						
							filterChain.doFilter(request, response); 
						/*}else{
						//权限未通过
						}	*/						 
					}
	        	}
		    }
	}
	       

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	 /** 判断是否为Ajax请求  
	    * <功能详细描述> 
	    * @param request 
	    * @return 是true, 否false  
	    */  
	   public static boolean isAjaxRequest(HttpServletRequest request)  
	   {  
	       String header = request.getHeader("X-Requested-With");   
	       if (header != null && "XMLHttpRequest".equals(header))   
	           return true;   
	       else   
	           return false;    
	   }  
	   
	   
	   /**
	    * gr正则过滤*测试
	    */
	   public static boolean check(String str){
			  
			  Pattern p = Pattern.compile("[*]");
			   Matcher m = p.matcher(str);
			   boolean match = m.find();
			   if(match){
			    return true;
			   }
			   return false;
			 }
}
