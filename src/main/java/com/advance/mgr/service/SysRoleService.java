package com.advance.mgr.service;

import com.advance.mgr.dto.sys.SysRoleReqDto;
import com.advance.mgr.mapper.sys.SysRoleMapper;
import com.advance.mgr.model.sys.SysRoleModel;
import com.advance.mgr.util.CopyDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;


/**
 * @author huangj
 * @Description: 用户 service
 * @date 2018/6/28
 */
@Service
public class SysRoleService {

    @Autowired
    SysRoleMapper sysRoleMapper;

    /**
     * 查询所有的角色
     * @return
     */
    public List<SysRoleModel> queryAllRoles() {
        return sysRoleMapper.selectAll();
    }

    /**
     * 查询角色对应有权限的菜单ID
     * @param id    角色ID
     * @return      菜单ID集合
     */
    public List<Integer> queryMenuByRoleId(Long id) {
        return sysRoleMapper.getMenuByRoleId(id);
    }

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
