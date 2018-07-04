package com.advance.mgr.controller.sys;

import com.advance.mgr.common.ResultDto;
import com.advance.mgr.dto.sys.SysUserQueryDto;
import com.advance.mgr.dto.sys.SysUserReqDto;
import com.advance.mgr.dto.sys.SysUserResDto;
import com.advance.mgr.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author huangj
 * @Description: 用户相关接口
 * @date 2018/7/4
 */
@RestController
@Api(description = "用户相关接口")
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "查询用户信息", notes = "查询用户信息")
    @PostMapping(value = "/list",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultDto<List<SysUserResDto>> queryUsers(@RequestBody SysUserQueryDto dto) {
        List<SysUserResDto> sysUserResDtos = sysUserService.queryUsers(dto);
        return ResultDto.success(sysUserResDtos);
    }

    @ApiOperation(value = "新增用户", notes = "新增用户")
    @PostMapping(value = "/add",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultDto<SysUserReqDto> createAUser(@RequestBody SysUserReqDto dto) {
        // todo 校验用户是否存在
        sysUserService.createAUser(dto);
        return ResultDto.success(dto);
    }

}
