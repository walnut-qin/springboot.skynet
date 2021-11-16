package com.kaos.his.controller;

import com.kaos.his.service.UserService;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testBoot")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("getUser")
    public String GetUser(@Param String id) {
        return userService.Sel(id).toString();
    }
}
