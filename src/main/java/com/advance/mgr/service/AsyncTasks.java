package com.advance.mgr.service;

import java.util.Random;
import java.util.concurrent.Future;
import com.advance.mgr.controller.HelloController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

/**
 * @author : hongcheng.wu
 * @version V1.0
 * @Description: Async实现异步调用
 * @date Date : 2018/7/31 16:00
 * @since JDK 1.8
 */
@Component
public class AsyncTasks {
    private static final Logger logger = LoggerFactory.getLogger(AsyncTasks.class);
    public static Random random = new Random();
    @Async("mySimpleAsync")
    public Future<String> doTaskOne() throws Exception {
        logger.info("Async, taskOne, Start...");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        logger.info("Async, taskOne, End, 第一个耗时: " + (end - start) + "毫秒");
        return new AsyncResult<>("AsyncTaskOne Finished");
    }

    @Async("myAsync")
    public Future<String> doTaskTwo() throws Exception {
        logger.info("Async, taskTwo, Start");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        logger.info("Async, taskTwo, End, 第二个耗时: " + (end - start) + "毫秒");
        return new AsyncResult<>("AsyncTaskTwo Finished");
    }

    @Async("myAsync")
    public Future<String> doTaskThree() throws Exception {
        logger.info("Async, taskThree, Start");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(5000));
        long end = System.currentTimeMillis();
        logger.info("Async, taskThree, End, 第三个耗时: " + (end - start) + "毫秒");
        return new AsyncResult<>("AsyncTaskThree Finished");
    }
}
