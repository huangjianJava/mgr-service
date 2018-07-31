package com.advance.mgr.controller;

import java.util.concurrent.Future;
import com.advance.mgr.service.AsyncTasks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

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
}
