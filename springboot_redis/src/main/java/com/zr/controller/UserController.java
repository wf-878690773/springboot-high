package com.zr.controller;

import com.zr.service.UserService;
import com.zr.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {

        System.out.println("---------hello------------");
        log.debug("hello-----------------------");
        return "HELLO,springBoot";
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public List<User> findAll() throws Exception {

        List<User> users = service.findAll();
        for (User user : users) {
            System.out.println(user);

        }
        return users;
    }


}
