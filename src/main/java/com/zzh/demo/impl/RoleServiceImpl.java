package com.zzh.demo.impl;

import com.zzh.demo.entity.Role;
import com.zzh.demo.repository.RoleRepository;
import com.zzh.demo.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleRepository roleRepository;

    @Override
    public Role find(String id) {
        return roleRepository.findById(id).get();
    }
}
