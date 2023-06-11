package com.hnlx.collegeinfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hnlx.collegeinfo.service.impl.CollegeServiceImpl;
import com.hnlx.collegeinfo.service.impl.rabbitmq.direct.MessageServiceRabbitmqDirectImpl;
import io.qameta.allure.Story;
import jdk.jfr.Description;
import lombok.Data;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CollegeInfoApplicationTests {
    @Resource
    CollegeServiceImpl collegeService;

    @Tag("testGetIdbyChName")
    @Description("测试根据中文名获取大学id")
    @Test
    void testGetIdbyChName(){
        Object res = collegeService.getUniversityIdByChname("牛津大学");
        // 从json字符串中获取university_id
        String id = res.toString().substring(17,res.toString().length()-1);
        assertEquals("6",id);
    }
}

