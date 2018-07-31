package com.advance.mgr.service;

import com.advance.mgr.controller.HelloController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

/**
 * @author : hongcheng.wu
 * @version V1.0
 * @Description:spring Retry测试
 * https://blog.csdn.net/u014513883/article/details/52371198
 * @date Date : 2018/7/31 20:53
 * @since JDK 1.8
 */
@Service
public class ReteyService {
    private static final Logger logger = LoggerFactory.getLogger(ReteyService.class);

    @Retryable(value= {RemoteAccessException.class},maxAttempts = 3,backoff = @Backoff(delay = 5000L,multiplier = 1))
    public void call() throws Exception {
        logger.info("do something...");
        throw new RemoteAccessException("RPC调用异常");
    }
    @Recover
    public void recover(RemoteAccessException e) {
        logger.info(e.getMessage());
    }
}
