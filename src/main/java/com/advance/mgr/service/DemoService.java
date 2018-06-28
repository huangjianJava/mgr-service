package com.advance.mgr.service;

import com.advance.mgr.mapper.StoreInfoMapper;
import com.advance.mgr.model.DemoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author huangj
 * @Description: service demo
 * @date 2018/6/28
 */
@Service
public class DemoService {

    @Autowired
    StoreInfoMapper storeInfoMapper;

    /**
     * 查询所有仓库信息
     * @return
     */
    public List<DemoModel> queryAllStoreInfo() {
        return storeInfoMapper.selectAll();
    }

}
