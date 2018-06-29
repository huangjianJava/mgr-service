package com.advance.mgr.service;

import com.advance.mgr.dto.sys.SysMenuQueryDto;
import com.advance.mgr.dto.sys.SysMenuReqDto;
import com.advance.mgr.dto.sys.SysUserReqDto;
import com.advance.mgr.mapper.sys.SysMenuMapper;
import com.advance.mgr.mapper.sys.SysUserMapper;
import com.advance.mgr.model.sys.SysMenuModel;
import com.advance.mgr.model.sys.SysUserModel;
import com.advance.mgr.util.CopyDataUtil;
import com.advance.mgr.util.EncryptMD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author huangj
 * @Description: 用户 service
 * @date 2018/6/28
 */
@Service
public class SysMenuService {

    @Autowired
    SysMenuMapper sysMenuMapper;

    /**
     * 查询菜单信息
     * @param dto
     * @return
     */
    public List<SysMenuModel> queryMenus(SysMenuQueryDto dto) {
        List<SysMenuModel> menuList = sysMenuMapper.getMenu(dto);
        List<Long> ids = menuList.stream().map(SysMenuModel::getId).collect(Collectors.toList());
        if (ids != null && ids.size() > 0) {
            menuList.addAll(sysMenuMapper.getButton(dto.getStatus(), ids));
        }
        return menuList;
    }

    /**
     * 新增菜单
     * @param dto
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,
            rollbackFor = {RuntimeException.class, Exception.class})
    public boolean createAMune(SysMenuReqDto dto) {
        SysMenuModel insertObj = CopyDataUtil.copyObject(dto, SysMenuModel.class);
        boolean createMenuSuccess = sysMenuMapper.insertSelective(insertObj) > 0;

        //todo 添加菜单之后给超级管理员加上此菜单的权限
        return createMenuSuccess;
    }

}
