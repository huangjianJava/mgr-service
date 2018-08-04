package com.advance.mgr;

import java.util.Date;
import com.advance.mgr.dto.sys.SysRoleResDto;
import com.advance.mgr.rabbitmq.RabbitmqProducers;
import com.advance.mgr.rabbitmq.RabbitmqConstant;
import com.advance.mgr.rabbitmq.RabbitmqMessage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Test
    public void Test(){
        SysRoleResDto dto = new SysRoleResDto();
        dto.setIsAdmin(1);
        dto.setRoleName("测试");
        dto.setRemark("测试");
        RabbitmqMessage<SysRoleResDto> message = new RabbitmqMessage<>();
        message.setSendDate(new Date());
        message.setExchange(RabbitmqConstant.EXCHANGE_MESSAGE_LOG.class.getSimpleName());
        message.setRoutingKey(RabbitmqConstant.EXCHANGE_MESSAGE_LOG.SEND.name());
        message.setTimestamp(System.currentTimeMillis());
        message.setBody(dto);
        mq.convertAndSend(RabbitmqConstant.EXCHANGE_MESSAGE_LOG.class.getSimpleName(),RabbitmqConstant.EXCHANGE_MESSAGE_LOG.SEND.name(),message);
    }
}
