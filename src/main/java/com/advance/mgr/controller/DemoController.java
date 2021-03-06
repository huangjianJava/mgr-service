package com.advance.mgr.controller;

import com.advance.mgr.common.ResultDto;
import com.advance.mgr.model.DemoModel;
import com.advance.mgr.service.DemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author huangj
 * @Description:  demo controller
 * @date 2018/5/25
 */
@RestController
@Api(description = "demo 相关接口")
@RequestMapping("/demo")
public class DemoController {

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    DemoService demoService;

    @GetMapping(value = "/stores")
    @ApiOperation(value = "查询全部的仓库信息", notes = "查询全部的仓库信息")
    public ResultDto<List<DemoModel>> queryAllStoreInfo(){
        List<DemoModel> demoModels = demoService.queryAllStoreInfo();
        return ResultDto.success(demoModels);
    }

    @RequestMapping("/profile")
    @ApiOperation(value = "查询全部的仓库信息", notes = "查询全部的仓库信息")
    public String testProfile(){
        return serverPort;
    }

    @GetMapping(value = "/hello")
    @ApiOperation(value = "查询全部的仓库信息", notes = "查询全部的仓库信息")
    public String sayHello(){
        return "hello world";
    }

}


















