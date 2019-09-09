package com.demo.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 队列消费者
 * @author Alan
 *
 */
@Component
@RabbitListener(queues = RabbitConfig.QUEUE_A)
public class MsgReceiver_Two {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @RabbitHandler
    public void process(String content) {    
            logger.info("处理器two接收处理队列A当中的消息： " + content);
    }
 
}

