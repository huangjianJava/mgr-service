package com.advance.mgr.rmsAuth;

import java.io.Serializable;
import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : hongcheng.wu
 * @version V1.0
 * @Description: 读取属性文件
 * @date Date : 2018/8/2 16:35
 * @since JDK 1.8
 */
@Data
@Component
@ConfigurationProperties(prefix = "com.rms.properties")
public class RmsProperties implements Serializable {

    /**
     * 描述 : ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 描述 : 应用清单(应用名称 : 应用地址)
     */
    private Map<String, ApplicationMeta> application;

}
