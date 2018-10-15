package com.zzh.demo.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@Controller
/**
 * @RestController
 * 它相当于@Controller+@ResponseBody的简写。
 * @ResponseBody 的作用时把返回值回显到页面，默认解析成Json格式。不会走视图解析器
 * 如果把@RestController换成@Controller，页面会报500，提示你检查视图解析器。
 */
public class HelloController {
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String helloSpringBoot(){
        return "Hello SpringBoot";
    }

    @Value("${devName}")
    private String devName;
    @Value("${time}")
    private String time;
    @RequestMapping(value = "/showInfo", method = RequestMethod.GET)
    public String showInfo(){
        return "author: "+ devName + ", date: " + time;
    }

    @Autowired
    private Developer developer;
    @RequestMapping(value = "developer", method = RequestMethod.GET)
    public String showDeveloper(){
        return developer.toString();
    }

    @RequestMapping(value = {"/t","/temp","/template"},method = RequestMethod.GET)
    public String showIndexHtml(){
        return "index";
    }

}
