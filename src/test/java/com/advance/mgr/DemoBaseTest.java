package com.advance.mgr;

import com.advance.mgr.common.service.RedisService;
import com.advance.mgr.mapper.StoreInfoMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author huangj
 * @Description:
 * @date 2018/5/25
 */
public class DemoBaseTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(DemoBaseTest.class);

    @Autowired
    RedisService redisService;

    @Test
    public void testRedis(){
        String test = (String)redisService.getObject("StringTwo");
        System.out.println("test:" + test);
    }

    @Test
    public void testOne(){
        logger.error("error log 测试");
    }

}
