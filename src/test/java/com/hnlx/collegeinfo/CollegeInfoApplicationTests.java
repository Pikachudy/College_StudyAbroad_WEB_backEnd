package com.hnlx.collegeinfo;

import com.hnlx.collegeinfo.service.impl.rabbitmq.direct.MessageServiceRabbitmqDirectImpl;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class CollegeInfoApplicationTests {

    @Resource
    MessageServiceRabbitmqDirectImpl messageServiceRabbitmqDirect;

    @Test
    void publisher(){
        messageServiceRabbitmqDirect.sendMessage("Hello!");
    }
}
