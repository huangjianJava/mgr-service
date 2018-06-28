package com.advance.mgr.controller;


import com.advance.mgr.common.ResultDto;
import com.advance.mgr.model.StoreInfoModel;
import com.advance.mgr.service.DemoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    DemoService demoService;

    @GetMapping(value = "/stores")
    @ApiOperation(value = "查询全部的仓库信息", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultDto<List<StoreInfoModel>> queryAllStoreInfo(){
        List<StoreInfoModel> storeInfoModels = demoService.queryAllStoreInfo();
        return ResultDto.success(storeInfoModels);
    }

    @RequestMapping("/profile")
    public String testProfile(){
        return serverPort;
    }

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String sayHello(){
        return "hello world";
    }

}


















