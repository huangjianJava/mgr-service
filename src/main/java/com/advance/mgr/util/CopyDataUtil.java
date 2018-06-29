
package com.advance.mgr.util;

import com.google.common.collect.Lists;
import java.util.List;

/**
 * @author huangj
 * @Description:  copy 工具类
 * @date 2018/6/29
 */
public class CopyDataUtil {

  public static <T, V> V copyObject(T source, Class<V> clazz) {
    if (source == null) {
        return null;
    }
    V newObj = null;
    try {
      newObj = clazz.newInstance();
      org.springframework.beans.BeanUtils.copyProperties(source, newObj);
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    }
    return newObj;
  }

  public static <T, V> List<V> copyList(List<T> list, Class<V> clazz) {
    List<V> data = Lists.newArrayList();
    if (list != null) {
      for (T obj : list) {
        V dto = null;
        try {
          dto = clazz.newInstance();
          org.springframework.beans.BeanUtils.copyProperties(obj, dto);
        } catch (InstantiationException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (IllegalAccessException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        if (dto != null) {
          data.add(dto);
        }
      }
      return data;
    } else {
      return null;
    }
  }

}
