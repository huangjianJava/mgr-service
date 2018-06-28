package com.advance.mgr.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @author huangj
 * @Description:  demo controller
 * @date 2018/5/25
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping("/profile")
    public String testProfile(){
        return serverPort;
    }

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String sayHello(){
        return "hello world";
    }

}


















