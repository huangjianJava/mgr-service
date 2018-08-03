package com.advance.mgr.rmsAuth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.advance.mgr.exception.definedException.AuthException;
import com.advance.mgr.exception.definedException.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author : hongcheng.wu
 * @version V1.0
 * @Description: restTemplete调用接口权限验证 目前只做了接口调用限制
 * 没有分配哪个应用只能访问哪些接口
 * @date Date : 2018/8/2 16:51
 * @since JDK 1.8
 */
@Slf4j
public class RmsAuthHandlerInterceptor implements HandlerInterceptor {

    /**
     * 描述 : 环境标识
     */
    private static final String DEV_PROFILES = "dev";

    @Autowired
    private RmsProperties rmsProperties;

    /**
     * 描述 : 环境变量
     */
    @Autowired
    private Environment env;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取请求方法
        String method = request.getMethod();
        //如果是options请求,则直接返回true (处理跨域情况)
        if (HttpMethod.OPTIONS.equals(HttpMethod.resolve(method))) {
            return true;
        }

        //获得当前环境
        String profilesActive = env.getProperty("spring.profiles.active");

        //获取认证信息(应用名称)
        String rmsApplicationName = request.getHeader(Constant.HEADER_RMS_APPLICATION_NAME_CODE);
        if (StringUtils.isBlank(rmsApplicationName)) {
            rmsApplicationName = request.getParameter(Constant.HEADER_RMS_APPLICATION_NAME_CODE);
        }
        //获取认证信息(sign)
        String rmsSign = request.getHeader(Constant.HEADER_RMS_SIGN_CODE);
        if (StringUtils.isBlank(rmsSign)) {
            rmsSign = request.getParameter(Constant.HEADER_RMS_SIGN_CODE);
        }

        //日志
        log.info("profiles.active:{},rmsApplicationName:{},rmsSign:{},method:{}", profilesActive, rmsApplicationName, rmsSign, method);

        //判断systemAppliation是否有效
        if (!this.rmsProperties.getApplication().containsKey(rmsApplicationName)) {
            throw new AuthException("unrecognized systemAppliation:" + rmsApplicationName);
        }

        //获得应用元数据
        ApplicationMeta applicationMeta = rmsProperties.getApplication().get(rmsApplicationName);

        //判断环境(开发环境无需校验sign)
        if (!DEV_PROFILES.equals(profilesActive)) {
            //判断是否缺少认证信息
            if (StringUtils.isBlank(rmsSign)) {
                throw new AuthException("missing required authentication parameters (rmsSign)");
            }

            //获得secret
            String secret = applicationMeta.getSecret();

            //计算sign
            String sign = Constant.sign(rmsApplicationName, secret);

            //比较sign
            if (!rmsSign.equals(sign)) {
                throw new AuthException("sign Validation failed");
            }

            //判断是否禁止调用服务权限
            if (applicationMeta.getDisabled()) {
                throw new PermissionException(rmsApplicationName + " is disabled");
            }


        }
         return true;
    }




    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        log.debug("SystemTagAuthHandlerInterceptor.postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        log.debug("SystemTagAuthHandlerInterceptor.afterCompletion");
    }
}
