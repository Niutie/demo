package com.zzh.demo.service;

import com.zzh.demo.entity.Mood;

public interface MoodService {
    Mood save(Mood mood);
    String asynSave(Mood mood);
}
