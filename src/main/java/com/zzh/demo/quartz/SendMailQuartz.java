package com.zzh.demo.quartz;

import com.zzh.demo.Entity.User;
import com.zzh.demo.mail.SendJunkMailService;
import com.zzh.demo.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
@Configurable
@EnableScheduling
public class SendMailQuartz {

    private static final Logger logger = LogManager.getLogger(SendMailQuartz.class);

    @Resource
    private SendJunkMailService sendJunkMailService;
    @Resource
    private UserService userService;

    @Scheduled(cron = "0 33 * * * *")
    public void reportCurrentByCron(){
        List<User> userList = userService.findAll();
        if (userList == null || userList.size() <= 0) return;
        sendJunkMailService.sendJunkMail(userList);
        logger.info("定时器开始运行！");
    }
}
