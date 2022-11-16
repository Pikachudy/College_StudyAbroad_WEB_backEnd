package com.hnlx.collegeinfo;

import com.hnlx.collegeinfo.service.impl.rabbitmq.direct.MessageServiceRabbitmqDirectImpl;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.time.Duration;

@SpringBootTest
class CollegeInfoApplicationTests {

//    @Resource
//    MessageServiceRabbitmqDirectImpl messageServiceRabbitmqDirect;
//
//    @Test
//    void publisher(){
//        messageServiceRabbitmqDirect.sendMessage("Hello!");
//    }
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Test
    void testRedis(){
//        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
//        System.out.println(ops.get("name"));
//        ops.getAndExpire("name", Duration.ofDays(1));

    }
}
