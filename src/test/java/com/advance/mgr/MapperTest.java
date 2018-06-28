package com.advance.mgr;

import com.advance.mgr.mapper.StoreInfoMapper;
import com.advance.mgr.model.DemoModel;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author huangj
 * @Description:
 * @date 2018/5/25
 */
public class MapperTest extends BaseTest {

    @Autowired
    StoreInfoMapper storeInfoMapper;

    @Test
    public void testMybatis(){
        DemoModel demoModel = storeInfoMapper.selectAll().get(0);
        System.out.println("name:" + demoModel.getStoreName());

        long size = storeInfoMapper.countStoreInfo();
        System.out.println("account:" + size);
    }

}
