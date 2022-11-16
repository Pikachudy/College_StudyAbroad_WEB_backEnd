package com.hnlx.collegeinfo.service.impl.rabbitmq.direct.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * description : 信息接收
 * @author : Pikachudy
 * @date : 2022/11/14 21:49
 */
@Component
public class MessageListener {
//    @RabbitListener(queues = "college",messageConverter = "jacksonConverter")
    @RabbitListener(queues = "college")
    public String collegeReceiver(String msg){
        System.out.println(msg);
        return "Get!";
    }
}
