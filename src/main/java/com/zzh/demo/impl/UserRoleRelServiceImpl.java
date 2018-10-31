package com.zzh.demo.impl;

import com.zzh.demo.entity.UserRoleRel;
import com.zzh.demo.repository.UserRoleRelRepository;
import com.zzh.demo.service.UserRoleRelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserRoleRelServiceImpl implements UserRoleRelService {

    @Resource
    private UserRoleRelRepository userRoleRelRepository;

    @Override
    public List<UserRoleRel> findByUserId(String userId) {
        return userRoleRelRepository.findByUserId(userId);
    }
}
