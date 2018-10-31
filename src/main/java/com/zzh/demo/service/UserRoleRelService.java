package com.zzh.demo.service;

import com.zzh.demo.entity.UserRoleRel;

import java.util.List;

public interface UserRoleRelService {
    List<UserRoleRel> findByUserId(String userId);
}
