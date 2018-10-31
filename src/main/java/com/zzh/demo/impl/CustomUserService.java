package com.zzh.demo.impl;

import com.zzh.demo.entity.User;
import com.zzh.demo.entity.UserRoleRel;
import com.zzh.demo.error.BusinessException;
import com.zzh.demo.service.RoleService;
import com.zzh.demo.service.UserRoleRelService;
import com.zzh.demo.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserService implements UserDetailsService {

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private UserRoleRelService userRoleRelService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findById(s); //存在错误
        if (user == null){
            throw new BusinessException("用户不存在");
        }
        List<UserRoleRel> roleList = userRoleRelService.findByUserId(user.getId());
        List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
        if (roleList != null && roleList.size() > 0){
            for (UserRoleRel rel:roleList){
                String roleName = roleService.find(rel.getRoleId()).getName();
                authorityList.add(new SimpleGrantedAuthority(roleName));
            }
        }
        return new org.springframework.security.core.userdetails.User(user.getName(),user.getPassword(),authorityList);
    }

}
