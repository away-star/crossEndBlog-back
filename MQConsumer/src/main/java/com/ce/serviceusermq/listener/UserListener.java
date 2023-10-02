package com.ce.serviceusermq.listener;

import cn.hutool.json.JSONUtil;

import com.ce.servicecommon.domain.MailDto;
import com.ce.serviceusermq.entity.LoginLog;
import com.ce.serviceusermq.service.LoginLogService;
import com.ce.serviceusermq.util.MailClientUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;



/**
 * @author ce
 * @date 2023/5/7 16:13
 */
@Component
@Slf4j
public class UserListener {
    @Resource
    private LoginLogService loginLogService;

    @Resource
    private MailClientUtil mailClientUtil;


    @RabbitListener(bindings = @QueueBinding(
            //此处durable指的是队列是否磁盘持久化（本来在内存，断电保存）
            value = @Queue(value = "queue_login" /*durable = "true"*/),
            //当 ignoreDeclarationExceptions 属性被设置为 true 时，如果交换机已经存在，生产者在声明交换机时不会抛出异常。相反，它会继续声明队列并将消息发布到该交换机上。
            exchange = @Exchange(name = "exchange_user"/* durable = "true"*/, type = ExchangeTypes.DIRECT, ignoreDeclarationExceptions = "true"),
            key = "login"
    ))
    public void listenLogin(String loginLog) {
        log.error(loginLog);
        LoginLog loginLog1 = JSONUtil.toBean(loginLog, LoginLog.class);
        log.error("loginLog:{}", loginLog);
        loginLogService.save(loginLog1);
    }

    @RabbitListener(bindings = @QueueBinding(
            //此处durable指的是队列是否磁盘持久化（本来在内存，断电保存）
            value = @Queue(value = "queue_loginCaptcha"),
            //当 ignoreDeclarationExceptions 属性被设置为 true 时，如果交换机已经存在，生产者在声明交换机时不会抛出异常。相反，它会继续声明队列并将消息发布到该交换机上。
            exchange = @Exchange(name = "exchange_user", type = ExchangeTypes.DIRECT, ignoreDeclarationExceptions = "true"),
            key = "loginCaptcha"
    ))
    public void listenLoginCaptcha(MailDto mailDto) {
        log.error("loginLog:{}", mailDto);
        mailClientUtil.sendMail(mailDto.getMail(), mailDto.getCaptcha(), mailDto.getContent());
    }

    @RabbitListener(bindings = @QueueBinding(
            //此处durable指的是队列是否磁盘持久化（本来在内存，断电保存）
            value = @Queue(value = "queue_registerCaptcha" /*durable = "true"*/),
            //当 ignoreDeclarationExceptions 属性被设置为 true 时，如果交换机已经存在，生产者在声明交换机时不会抛出异常。相反，它会继续声明队列并将消息发布到该交换机上。
            exchange = @Exchange(name = "exchange_user"/* durable = "true"*/, type = ExchangeTypes.DIRECT, ignoreDeclarationExceptions = "true"),
            key = "registerCaptcha"
    ))
    public void listenRegisterCaptcha(MailDto mailDto) {
        log.error("loginLog:{}", mailDto);
        mailClientUtil.sendMail(mailDto.getMail(), mailDto.getCaptcha(), mailDto.getContent());
    }

    @RabbitListener(bindings = @QueueBinding(
            //此处durable指的是队列是否磁盘持久化（本来在内存，断电保存）
            value = @Queue(value = "queue_recoverCaptcha" /*durable = "true"*/),
            //当 ignoreDeclarationExceptions 属性被设置为 true 时，如果交换机已经存在，生产者在声明交换机时不会抛出异常。相反，它会继续声明队列并将消息发布到该交换机上。
            exchange = @Exchange(name = "exchange_user"/* durable = "true"*/, type = ExchangeTypes.DIRECT, ignoreDeclarationExceptions = "true"),
            key = "recoverCaptcha"
    ))
    public void listenRecoverCaptcha(MailDto mailDto) {
        log.error("loginLog:{}", mailDto);
        mailClientUtil.sendMail(mailDto.getMail(), mailDto.getCaptcha(), mailDto.getContent());
    }
}


