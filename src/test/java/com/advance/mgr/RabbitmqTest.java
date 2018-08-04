package com.advance.mgr;

import java.util.Date;
import com.advance.mgr.dto.sys.SysRoleResDto;
import com.advance.mgr.rabbitmq.RabbitmqProducers;
import com.advance.mgr.rabbitmq.RabbitmqMessage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author : hongcheng.wu
 * @version V1.0
 * @Description: TODO
 * @date Date : 2018/8/4 10:28
 * @since JDK 1.8
 */

public class RabbitmqTest  extends BaseTest{
    @Autowired
    private RabbitmqProducers mq;

    @Value("${spring.rabbitmq.mgr-exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.rount-key}")
    private String rountKey;

    @Value("${spring.application.name}")
    private String springApplicationName;


    @Test
    public void Test(){
        SysRoleResDto dto = new SysRoleResDto();
        dto.setIsAdmin(1111);
        dto.setRoleName("是否重新发送");
        dto.setRemark("是否重新发送请求");
        RabbitmqMessage<SysRoleResDto> message = new RabbitmqMessage<>();
        message.setSender(springApplicationName);
        message.setSendDate(new Date());
        message.setExchange(exchange);
        message.setRoutingKey(rountKey);
        message.setTimestamp(System.currentTimeMillis());
        message.setBody(dto);
        mq.convertAndSend(exchange,rountKey,message);
    }
}
