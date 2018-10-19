package com.zzh.demo.impl;

import com.zzh.demo.Entity.User;
import com.zzh.demo.repository.UserRepository;
import com.zzh.demo.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private RedisTemplate redisTemplate;

    private static final String ALL_USER = "ALL_USER_LIST";

    @Override
    public User findById(String id) {
        //return userRepository.findById(id).get();
        //step 1: 查询缓存中的数据
        List<User> userList = redisTemplate.opsForList().range(ALL_USER, 0, -1);
        if (userList != null && userList.size() > 0){
            for(User user : userList){
                if (user.getId().equals(id)){
                    return user;
                }
            }
        }
        //step 2：查询数据库中的数据
        User user = userRepository.findById(id).get();
        if (user != null){
            redisTemplate.opsForList().leftPush(ALL_USER, user);
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        //return userRepository.save(user);
        User saveUser = userRepository.save(user);
        //String error = null;
        //error.split("/");
        return saveUser;
    }

    @Override
    public void delete(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public List<User> findByNameLike(String name) {
        return userRepository.findByNameLike(name);
    }

    @Override
    public List<User> findByIdIn(Collection<String> ids) {
        return userRepository.findByIdIn(ids);
    }


}
