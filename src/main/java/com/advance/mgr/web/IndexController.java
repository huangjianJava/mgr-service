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


    @GetMapping(value = "/")
    public String toIndex(Model model, HttpServletRequest request) {
        System.out.println("=========== toIndex ===========");
        model.addAttribute("index", "验证第一个页面");
        return "/index/index";
    }
}
