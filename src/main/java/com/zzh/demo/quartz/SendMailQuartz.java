package com.zzh.demo.quartz;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Configurable
@EnableScheduling
public class SendMailQuartz {

    private static final Logger logger = LogManager.getLogger(SendMailQuartz.class);

    @Scheduled(cron = "*/3 * * * * *")
    public void reportCurrentByCron(){
        logger.info("定时器开始运行！");
    }
}
