package com.kaos.his.service;

import com.kaos.his.entity.User;
import com.kaos.his.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public User Sel(String id) {
        return userMapper.Sel(id);
    }
}
