package com.advance.mgr.controller.sys;

import com.advance.mgr.common.ResultDto;
import com.advance.mgr.dto.sys.SysMenuQueryDto;
import com.advance.mgr.dto.sys.SysMenuReqDto;
import com.advance.mgr.dto.sys.SysMenuResDto;
import com.advance.mgr.model.sys.SysMenuModel;
import com.advance.mgr.service.SysMenuService;
import com.advance.mgr.util.CopyDataUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author huangj
 * @Description: 菜单相关接口
 * @date 2018/6/28
 */
@RestController
@Api(description = "菜单相关接口")
@RequestMapping("/api")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     *  获取菜单所有数据
     * @param dto  SysMenuDto
     * @return     ResultData<PageInfo<SysMenuResDto>>
     */
     @ApiOperation(value = "获取数据列表", notes = "获取数据列表")
     @PostMapping(value = "/menu/list",  produces = MediaType.APPLICATION_JSON_VALUE)
     public ResultDto<List<SysMenuResDto>> getList(@RequestBody SysMenuQueryDto dto) {
         List<SysMenuModel> sysMenuModels = sysMenuService.queryMenus(dto);
         List<SysMenuResDto> sysMenuResDtos = CopyDataUtil.copyList(sysMenuModels, SysMenuResDto.class);

         sysMenuResDtos = formatMenuList(sysMenuResDtos);
         sysMenuResDtos.sort(Comparator.comparing(SysMenuResDto::getOrderNum));
         return ResultDto.success(sysMenuResDtos);
     }

    /**
     *  添加菜单
     * @param dto   SysMenuDto
     * @return      ResultData<SysMenuDto>
     */
     @ApiOperation(value = "增加数据", notes = "增加数据")
     @PostMapping(value = "/menu/add",  produces = MediaType.APPLICATION_JSON_VALUE)
     public ResultDto<Boolean> insert(@Validated @RequestBody SysMenuReqDto dto) {
         boolean createSuccess = sysMenuService.createAMune(dto);
         return ResultDto.success(createSuccess);
     }

    /**
     * 按照层级组装菜单 SysMenuResDto
     * @param rootMenu
     * @return
     */
    private List<SysMenuResDto> formatMenuList(List<SysMenuResDto> rootMenu) {
        // 先找到所有的一级菜单
        List<SysMenuResDto> menuList = rootMenu.stream().filter(menu -> menu.getParentId() == 0).collect(Collectors.toList());

        if (menuList.size() == 0) {
            menuList = rootMenu.stream().filter(menu -> menu.getType() == 2).collect(Collectors.toList());
        }

        // 为一级菜单设置子菜单，getChild是递归调用的
        for (SysMenuResDto menu : menuList) {
            menu.setChildren(getChild(menu.getId(), rootMenu));
        }
        return menuList;
    }

    /**
     * 递归查找子菜单
     * @param id        当前菜单id
     * @param rootMenu  要查找的列表
     * @return          List<SysMenuResDto>
     */
    private List<SysMenuResDto> getChild(Long id, List<SysMenuResDto> rootMenu) {
        // 子菜单
        List<SysMenuResDto> childList = new ArrayList<>();
        for (SysMenuResDto menu : rootMenu) {
            menu.setMenuType(getType(menu.getType()));
           // menu.setMenuStatus(getStatus(menu.getStatus()));
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (menu.getParentId().equals(id)) {
                childList.add(menu);
            }
        }
        // 把子菜单的子菜单再循环一遍
        // 没有url子菜单还有子菜单
        for (SysMenuResDto menu : childList) {
            // 递归
            menu.setMenuType(getType(menu.getType()));
            //menu.setMenuStatus(getStatus(menu.getStatus()));
            menu.setChildren(getChild(menu.getId(), rootMenu));
        }
        // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }


    private String getType(Integer type) {
       return 1 == type ? "目录" : 2 == type ? "菜单" : "按钮";
    }

//    private String getStatus(Integer status) {
//        return 1 == status ? "启用" : "停用";
//    }
}
