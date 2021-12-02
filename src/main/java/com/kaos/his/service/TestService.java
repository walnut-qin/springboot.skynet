package com.kaos.his.service;

import com.kaos.his.entity.config.Param;
import com.kaos.his.mapper.config.ParamMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService {
    @Autowired
    ParamMapper paramListMapper;

    @Transactional
    public Param Run(String param) {
        // 查询出指定的药品实体
        var drug = this.paramListMapper.QueryParam("NormString", true);

        return drug;
    }
}
