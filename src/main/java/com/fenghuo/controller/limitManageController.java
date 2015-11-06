package com.fenghuo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fenghuo.domain.staff;
import com.fenghuo.domain.staff_role;
import com.fenghuo.service.limitsService;

@Controller
@RequestMapping("/limitManageController")
public class limitManageController {
	@Autowired
	private limitsService limitsService;

	/**
	 * 权限判断
	 * 
	 * @author gr
	 * @return pid_url 需要前台传过来,为*后面显示数据的url,明确跳转到哪个views层,
	 *         其余跳转views层显示数据的RequestMapping方法接口可以关闭,保证安全性，只留这一个方法跳转。
	 */
	@RequestMapping("/limitManage")
	public String LimitTest(HttpServletRequest request,
			HttpServletResponse response, HttpSession httpSession) {
		String viewUri = null;
		String controlleruri = null;
		staff s = (staff) httpSession.getAttribute("staff");
		if (s == null) {
			System.out.println(" return/admin/login");
			try {
				response.sendRedirect("/adminLogin");

			} catch (Exception e) {

				e.printStackTrace();
			}
			return null;
		}

		else {
			long staff_id = s.getStaff_id();

			String uri = request.getAttribute("uri").toString();

			// 查看该员工是否有权限
			staff_role sr = limitsService.testLimits(staff_id, uri);

			viewUri = uri.substring(uri.indexOf("*") + 1);

			// 跳转判断
			if (sr != null) {
				if (viewUri.equals("/manageRoleLimits")) {
					controlleruri = uri.substring(0, uri.indexOf("*") - 1);
					// String viewUri1 = uri.substring(uri.indexOf("*")+2);
					try {
						request.getRequestDispatcher(controlleruri + viewUri)
								.forward(request, response);
						System.out.println("controlleruri+viewUri"
								+ controlleruri + viewUri);
						return null;
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else if (viewUri.equals("/addOrg")) {
					try {
						controlleruri = uri.substring(0, uri.indexOf("*") - 1);
						request.getRequestDispatcher(controlleruri + viewUri)
								.forward(request, response);
						System.out.println("controlleruri+viewUri"
								+ controlleruri + viewUri);
						return null;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				System.out.println("////viewUri****" + viewUri);
				return viewUri;

			} else {
				try {
					response.sendRedirect("/adminLogin");
				} catch (Exception e) {

					e.printStackTrace();
				}
				return null;
			}
		}

	}
}
