package com.advance.mgr.mapper.sys;

import com.advance.mgr.common.MyMapper;
import com.advance.mgr.model.sys.SysRoleModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author huangj
 * @Description:  SysRoleModel mapper
 * @date 2018/6/29
 */
@Component
public interface SysRoleMapper extends MyMapper<SysRoleModel> {

    /**
     * 查询角色对应有权限的菜单ID
     * @param id   角色id
     * @return     List<String>
     */
    List<Integer> getMenuByRoleId(@Param("roleId") Long id);

    /**
     * 新增角色所有菜单权限
     * @param userId
     * @param menuIds
     */
    void insertRoleIdAndMenu(@Param("roleId") Long userId,@Param("menuList") List<Integer> menuIds);

}