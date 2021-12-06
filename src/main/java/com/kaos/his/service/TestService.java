package com.kaos.his.service;

import com.kaos.his.entity.config.DawnCodeType;
import com.kaos.his.mapper.config.DawnCodeTypeMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService {
    @Autowired
    DawnCodeTypeMapper paramListMapper;

    @Transactional
    public DawnCodeType Run(String param) {
        // 查询出指定的药品实体
        var drug = this.paramListMapper.QueryValidCodeType(param);

        return drug;
    }
}
