package com.zzh.demo.repository;

import com.zzh.demo.entity.UserRoleRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRoleRelRepository extends JpaRepository<UserRoleRel,String> {
    List<UserRoleRel> findByUserId(@Param("userId")String userID);
}
