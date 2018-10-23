package com.zzh.demo.repository;

import com.zzh.demo.entity.Mood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoodRepository extends JpaRepository<Mood, String> {
}
