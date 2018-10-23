package com.zzh.demo.impl;

import com.zzh.demo.entity.Mood;
import com.zzh.demo.service.MoodService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MoodConsumer {

    @JmsListener(destination = "test.q")
    public void receiveQueue(String text){
        System.out.println("用户发表评论： " + text);
    }

    @Resource
    private MoodService moodService;

    @JmsListener(destination = "asyn.q")
    public void receiveQueue(Mood mood){
        moodService.save(mood);
    }
}
