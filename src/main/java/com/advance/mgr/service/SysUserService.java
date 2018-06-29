package com.advance.mgr.service;

import com.advance.mgr.dto.sys.SysUserReqDto;
import com.advance.mgr.mapper.sys.SysUserMapper;
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
public class SysUserService {

    private static final String DEFULT_PASSWORD = "123456";

    @Autowired
    SysUserMapper sysUserMapper;

    /**
     * 新增用户
     * @param dto
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,
            rollbackFor = {RuntimeException.class, Exception.class})
    public boolean createAUser(SysUserReqDto dto) {
        // 新增用户数据
        SysUserModel sysUserModel = CopyDataUtil.copyObject(dto, SysUserModel.class);
        sysUserModel.setPassword(EncryptMD5Util.getMD5(DEFULT_PASSWORD));
        boolean createUserSuccess = sysUserMapper.insertSelective(sysUserModel) > 0;

        // 新增用户角色对应表数据
        if(!CollectionUtils.isEmpty(dto.getRoleIds())) {
            sysUserMapper.insertUserIdAndRole(sysUserModel.getId(), dto.getRoleIds());
        }
        return createUserSuccess;
    }

}
