package com.advance.mgr.controller;

import java.util.Random;
import java.util.concurrent.Future;
import javax.validation.Valid;
import com.advance.mgr.dto.login.UserLoginReqDto;
import com.advance.mgr.exception.RestResponse;
import com.advance.mgr.exception.definedException.ParameterValidException;
import com.advance.mgr.exception.definedException.SystemRuntimeException;
import com.advance.mgr.service.AsyncTasks;
import com.advance.mgr.service.ReteyService;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.retry.RetryException;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * @author : hongcheng.wu
 * @version V1.0
 * @Description: TODO
 * @date Date : 2018/7/31 15:15
 * @since JDK 1.8
 */
@RestController
public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);


    @Autowired
    AsyncTasks asyncTasks;

    @Autowired
    ReteyService reteyService;


    /**
     * 测试自定义线程池异步处理功能
     * @return
     * @throws Exception
     */
    @GetMapping("/task")
    public String task() throws Exception {
        long start = System.currentTimeMillis();

        Future<String> task1 = asyncTasks.doTaskOne();
        Future<String> task2 = asyncTasks.doTaskTwo();
        Future<String> task3 = asyncTasks.doTaskThree();

       /* while(true) {
            if(task1.isDone() && task2.isDone() && task3.isDone()) {
                // 三个任务都调用完成，退出循环等待
                break;
            }
            Thread.sleep(1000);
        }*/

        long end = System.currentTimeMillis();
        logger.info("第一个结果：{}",task1.get());
        logger.info("第二个结果：{}",task2.get());
        logger.info("第三个结果：{}",task3.get());

        String result = "任务全部完成，总耗时：" + (end - start) + "毫秒";
        return result;
    }

    /**
     * 测试自定义异常功能
     * @param dto
     * @param result
     * @return
     * @throws ParameterValidException
     */
    @PostMapping(value = "/exceptionTest", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestResponse<String> exceptionTest (@Valid @RequestBody UserLoginReqDto dto , BindingResult result)  throws ParameterValidException {
        if (result.hasErrors()) {
            throw new ParameterValidException("校验失败入参为空",
                    result.getAllErrors());
        }
        return new RestResponse<>();
    }

    /**
     * 重试测试
     * @return
     */
    @GetMapping("/retry")
    public void RetryTest() throws Exception {
        reteyService.call();

    }




}
