package com.zzh.demo.dao;

import com.zzh.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author zzh05
 * @date 2018/10/22
 */
@Mapper
public interface UserDao {

    /**
     * @param name
     * @param age
     */
    User findByNameAndAge(@Param("name") String name, @Param("age") String age);
}
