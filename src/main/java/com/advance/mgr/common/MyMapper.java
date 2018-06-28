package com.advance.mgr.common;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author huangj
 * @Description:  Mapper 的父类
 * @date 2018/6/5
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
    // TODO
    // FIXME 特别注意，该接口不能被扫描到，否则会出错
}
