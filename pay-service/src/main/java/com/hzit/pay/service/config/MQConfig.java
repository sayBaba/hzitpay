package com.hzit.pay.service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * MQ配置
 */
@Configuration
public class MQConfig {

    //异步通知队列
    public static final String NOTIFY_MCH_QUEUE = "NOTIFY_MCH_QUEUE";

    // 通知延时交换机
    public static final String Delay_Exchange_Name = "NOTIFY_DELAY_EXCHANGE";

    // 通知延时队列
    public static final String Timeout_Trade_Queue_Name = "NOTIFY_DELAY_QUEUE";

    @Bean
    public Queue delayPayQueue() {
        return new Queue(MQConfig.Timeout_Trade_Queue_Name, true);
    }

    @Bean
    public CustomExchange delayExchange() {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(Delay_Exchange_Name, "x-delayed-message", true, false, args);
    }

    /**
     * 把立即消费的队列和延时消费的exchange绑定在一起
     *
     * @return
     */
    @Bean
    public Binding bindingNotify() {
        return BindingBuilder.bind(delayPayQueue()).to(delayExchange()).with("").noargs();
    }




}
