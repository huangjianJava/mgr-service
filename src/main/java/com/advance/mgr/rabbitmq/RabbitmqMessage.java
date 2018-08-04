package com.advance.mgr.rabbitmq;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import lombok.Data;
import lombok.ToString;

/**
 * @author : hongcheng.wu
 * @version V1.0
 * @Description: 封装公共的消息对象
 * @date Date : 2018/8/4 10:01
 * @since JDK 1.8
 */
@Data
@ToString
public class RabbitmqMessage<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 发送者
     */
    private String sender;

    /**
     * 描述 : 交换机
     */
    private String exchange;

    /**
     * 描述 : 路由键
     */
    private String routingKey;


    /**
     * 描述 : 时间戳
     */
    private Long timestamp;

    /**
     * 描述 : 消息创建时间
     */
    private Date createDate;

    /**
     * 描述 : 消息发送时间
     */
    private Date sendDate;

    /**
     * 描述 : 消息头
     */
    private Map<String, String> header;

    /**
     * 描述 : 消息体
     */
    private T body;

    /**
     * 描述 : 构造函数
     */
    public RabbitmqMessage() {

    }

    /**
     * 描述 : 构造函数
     *
     * @param header 消息头
     * @param body   消息体
     */
    public RabbitmqMessage(Map<String, String> header, T body) {
        this.id = UUID.randomUUID().toString();
        this.createDate = new Date();
        this.header = header;
        this.body = body;
    }


}
