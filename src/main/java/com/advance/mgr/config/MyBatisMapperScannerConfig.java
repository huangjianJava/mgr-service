package com.advance.mgr.config;

import com.advance.mgr.common.MyMapper;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;

/**
 * @author huangj
 * @Description: MyBatis 扫描类
 * @date 2018/6/5
 */
@Configuration
// 必须在 MyBatisConfig 注册后再加载MapperScannerConfigurer，否则会报错
@AutoConfigureAfter(MyBatisConfig.class)
public class MyBatisMapperScannerConfig {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.advance.mgr.mapper");
        //mapperScannerConfigurer.setAnnotationClass(MyBatisDao.class); // 可以写一个自定义注解，标注mybatis的 mapper

        // 初始化扫描器的相关配置，创建一个 Mapper 的父类
        Properties properties = new Properties();
        properties.setProperty("mappers", MyMapper.class.getName());
        properties.setProperty("style", "normal");
        properties.setProperty("notEmpty", "false");
        properties.setProperty("IDENTITY", "MYSQL");
        mapperScannerConfigurer.setProperties(properties);
        
        return mapperScannerConfigurer;
    }
}
