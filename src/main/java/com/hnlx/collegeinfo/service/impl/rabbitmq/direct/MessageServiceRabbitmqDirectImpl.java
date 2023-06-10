package com.hnlx.collegeinfo.service.impl.rabbitmq.direct;

import com.hnlx.collegeinfo.service.MessageService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * description : 微服务通信发送端
 * @author : Pikachudy
 * @date : 2022/11/14 21:42
 */
@Service
public class MessageServiceRabbitmqDirectImpl implements MessageService {
    @Resource
    RabbitTemplate template;
    @Override
    public void sendMessage(String msg) {
        Object res = template.convertSendAndReceive("amq.direct","college",msg);
        System.out.println("收到相应"+res);
    }
}
