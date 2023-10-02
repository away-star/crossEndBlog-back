package com.ce.serviceusermq.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * @author ce
 * @date 2023/5/8 16:25
 */

@Slf4j
public class DLListener {
    @RabbitListener(bindings = @QueueBinding(
            //此处durable指的是队列是否磁盘持久化（本来在内存，断电保存）
            value = @Queue(value = "queue_dead" /*durable = "true"*/),
            //当 ignoreDeclarationExceptions 属性被设置为 true 时，如果交换机已经存在，生产者在声明交换机时不会抛出异常。相反，它会继续声明队列并将消息发布到该交换机上。
            exchange = @Exchange(name = "exchange_dead"/* durable = "true"*/, type = ExchangeTypes.DIRECT, ignoreDeclarationExceptions = "true"),
            key = "dead"
    ))
    public void listenDeadMessage(String message) {
        log.error("error:{}", message);
    }

    @RabbitListener(bindings = @QueueBinding(
            //此处durable指的是队列是否磁盘持久化（本来在内存，断电保存）
            value = @Queue(value = "queue_error" /*durable = "true"*/),
            //当 ignoreDeclarationExceptions 属性被设置为 true 时，如果交换机已经存在，生产者在声明交换机时不会抛出异常。相反，它会继续声明队列并将消息发布到该交换机上。
            exchange = @Exchange(name = "exchange_error"/* durable = "true"*/, type = ExchangeTypes.DIRECT, ignoreDeclarationExceptions = "true"),
            key = "error"
    ))
    public void listenErrorMessage(String error) {
        log.error("error:{}", error);
    }
}
