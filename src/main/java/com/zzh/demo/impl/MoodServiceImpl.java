package com.zzh.demo.impl;

import com.zzh.demo.entity.Mood;
import com.zzh.demo.repository.MoodRepository;
import com.zzh.demo.service.MoodService;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;

@Service
public class MoodServiceImpl implements MoodService {

    @Resource
    private MoodRepository moodRepository;

    @Override
    public Mood save(Mood mood) {
        return moodRepository.save(mood);
    }

    private static Destination destination = new ActiveMQQueue("asyn.q");

    @Resource
    private MoodProducer moodProducer;
    @Override
    public String asynSave(Mood mood) {
        moodProducer.sendMessage(destination, mood);
        return "success";
    }




}
