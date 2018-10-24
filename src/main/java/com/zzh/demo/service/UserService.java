package com.zzh.demo.service;

import com.zzh.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;

public interface UserService {
    User findById(String id);
    List<User> findAll();
    Future<List<User>> findAsynAll();
    User save(User user);
    void delete(String id);
    Page<User> findAll(Pageable pageable);
    List<User> findByName(String name);
    List<User> findByNameLike(String name);
    List<User> findByIdIn(Collection<String> ids);

    User findByNameAndAge(String name, String age);
    User findByNameAndAgeRetry(String name, String age);
}
