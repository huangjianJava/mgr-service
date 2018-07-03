package com.advance.mgr.controller.sys;

import com.advance.mgr.common.ResultDto;
import com.advance.mgr.common.enums.sys.MenuTypeEnum;
import com.advance.mgr.dto.sys.SysMenuQueryDto;
import com.advance.mgr.dto.sys.SysMenuReqDto;
import com.advance.mgr.dto.sys.SysMenuResDto;
import com.advance.mgr.model.sys.SysMenuModel;
import com.advance.mgr.service.SysMenuService;
import com.advance.mgr.util.CopyDataUtil;
import com.google.common.collect.Lists;
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
@RequestMapping("/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation(value = "获取菜单列表", notes = "获取菜单列表")
    @PostMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultDto<List<SysMenuResDto>> queryMenus(@RequestBody SysMenuQueryDto dto) {
        // 查询菜单信息
        List<SysMenuModel> sysMenuModels = sysMenuService.queryMenus(dto);
        List<SysMenuResDto> sysMenuResDtos = CopyDataUtil.copyList(sysMenuModels, SysMenuResDto.class);

        // 组装菜单 Tree并按照 orderNum 排序
        sysMenuResDtos = assembleMenuTree(sysMenuResDtos);
        sysMenuResDtos.sort(Comparator.comparing(SysMenuResDto::getOrderNum));
        return ResultDto.success(sysMenuResDtos);
    }

    @ApiOperation(value = "新增菜单", notes = "新增菜单")
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultDto<Boolean> createAMenu(@Validated @RequestBody SysMenuReqDto dto) {
        boolean createSuccess = sysMenuService.createAMenu(dto);
        return ResultDto.success(createSuccess);
    }

    /**
     * 按照层级组装菜单Tree SysMenuResDto
     * @param sourceMenus
     * @return
     */
    private List<SysMenuResDto> assembleMenuTree(List<SysMenuResDto> sourceMenus) {
        // 先找到所有的一级菜单(目录)
        List<SysMenuResDto> menuList = sourceMenus.stream()
                .filter(menu -> menu.getParentId() == 0)
                .collect(Collectors.toList());

        // 没有目录找所有的二级菜单(菜单)
        if (menuList.size() == 0) {
            menuList = sourceMenus.stream()
                    .filter(menu -> menu.getType() == 2)
                    .collect(Collectors.toList());
        }

        // 设置子菜单，递归调用 getChild
        for (SysMenuResDto menu : menuList) {
            menu.setChildren(getChild(menu.getId(), sourceMenus));
        }
        return menuList;
    }

    /**
     * 递归查找子菜单    -> 目录 - 菜单 - 按钮
     * @param id       当前菜单id
     * @param sourceMenus 要查找的列表
     * @return List<SysMenuResDto>
     */
    private List<SysMenuResDto> getChild(Long id, List<SysMenuResDto> sourceMenus) {
        // 子菜单
        List<SysMenuResDto> childList = Lists.newArrayList();
        for (SysMenuResDto menu : sourceMenus) {
            String typeText = MenuTypeEnum.parse(menu.getType()).getName();
            menu.setTypeText(typeText);
            // 遍历所有节点，将父菜单id与传过来的id比较，添加该菜单下的子菜单
            if (menu.getParentId().equals(id)) {
                childList.add(menu);
            }
        }

        // 递归查按钮
        for (SysMenuResDto menu : childList) {
            String typeText = MenuTypeEnum.parse(menu.getType()).getName();
            menu.setTypeText(typeText);
            menu.setChildren(getChild(menu.getId(), sourceMenus));
        }

        // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

}
