package com.star.serviceusermq.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;


// 加入Spring容器
@Component
public class MailClientUtil {



    @Resource
    private JavaMailSender mailSender;// 引入mail依赖后即可注入该类，通过该类实现邮件发送的最终方法。

    @Value("${spring.mail.username}")
    private String from;//定义发件人 ，从配置文件中读取

    /**
     * 发送邮件功能
     *
     * @param to              收件人邮箱，随意，可以是@163.com，也可以是@qq.com
     * @param theme，主题，当前邮件主题
     * @param content，邮件内容    发送邮件失败会保存日志
     */
    public boolean sendMail(String to, String theme, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(theme);

            // 设置邮件内容为HTML格式
            helper.setText("<html><head><style type=\"text/css\">" +
                    ".mail-title {background-color: #0096d0; padding: 20px; color: #fff; font-size: 24px;}" +
                    ".mail-content {background-color: #f1f1f1; padding: 20px;}" +
                    ".mail-link {color: #fff; text-decoration: none;}" +
                    "</style></head><body>" +
                    "<div class=\"mail-title\">" + content + "</div>" +
                    "<div class=\"mail-content\">" +
                    "欢迎体验cross-end blog" +
                    "</div>" +
                    "<div class=\"mail-title\">" +
                    "<a class=\"mail-link\" href=\"https://www.example.com\">点击这里访问我的网站</a>" +
                    "</div>" +
                    "</body></html>", true);

            mailSender.send(helper.getMimeMessage());
            return true;
        } catch (javax.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}

