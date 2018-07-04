package com.advance.mgr.controller.sys;

import com.advance.mgr.common.ResultDto;
import com.advance.mgr.dto.sys.SysRoleReqDto;
import com.advance.mgr.dto.sys.SysRoleResDto;
import com.advance.mgr.model.sys.SysRoleModel;
import com.advance.mgr.service.SysRoleService;
import com.advance.mgr.util.CopyDataUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author huangj
 * @Description: 角色相关接口
 * @date 2018/7/3
 */
@RestController
@Api(description = "角色相关接口")
@RequestMapping("/role")
public class SysRoleController{

    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation(value = "查询所有的角色", notes = "查询所有的角色")
    @PostMapping(value = "/all",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultDto<List<SysRoleResDto>> queryAllRoles() {
        List<SysRoleModel> sysRoleModels = sysRoleService.queryAllRoles();
        List<SysRoleResDto> sysRoleResDtos = CopyDataUtil.copyList(sysRoleModels, SysRoleResDto.class);
        return ResultDto.success(sysRoleResDtos);
    }

    @ApiOperation(value = "查询角色对应有权限的菜单ID", notes = "查询角色对应有权限的菜单ID")
    @GetMapping(value = "/queryMenus",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultDto<List<Integer>> queryMenuByRoleId(@ApiParam(value = "角色ID") @RequestParam Long roleId) {
        List<Integer> menuIds = sysRoleService.queryMenuByRoleId(roleId);
        return ResultDto.success(menuIds);
    }

     @ApiOperation(value = "新增角色并绑定角色与菜单权限关系", notes = "新增角色并绑定角色与菜单权限关系")
     @PostMapping(value = "/add",  produces = MediaType.APPLICATION_JSON_VALUE)
     public ResultDto<SysRoleReqDto> insert(@Valid @RequestBody SysRoleReqDto dto) {
         // todo 校验角色名是否存在
         sysRoleService.createARole(dto);
         return ResultDto.success(dto);
     }

}
