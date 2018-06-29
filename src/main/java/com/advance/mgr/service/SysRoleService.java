package com.advance.mgr.service;

import com.advance.mgr.dto.sys.SysRoleReqDto;
import com.advance.mgr.dto.sys.SysUserReqDto;
import com.advance.mgr.mapper.sys.SysRoleMapper;
import com.advance.mgr.mapper.sys.SysUserMapper;
import com.advance.mgr.model.sys.SysRoleModel;
import com.advance.mgr.model.sys.SysUserModel;
import com.advance.mgr.util.CopyDataUtil;
import com.advance.mgr.util.EncryptMD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * @author huangj
 * @Description: 用户 service
 * @date 2018/6/28
 */
@Service
public class SysRoleService {

    private static final String DEFULT_PASSWORD = "123456";

    @Autowired
    SysRoleMapper sysRoleMapper;

    /**
     * 新增角色
     * @param dto
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,
            rollbackFor = {RuntimeException.class, Exception.class})
    public boolean createARole(SysRoleReqDto dto) {
        // 新增角色数据
        SysRoleModel sysRoleModel = CopyDataUtil.copyObject(dto, SysRoleModel.class);
        boolean createRoleSuccess = sysRoleMapper.insertSelective(sysRoleModel) > 0;

        // 新增角色与菜单权限对应表数据
        if(!CollectionUtils.isEmpty(dto.getMenuIds())) {
            sysRoleMapper.insertRoleIdAndMenu(sysRoleModel.getId(),dto.getMenuIds());
        }
        return createRoleSuccess;
    }

}
