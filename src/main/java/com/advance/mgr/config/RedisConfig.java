package com.advance.mgr.config;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.Map;

/**
 * @author huangj
 * @Description:  Redis 配置类
 * 备注：该项目 Redis 的 key 规定为 String类型，value 已经做了 转json 的序列化
 * @date 2018/6/11
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    /* 定义常用缓存的 value */
    public static final String ONE_HOUR_CACHE = "ONE_HOUR_CACHE";

    public static final String FOUR_HOUR_CACHE = "FOUR_HOUR_CACHE";

    public static final String MIDDLE_TIME_CACHE = "ONE_DAY_CACHE";

	@Value("${spring.redis.host}")
	private String host;

	@Value("${spring.redis.port}")
	private int port;

	@Value("${spring.redis.password}")
	private String password;

	@Value("${spring.redis.database}")
	private int database;

	@Value("${spring.redis.timeout}")
	private int timeout;

	@Value("${spring.redis.pool.max-active}")
	private int maxActive;

	@Value("${spring.redis.pool.max-wait}")
	private int maxWait;

	@Value("${spring.redis.pool.min-idle}")
	private int minIdle;

	/**
	 * JedisPoolConfig 连接池 配置
	 * todo 详细配置后面再了解
	 * @return
	 */
	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		// 最大空闲数
		jedisPoolConfig.setMaxIdle(8);
		// 最大建立连接等待时间
		jedisPoolConfig.setMaxWaitMillis(maxWait);
		// 最小能够保持idle状态的对象数
		jedisPoolConfig.setMinIdle(minIdle);
		jedisPoolConfig.setTestOnBorrow(true);
		jedisPoolConfig.setTestOnCreate(true);
		jedisPoolConfig.setTestWhileIdle(true);
		return jedisPoolConfig;
	}

	/**
	 * RedisConnectionFactory 配置
	 * todo 详细配置后面再了解
	 * @param jedisPoolConfig
	 * @return
	 */
	@Bean
	public RedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig) {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(jedisPoolConfig);
		// redis 主机地址
		jedisConnectionFactory.setHostName(host);
		// redis 主机端口号
		jedisConnectionFactory.setPort(port);
		// redis 密码
		if(StringUtils.isNotBlank(password)){
			jedisConnectionFactory.setPassword(password);
		}
		// redis 默认有16个数据库，默认指定其中一个
		jedisConnectionFactory.setDatabase(database);
		// 客户端超时设置(时间单位是毫秒)
		jedisConnectionFactory.setTimeout(timeout);
		return jedisConnectionFactory;
	}

	/**
	 * 实例化 RedisTemplate 对象
	 * 备注：序列化方式也可以自定义，Sinter 项目中通过将对象转为 JSON 在存储到 redis，
	 * 只使用到了 StringRedisSerializer 序列化器  <String, Object>
	 * @return
	 */
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		// 设置 key 和 value 的序列化方式
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		// 开启事务
		redisTemplate.setEnableTransactionSupport(true);
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		return redisTemplate;
	}

    /**
     * 缓存管理器(使用 redis 组件)
     * @param redisTemplate
     * @return
     */
    @Bean
    public CacheManager cacheManager(
            @SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);

        // 默认60秒失效
        redisCacheManager.setDefaultExpiration(60);
        // 自定义各个缓存的失效时间
        Map<String, Long> expires = Maps.newHashMap();
        expires.put("ONE_HOUR_CACHE", (long) 1000 * 60 * 60);
        expires.put("ONE_DAY_CACHE", (long) 1000 * 60 * 60 * 24);
        expires.put("FOUR_HOUR_CACHE", (long) (60 * 60 * 4));
        redisCacheManager.setExpires(expires);
        return redisCacheManager;
    }


    /**
     * 缓存数据时 key 生成策略
     * @return
     */
    @Bean
    public KeyGenerator wiselyKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sbParams = new StringBuilder();
                for (Object obj : params) {
                    sbParams.append(obj.toString());
                }

                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                sb.append(encodeMD5(sbParams.toString()));
                return sb.toString();
            }
        };
    }

    // MD5 编码
    private String encodeMD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            return inStr;
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }

        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }

}
