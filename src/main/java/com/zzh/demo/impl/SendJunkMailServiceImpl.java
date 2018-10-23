package com.zzh.demo.impl;

import com.zzh.demo.entity.User;
import com.zzh.demo.mail.SendJunkMailService;
import com.zzh.demo.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class SendJunkMailServiceImpl implements SendJunkMailService {

    @Autowired
    JavaMailSender mailSender;
    @Resource
    private UserService userService;
    @Value("${spring.mail.username}")
    private String from;
    public static final Logger logger = LogManager.getLogger(SendJunkMailServiceImpl.class);

    @Override
    public boolean sendJunkMail(List<User> userList) {

        try {
            if(userList == null || userList.size() <= 0) return Boolean.FALSE;
            for(User user:userList) {
                MimeMessage mimeMessage = this.mailSender.createMimeMessage();
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
                mimeMessageHelper.setFrom(from);
                mimeMessageHelper.setSubject("Spring Boot Test Message!");
                mimeMessageHelper.setTo("zhuzh@paraview.cn");
                mimeMessageHelper.setText(user.getName() +  "， 今天天气不错，适合出行！");
                this.mailSender.send(mimeMessage);
            }
        } catch (Exception ex) {
            logger.error("send JunkMail error and user=%s", userList, ex);
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
