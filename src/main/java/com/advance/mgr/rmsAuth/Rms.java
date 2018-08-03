package com.advance.mgr.rmsAuth;

import java.util.HashMap;
import java.util.Map;
import com.advance.mgr.exception.definedException.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author : hongcheng.wu
 * @version V1.0
 * @Description: 远程服务调用封装
 * @date Date : 2018/8/2 17:37
 * @since JDK 1.8
 */
@Component
@Slf4j
public class Rms {


    /**
     * 描述 : 应用名称
     */
    @Value("${spring.application.name}")
    private String springApplicationName;


    /**
     * 描述 : restTemplate
     */
    @Autowired
    private RestTemplate restTemplate;



    /**
     * 描述 : 配置
     */
    @Autowired
    private RmsProperties rmsProperties;


    /**
     *
     * @param headers headers
     * @param input 输入参数
     * @param url url路径
     * @param method 请求方法
     * @param responseType 返回类型
     * @param uriVariables rest参数
     * @param <I> 输入类型
     * @param <O> 输出类型
     * @return 服务结果
     */
    public <I, O> ResponseEntity<O> call(HttpHeaders headers, I input, String url, String method, ParameterizedTypeReference<O> responseType, Map<String, ?> uriVariables) {
        //客户端权限验证
        verification();
        //构建请求头
        HttpHeaders httpHeaders = buildSystemTagHeaders();
        //添加请求头
        if (MapUtils.isNotEmpty(headers)) {
            httpHeaders.putAll(headers);
        }

        //构建请求消息体
        HttpEntity<I> requestEntity = new HttpEntity<>(input, httpHeaders);
        //请求并且返回
        log.debug("rms url : {} , method : {} ", url, method);

        return restTemplate.exchange(url, HttpMethod.resolve(method), requestEntity, responseType,
                uriVariables != null ? uriVariables : new HashMap<String, String>());
    }

    public <I, O> ResponseEntity<O> call(I input, String url, String method, ParameterizedTypeReference<O> responseType, Map<String, ?> uriVariables) {
        return this.call(null, input, url, method,  responseType,  uriVariables) ;
    }

    /**
     * 描述 : 构建请求头
     *
     *
     * @return 请求头
     */
    private HttpHeaders buildSystemTagHeaders() {
        String secret = rmsProperties.getApplication().get(springApplicationName).getSecret();
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.HEADER_RMS_APPLICATION_NAME_CODE, springApplicationName);
        headers.add(Constant.HEADER_RMS_SIGN_CODE, Constant.sign(springApplicationName, secret));
        return headers;
    }

    /**
     * 描述 : 客户端验证
     *
     *
     */
    private void verification() {
        ApplicationMeta applicationMeta = rmsProperties.getApplication().get(springApplicationName);
        if (applicationMeta.getDisabled()) {
            throw new PermissionException(springApplicationName + " is disabled");
        }
    }




}
