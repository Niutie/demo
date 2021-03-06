package com.zzh.demo.listener;

import com.zzh.demo.entity.User;
import com.zzh.demo.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
public class UserListener implements ServletContextListener {

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private UserService userService;
    private static final String ALL_USER = "ALL_USER_LIST";

    Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        List<User> userList = userService.findAll();
        redisTemplate.delete(ALL_USER);
        redisTemplate.opsForList().leftPushAll(ALL_USER, userList);
        List<User> queryUserList = redisTemplate.opsForList().range(ALL_USER, 0, -1);
        //System.out.println("缓存中目前的用户数有： " + queryUserList.size() + " 人！");
        //System.out.println("ServletContext 初始化");
        logger.info("ServletContext 初始化");
        logger.info("缓存中目前的用户数有： " + queryUserList.size() + " 人！");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //System.out.println("ServletContext 销毁");
        logger.info("ServletContext 销毁");
    }
}
