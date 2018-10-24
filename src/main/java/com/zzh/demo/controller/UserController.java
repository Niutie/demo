package com.zzh.demo.controller;

import com.zzh.demo.entity.User;
import com.zzh.demo.error.BusinessException;
import com.zzh.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/test")
    public String test(Model model) {
        List<User> user = userService.findAll();
        model.addAttribute("users", user);
        return "user";
    }

    @RequestMapping("/findAll")
    public String findAll(Model model){
        List<User> user = userService.findAll();
        model.addAttribute("users", user);
        throw new BusinessException("业务异常");
    }

    @RequestMapping("/findByNameAndAgeRetry")
    public String findByNameAndAgeRetry(Model model){
        User user = userService.findByNameAndAgeRetry("朱八", "27");
        model.addAttribute("users", user);
        return "success";
    }
}
