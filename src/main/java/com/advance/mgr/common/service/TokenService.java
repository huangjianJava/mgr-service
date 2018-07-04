package com.advance.mgr.common.service;

import com.advance.mgr.dto.login.UserLoginResDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author huangj
 * @Description:  Redis service 工具类
 * @date 2018/6/11
 */
@Service
public class TokenService {

    /** token redis key 前缀 */
    private static final String TOKEN_PREFIX = "cloud:mgr:token:";

    /** token 有效期默认为 3600秒(一小时) */
    private static final long TOKEN_SECOND_3600 = 3600;

    @Autowired
    private RedisService redisService;

    /**
     * 保存token信息
     * @param token      token
     * @param userInfo   用户信息实体类
     */
    public void saveTokenInf(String token, UserLoginResDto userInfo){
        redisService.setTimeLimitObject(getTokenKey(token),userInfo,TOKEN_SECOND_3600);
    }

    private String getTokenKey(String tokenStr) {
        return TOKEN_PREFIX + tokenStr;
    }


}
