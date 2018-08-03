/**
 * RmsController.java
 * Created at 2017-05-25
 * Created by Administrator
 * Copyright (C) 2016 itkk.org, All rights reserved.
 */
package com.advance.mgr.rmsAuth;

import com.advance.mgr.exception.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
* @Description: RmsController验证
* @author : hongcheng.wu
* @date Date : 2018/8/2 16:34
* @version V1.0
* @since JDK 1.8
*/
@Api(value = "rest服务", consumes = "application/json", produces = "application/json", protocols = "http")
@RestController
@RequestMapping("/test/rms")
public class RmsController {

    /**
     * 描述 : rmsProperties
     */
    @Autowired(required = false)
    private RmsProperties rmsProperties;


    /**
     * 描述 : 获得rms配置详情
     *
     * @return rms配置详情
     */
    @ApiOperation(value = "RMS_2", notes = "获得rms配置详情")
    @RequestMapping(value = "/properties", method = RequestMethod.GET)
    public RestResponse<RmsProperties> getProperties() {
        return new RestResponse<>(rmsProperties);
    }

}
