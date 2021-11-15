package com.kaos.his.mapper;

import com.kaos.his.entity.User;

import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    User Sel(String id);
}
