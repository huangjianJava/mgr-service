package com.advance.mgr.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author huangj
 * @Description:  Redis service 工具类
 * @date 2018/6/11
 */
@Service
public class RedisService {

    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    private static final String REDIS_SERVICE_MSG = "redis service msg:%s";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // ============================= common ============================
    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间(秒)
     * @return
     */
    public boolean setExpireTime(String key,long time){
        try {
            if(time > 0){
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            logger.error(String.format(REDIS_SERVICE_MSG,e.getMessage()));
            return false;
        }
    }

    /**
     * 根据 key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpireTime(String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    /**
     * 判断 key 是否存在
     * @param key 键
     * @return true:存在  false:不存在
     */
    public boolean hasKey(String key){
        try {
            return redisTemplate.hasKey(key).booleanValue();
        } catch (Exception e) {
            logger.error(String.format(REDIS_SERVICE_MSG,e.getMessage()));
            return false;
        }
    }

    /**
     * 删除缓存
     * @param keys
     */
    public void delete(List<String> keys){
        redisTemplate.delete(keys);
    }

    // ============================ String\int\对象 =============================
    /**
     * 普通缓存放入
     * @param key 键
     * @param value 值
     * @return true:成功 false:失败
     */
    public boolean setObject(String key,Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            logger.error(String.format(REDIS_SERVICE_MSG,e.getMessage()));
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true:成功   false:失败
     */
    public boolean setTimeLimitObject(String key,Object value,long time){
        try {
            if(time > 0){
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            }else{
                setObject(key, value);
            }
            return true;
        } catch (Exception e) {
            logger.error(String.format(REDIS_SERVICE_MSG,e.getMessage()));
            return false;
        }
    }

    /**
     * 普通缓存获取
     * @param key 键
     * @return 值
     */
    public Object getObject(String key){
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    // =============================== List =================================

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @return
     */
    public boolean setList(String key, List value) {
        try {
            Long pushCount = redisTemplate.opsForList().rightPushAll(key, value);
            return pushCount > 0 ? true : false;
        } catch (Exception e) {
            logger.error(String.format(REDIS_SERVICE_MSG,e.getMessage()));
            return false;
        }
    }

    /**
     * 将list放入缓存并设置有效时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    public boolean setTimeLimitList(String key, List value, long time) {
        try {
            Long pushCount = redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0){
                setExpireTime(key, time);
            }
            return pushCount > 0 ? true : false;
        } catch (Exception e) {
            logger.error(String.format(REDIS_SERVICE_MSG,e.getMessage()));
            return false;
        }
    }

    /**
     * 获取list缓存的内容
     * @param key 键
     * @param start 开始
     * @param end 结束  0 到 -1代表所有值
     * @return
     */
    public List getList(String key,long start, long end){
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            logger.error(String.format(REDIS_SERVICE_MSG,e.getMessage()));
            return Collections.emptyList();
        }
    }

    /**
     * 获取list缓存的长度
     * @param key 键
     * @return
     */
    public long getListSize(String key){
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            logger.error(String.format(REDIS_SERVICE_MSG,e.getMessage()));
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     * @param key 键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object getOneByKeyAndIndex(String key,long index){
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            logger.error(String.format(REDIS_SERVICE_MSG,e.getMessage()));
            return null;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     * @param key 键
     * @param index 索引
     * @param value 值
     * @return
     */
    public boolean updateOneByKeyAndIndex(String key, long index,Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            logger.error(String.format(REDIS_SERVICE_MSG,e.getMessage()));
            return false;
        }
    }

    // ================================ Map =================================
    /**
     * 设置 map 缓存
     * @param key redis key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean setMap(String key, Map<String,?> map){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            logger.error(String.format(REDIS_SERVICE_MSG,e.getMessage()));
            return false;
        }
    }

    /**
     * 设置 map 缓存并设置有效时间
     * @param key 键
     * @param map 对应多个键值
     * @param time 时间(秒)
     * @return true:成功 false:失败
     */
    public boolean setTimeLimitMap(String key, Map<String,?> map, long time){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if(time > 0){
                setExpireTime(key, time);
            }
            return true;
        } catch (Exception e) {
            logger.error(String.format(REDIS_SERVICE_MSG,e.getMessage()));
            return false;
        }
    }

    /**
     * 获取指定 key 的数据
     * @param redisKey 键 redis的key,不能为null
     * @param hashKey 项 存放Map的key,不能为null
     * @return 值
     */
    public Object getMap(String redisKey,String hashKey){
        return redisTemplate.opsForHash().get(redisKey, hashKey);
    }

    /**
     * 获取 Map 所有的键值
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object,Object> getAllKeys(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 判断hash表中是否有该项的值
     * @param redisKey 键 不能为null
     * @param hashKey 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hHasKey(String redisKey, String hashKey){
        return redisTemplate.opsForHash().hasKey(redisKey, hashKey);
    }

}
