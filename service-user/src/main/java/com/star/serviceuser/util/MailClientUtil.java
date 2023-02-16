package com.star.serviceuser.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;


// 加入Spring容器
@Component
public class MailClientUtil {

    // 记录日志
    private static final Logger logger = LoggerFactory.getLogger(MailClientUtil.class);

    @Autowired
    private JavaMailSender mailSender;// 引入mail依赖后即可注入该类，通过该类实现邮件发送的最终方法。

    @Value("${spring.mail.username}")
    private String from;//定义发件人 ，从配置文件中读取

    /**
     * 发送邮件功能
     * @param to 收件人邮箱，随意，可以是@163.com，也可以是@qq.com
     * @param theme，主题，当前邮件主题
     * @param content，邮件内容
     * 发送邮件失败会保存日志
     */
    public boolean sendMail(String to, String theme, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(theme);
            helper.setText(content, true);//不加参数默认是文本，加上true之后支持html格式文件
            mailSender.send(helper.getMimeMessage());
            return true;
        } catch (javax.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

