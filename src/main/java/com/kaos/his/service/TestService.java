package com.kaos.his.service;

import com.kaos.his.entity.config.Switch;
import com.kaos.his.mapper.config.SwitchMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService {
    @Autowired
    SwitchMapper switchMapper;

    @Transactional
    public Switch Run(String param) {
        // 查询出指定的药品实体
        this.switchMapper.TurnOn(param);

        Switch v = this.switchMapper.QuerySwitch(param);

        return v;
    }
}
