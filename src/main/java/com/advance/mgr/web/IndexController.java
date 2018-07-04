package com.advance.mgr.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 首页测试
 */
@Controller
@RequestMapping(value = "/index")
public class IndexController {


    @GetMapping(value = "/login")
    public String toLogin(Model model, HttpServletRequest request) {

        return "/index/login/login";
    }

    @GetMapping(value = "/index")
    public String toIndex(Model model, HttpServletRequest request) {

        return "/index/common";
    }

    @GetMapping(value = "/member")
    public String toMember(Model model, HttpServletRequest request) {

        return "/index/member-list";
    }
    @GetMapping(value = "/welcome")
    public String toWelcome(Model model, HttpServletRequest request) {

        return "/index/welcome";
    }
}
