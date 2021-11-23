package com.kaos.his.service;

import com.kaos.his.entity.credential.PreinCard;
import com.kaos.his.mapper.credential.PreinCardMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    PreinCardMapper departmentMapper;

    public PreinCard Run(String param) {
        return this.departmentMapper.QueryLatestPreinCard(param);
    }
}
