package com.advance.mgr.mapper.sys;

import com.advance.mgr.common.MyMapper;
import com.advance.mgr.dto.sys.SysMenuQueryDto;
import com.advance.mgr.model.sys.SysMenuModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author huangj
 * @Description:  SysMenuModel mapper
 * @date 2018/6/29
 */
@Component
public interface SysMenuMapper extends MyMapper<SysMenuModel> {

    /**
     * 返回按钮信息
     *
     * @param ids     菜单id
     * @param status  状态
     * @return       list<SysMenuModel>
     */
    List<SysMenuModel> getButton(@Param("status") Integer status, @Param("ids") List<Long> ids);

    /**
     * 返回菜单信息(不包含按钮)
     * @param dto    菜单数据
     * @return       list<SysMenuModel>
     */
    List<SysMenuModel> getMenu(SysMenuQueryDto dto);

}