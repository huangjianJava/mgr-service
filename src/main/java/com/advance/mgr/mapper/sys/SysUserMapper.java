package com.advance.mgr.mapper.sys;

import com.advance.mgr.common.MyMapper;
import com.advance.mgr.dto.sys.SysUserQueryDto;
import com.advance.mgr.dto.sys.SysUserResDto;
import com.advance.mgr.model.sys.SysUserModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author huangj
 * @Description:  SysUserModel mapper
 * @date 2018/6/29
 */
@Component
public interface SysUserMapper extends MyMapper<SysUserModel> {

    /**
     * 查询用户列表
     * @param dto
     * @return
     */
    List<SysUserResDto> getList(SysUserQueryDto dto);

    /**
     * 新增用户角色
     * @param userId     用户id
     * @param roleList   角色id
     */
    void insertUserIdAndRole(@Param("userId") Long userId, @Param("roleList") List<Long> roleList);

}