package com.hnlx.collegeinfo.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description : RabbitMQ 配置类
 * @author : Pikachudy
 * @date : 2022/11/14 18:37
 */
@Configuration
public class RabbitConfiguration {
    @Bean("directExchange")  //定义交换机Bean，可以很多个
    public Exchange exchange(){
        return ExchangeBuilder.directExchange("amq.direct").build();
    }

    @Bean("collegeQueue")     //定义消息队列
    public Queue queue(){
        return QueueBuilder
                .nonDurable("college")   //非持久化类型
                .build();
    }

    @Bean("binding")
    public Binding binding(@Qualifier("directExchange") Exchange exchange,
                           @Qualifier("collegeQueue") Queue queue){
        //将我们刚刚定义的交换机和队列进行绑定
        return BindingBuilder
                .bind(queue)   //绑定队列
                .to(exchange)  //到交换机
                .with("college")   //使用自定义的routingKey
                .noargs();
    }
    @Bean("jacksonConverter") //用于JSON转换
    public Jackson2JsonMessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

}
