package com.advance.mgr.rabbitmq.messageLog;

/**
 * @author : hongcheng.wu
 * @version V1.0
 * @Description: 定义Rabbitmq日志常量(预留死性模式 不是很懂)
 * @date Date : 2018/8/4 9:58
 * @since JDK 1.8
 */
public class RabbitmqLogConstant {

    /**
     * (EXCHANGE)消息记录
     */
    public enum EXCHANGE_MESSAGE_LOG {
        /**
         * 发送消息
         */
        SEND,
        /**
         * 接收消息
         */
        RECEIVE,
        /**
         * 消息完成
         */
        COMPLETE,
        /**
         * 消息错误
         */
        ERROR
    }

    /**
     * 描述 : 私有化构造函数
     */
    private RabbitmqLogConstant() {
    }

}
