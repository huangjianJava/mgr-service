
package com.advance.mgr.rmsAuth;

import java.io.Serializable;
import lombok.Data;

/**
* @Description: 定义Application元数据
* @author : hongcheng.wu
* @date Date : 2018/8/2 16:31
* @version V1.0
* @since JDK 1.8
*/
@Data
public class ApplicationMeta implements Serializable {

    /**
     * 描述 : ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * 描述 : 服务ID 比如:127.0.0.1:8080
     */
    private String serviceId;

    /**
     * 描述 : 私钥
     */
    private String secret;

    /**
     * 描述 : 禁止服务调用
     */
    private Boolean disabled = false;

    /**
     * 描述 : 描述
     */
    private String description;

}
