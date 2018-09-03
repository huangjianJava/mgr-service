package com.advance.mgr;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author huangj
 * @Description:  一些简单的测试
 * @date 2018/5/29
 */
public class SimpleTest {

    @Test
    public void testApache(){

    }

    @Test
    public void test() {
        if (StringUtils.isEmpty(null)) {
            System.out.println("正确");
        }else{
            System.out.println("不正确");
        }
    }

}

























