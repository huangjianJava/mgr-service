package com.advance.mgr.mapper;

import com.advance.mgr.common.MyMapper;
import com.advance.mgr.model.StoreInfoModel;
import org.springframework.stereotype.Component;

/**
 * @author huangj
 * @Description: 仓库 mapper -> 集成调用 mapper 的处理
 * @date 2018/6/5
 */
@Component
public interface StoreInfoMapper extends MyMapper<StoreInfoModel> {

    long countStoreInfo();

}