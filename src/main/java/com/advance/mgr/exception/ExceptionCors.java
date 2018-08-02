package com.advance.mgr.exception;

import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author : hongcheng.wu
 * @version V1.0
 * @Description: 跨域特殊处理
 * @date Date : 2018/7/31 17:09
 * @since JDK 1.8
 */
@Component
public class ExceptionCors {
    /**
     * <p>
     * 跨域特殊处理
     * </p>
     * <p>
     * 当请求被拦截器拦截掉(可能是认证问题),没有进入controller的时候,则无法携带cors头,这里会判断,cors头,进行补充
     * </p>
     *
     * @param response response
     */
    public void fixCors(HttpServletResponse response) {
        final String headerAccessControlAllowOrigin = "Access-Control-Allow-Origin";
        final String headerAccessControlAllowMethods = "Access-Control-Allow-Methods";
        final String headerAccessControlAllowHeaders = "Access-Control-Allow-Headers";
        final String headerValue = "*";
        if (StringUtils.isBlank(response.getHeader(headerAccessControlAllowOrigin))) {
            response.setHeader(headerAccessControlAllowOrigin, headerValue);
        }
        if (StringUtils.isBlank(response.getHeader(headerAccessControlAllowMethods))) {
            response.setHeader(headerAccessControlAllowMethods, headerValue);
        }
        if (StringUtils.isBlank(response.getHeader(headerAccessControlAllowHeaders))) {
            response.setHeader(headerAccessControlAllowHeaders, headerValue);
        }
    }
}
