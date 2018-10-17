package com.zzh.demo.controller;

import com.zzh.demo.Entity.User;
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
}
