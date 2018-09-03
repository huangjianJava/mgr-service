package com.advance.mgr.rabbitmq.messageLog.domain;

import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author : hongcheng.wu
 * @version V1.0
 * @Description: ErrorLog
 * @date Date : 2018/8/4 11:36
 * @since JDK 1.8
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
public class ErrorLog {

    /**
     * 描述 : 日志ID
     */
    private String logId;

    /**
     * 描述 : 消息ID
     */
    private String id;

    /**
     * 描述 : 队列
     */
    private String queues;

    /**
     * 描述 : 错误时间
     */
    private Date errorDate;

    /**
     * 描述 : 错误信息
     */
    private String errorMsg;
}
