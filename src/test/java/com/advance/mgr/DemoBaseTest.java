package com.advance.mgr;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huangj
 * @Description:
 * @date 2018/5/25
 */
public class DemoBaseTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(DemoBaseTest.class);

    @Test
    public void testOne(){
        logger.error("error log 测试");
    }

}
