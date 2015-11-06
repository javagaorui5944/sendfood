package com.fenghuo.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 管理员后台
 **/
@Controller
public class adminController {

    //后台管理系统主页
    @RequestMapping("/welcome")
    public String Index(){
        return "/index";

    }
    //后台管理系统主页
    @RequestMapping("/")
    public String Index1(){
        return "/index";
    }


    @RequestMapping(value = "/contact")
    public String contact() {
        return "/customer/contact";
    }
    @RequestMapping(value = "/aboutUs")
    public String aboutUs() {
        return "/customer/aboutUs";
    }
    @RequestMapping(value = "/joinUs")
    public String joinUs() {
        return "/customer/joinUs";
    }
    @RequestMapping(value = "/SettledOnCampus")
    public String SettledOnCampus() {
        return "/customer/SettledOnCampus";
    }


    //后台管理系统主页
    @RequestMapping("/admin")
    public String adminIndex(HttpSession httpSession,Model model) {
        return "/admin/index";
    }

    //后台管理系统登录页面
    @RequestMapping(value="/adminLogin", method=RequestMethod.GET)
    public String adminLogin() {
        return "/admin/login";
    }

    @RequestMapping("/adminsetOrder")
    public String adminSetOrder(){
        return "/admin/setOrder";
    }

    @RequestMapping("/logOut")
    public void logOut(HttpServletRequest request,HttpServletResponse res,HttpSession httpSession){
        // 清除session
        httpSession.invalidate();
        try {
            res.sendRedirect("/adminLogin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
